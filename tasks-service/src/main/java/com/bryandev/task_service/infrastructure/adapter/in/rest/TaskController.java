package com.bryandev.task_service.infrastructure.adapter.in.rest;

import com.bryandev.task_service.application.command.CreateTaskCommand;
import com.bryandev.task_service.application.command.DeleteTaskCommand;
import com.bryandev.task_service.application.command.PatchTaskCommand;
import com.bryandev.task_service.application.command.UpdateTaskCommand;
import com.bryandev.task_service.application.dto.response.TaskPageResponse;
import com.bryandev.task_service.application.dto.response.TaskResponse;
import com.bryandev.task_service.application.query.GetTaskByIdQuery;
import com.bryandev.task_service.application.query.ListTasksQuery;
import com.bryandev.task_service.application.usecase.*;
import com.bryandev.task_service.domain.criteria.PaginationParams;
import com.bryandev.task_service.domain.enums.TaskStatus;
import com.bryandev.task_service.domain.model.PagedResult;
import com.bryandev.task_service.domain.model.Task;
import com.bryandev.task_service.infrastructure.adapter.in.rest.dto.request.CreateTaskRequest;
import com.bryandev.task_service.infrastructure.adapter.in.rest.dto.request.PatchTaskRequest;
import com.bryandev.task_service.infrastructure.adapter.in.rest.dto.request.UpdateTaskRequest;
import com.bryandev.task_service.infrastructure.adapter.in.rest.dto.response.ApiResponse;
import com.bryandev.task_service.infrastructure.adapter.in.rest.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;
    private final PatchTaskUseCase patchTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;
    private final GetTaskByIdUseCase getTaskByIdUseCase;
    private final ListTasksUseCase listTasksUseCase;

    // --- CREAR ---
    @PostMapping
    public ResponseEntity<ApiResponse<TaskResponse>> create(
            @Valid @RequestBody CreateTaskRequest request,
            Authentication authentication) {

        Long userId = getUserId(authentication);
        var command = new CreateTaskCommand(request.title(), request.description(), userId);
        Task task = createTaskUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tarea creada exitosamente", TaskResponse.from(task)));
    }

    // --- ACTUALIZAR TOTAL ---
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskRequest request,
            Authentication authentication) {

        var command = new UpdateTaskCommand(id, request.title(), request.description());
        Task task = updateTaskUseCase.execute(command);

        return ResponseEntity.ok(ApiResponse.success("Tarea actualizada", TaskResponse.from(task)));
    }

    // -- ACTUALIZAR PARCIALMENTE ----
    // --- ACTUALIZAR (PATCH - actualización parcial) ---
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody PatchTaskRequest request,
            Authentication authentication) {

        var command = new PatchTaskCommand(id, request.title(), request.description(), request.status());
        Task task = patchTaskUseCase.execute(command);

        return ResponseEntity.ok(ApiResponse.success("Tarea actualizada", TaskResponse.from(task)));
    }

    // --- ELIMINAR (individual o por lote) ---
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteBatch(@RequestParam List<Long> ids) {
        deleteTaskUseCase.execute(new DeleteTaskCommand(ids));
        return ResponseEntity.ok(ApiResponse.success("Tareas eliminadas", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSingle(@PathVariable Long id) {
        deleteTaskUseCase.execute(new DeleteTaskCommand(List.of(id)));
        return ResponseEntity.ok(ApiResponse.success("Tarea eliminada", null));
    }

    // --- OBTENER POR ID ---
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> getById(@PathVariable Long id) {
        Task task = getTaskByIdUseCase.execute(new GetTaskByIdQuery(id));
        return ResponseEntity.ok(ApiResponse.success("Tarea encontrada", TaskResponse.from(task)));
    }

    // --- LISTADO (endpoint único con Strategy) ---
    @GetMapping
    public ResponseEntity<ApiResponse<TaskPageResponse>> list(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            Authentication authentication) {

        // Si no se pasa userId, usar el del token
        Long userId = getUserId(authentication);

        var pagination = new PaginationParams(page, size, sortBy, sortDirection);
        var query = new ListTasksQuery(userId, status, search, pagination);

        PagedResult<Task> result = listTasksUseCase.execute(query);

        return ResponseEntity.ok(ApiResponse.success("Tareas listadas", TaskPageResponse.from(result)));
    }

    private Long getUserId(Authentication authentication) {
        var userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUserId();
    }
}