package com.andy.gestor_tareas;

import org.springframework.stereotype.Service;
import java.util.List;

@Service   // Marca esta clase como "servicio": Spring la administra y la puede inyectar donde se necesite
public class TareaService {

    private final TareaRepository tareaRepository;

    // Inyección por constructor: Spring pasa el repository automáticamente al crear el servicio
    public TareaService(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    // Listar todas las tareas
    public List<Tarea> listarTodas() {
        return tareaRepository.findAll();
    }

    // Listar solo las tareas de un estado (usa el método derivado que creaste en el repository)
    public List<Tarea> listarPorEstado(EstadoTarea estado) {
        return tareaRepository.findByEstado(estado);
    }

    // Buscar una tarea por su id; si no existe, lanza 404
    public Tarea obtenerPorId(Long id) {
        return tareaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la tarea con id " + id));
    }

    // Crear una tarea nueva
    public Tarea crear(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    // Actualizar una tarea existente (PUT reemplaza la tarea completa)
    public Tarea actualizar(Long id, Tarea datos) {
        Tarea tarea = obtenerPorId(id);        // reutiliza el método de arriba: ya lanza 404 si no existe
        tarea.setTitulo(datos.getTitulo());
        tarea.setDescripcion(datos.getDescripcion());
        tarea.setEstado(datos.getEstado());
        tarea.setPrioridad(datos.getPrioridad());
        return tareaRepository.save(tarea);    // save() actualiza porque la tarea ya trae id
    }

    // Eliminar una tarea
    public void eliminar(Long id) {
        if (!tareaRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No existe la tarea con id " + id);
        }
        tareaRepository.deleteById(id);
    }
}