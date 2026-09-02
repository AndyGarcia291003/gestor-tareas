package com.andy.gestor_tareas;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// JpaRepository<Tarea, Long>:
//   Tarea = la entidad que maneja este repositorio
//   Long  = el tipo del id de esa entidad
public interface TareaRepository extends JpaRepository<Tarea, Long> {

    // Método "derivado": Spring lee el NOMBRE del método y escribe la consulta solo.
    // "findByEstado" => SELECT * FROM tareas WHERE estado = ?
    // Con esto tendrás el filtro por estado que planeamos, sin escribir SQL.
    List<Tarea> findByEstado(EstadoTarea estado);
}