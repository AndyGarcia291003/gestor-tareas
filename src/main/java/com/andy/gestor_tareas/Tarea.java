package com.andy.gestor_tareas;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity                       // Le dice a Spring: "esta clase es una tabla en la BD"
@Table(name = "tareas")       // La tabla se llamará "tareas"
public class Tarea {

    @Id                                                 // Este campo es la llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // El id se autoincrementa solo (1, 2, 3...)
    private Long id;

    @Column(nullable = false)   // Columna obligatoria (NOT NULL en la BD)
    private String titulo;

    @Column(columnDefinition = "TEXT")  // Texto largo, opcional
    private String descripcion;

    @Enumerated(EnumType.STRING)   // Guarda el estado como texto ("PENDIENTE"), no como número
    @Column(nullable = false)
    private EstadoTarea estado;

    @Enumerated(EnumType.STRING)
    private PrioridadTarea prioridad;

    @Column(name = "fecha_creacion", updatable = false)  // No cambia una vez creada
    private LocalDateTime fechaCreacion;

    // Se ejecuta automáticamente justo antes de guardar por primera vez
    @PrePersist
    protected void alCrear() {
        this.fechaCreacion = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = EstadoTarea.PENDIENTE;   // Si no se especifica, nace PENDIENTE
        }
    }

    // ===== Getters y Setters (así se leen y escriben los campos) =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public EstadoTarea getEstado() { return estado; }
    public void setEstado(EstadoTarea estado) { this.estado = estado; }

    public PrioridadTarea getPrioridad() { return prioridad; }
    public void setPrioridad(PrioridadTarea prioridad) { this.prioridad = prioridad; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}