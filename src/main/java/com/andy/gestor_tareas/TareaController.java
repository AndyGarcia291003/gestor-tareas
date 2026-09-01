package com.andy.gestor_tareas;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController                 // Esta clase maneja peticiones HTTP y devuelve datos (JSON)
@RequestMapping("/api/tareas")  // Todas las rutas de aquí empiezan con /api/tareas
public class TareaController {

    private final TareaService tareaService;

    // Misma inyección por constructor que en el service
    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    // GET /api/tareas            -> todas las tareas
    // GET /api/tareas?estado=PENDIENTE -> solo las de ese estado
    @GetMapping
    public List<Tarea> listar(@RequestParam(required = false) EstadoTarea estado) {
        if (estado != null) {
            return tareaService.listarPorEstado(estado);
        }
        return tareaService.listarTodas();
    }

    // GET /api/tareas/{id}  -> una tarea por su id
    @GetMapping("/{id}")
    public Tarea obtener(@PathVariable Long id) {
        return tareaService.obtenerPorId(id);   // si no existe, el service lanza 404
    }

    // POST /api/tareas  -> crear una tarea (los datos vienen en el cuerpo JSON)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)         // responde 201 Created en vez de 200
    public Tarea crear(@RequestBody Tarea tarea) {
        return tareaService.crear(tarea);
    }

    // PUT /api/tareas/{id}  -> actualizar una tarea existente
    @PutMapping("/{id}")
    public Tarea actualizar(@PathVariable Long id, @RequestBody Tarea datos) {
        return tareaService.actualizar(id, datos);
    }

    // DELETE /api/tareas/{id}  -> eliminar una tarea
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)      // responde 204 No Content (borrado exitoso, sin cuerpo)
    public void eliminar(@PathVariable Long id) {
        tareaService.eliminar(id);
    }
}