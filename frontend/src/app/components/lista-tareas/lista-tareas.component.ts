import { Component, signal, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TareaService } from '../../services/tarea.service';
import { Tarea, PrioridadTarea, EstadoTarea } from '../../models/tarea.model';

// Un tipo para el filtro: o un estado concreto, o 'TODAS'
type Filtro = EstadoTarea | 'TODAS';

@Component({
  selector: 'app-lista-tareas',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './lista-tareas.component.html',
  styleUrl: './lista-tareas.component.css'
})
export class ListaTareasComponent implements OnInit {

  private tareaService = inject(TareaService);

  tareas = signal<Tarea[]>([]);
  cargando = signal<boolean>(false);
  error = signal<string | null>(null);

  // El filtro actualmente seleccionado (empieza en "TODAS")
  filtroActivo = signal<Filtro>('TODAS');

  nuevoTitulo = '';
  nuevaDescripcion = '';
  nuevaPrioridad: PrioridadTarea = 'MEDIA';

  ngOnInit(): void {
    this.cargarTareas();
  }

  // Ahora cargarTareas respeta el filtro activo
  cargarTareas(): void {
    this.cargando.set(true);
    this.error.set(null);

    const filtro = this.filtroActivo();

    // Si el filtro es "TODAS" -> listarTodas; si es un estado -> listarPorEstado
    const peticion = filtro === 'TODAS'
      ? this.tareaService.listarTodas()
      : this.tareaService.listarPorEstado(filtro);

    peticion.subscribe({
      next: (datos) => {
        this.tareas.set(datos);
        this.cargando.set(false);
      },
      error: (err) => {
        console.error('Error al cargar tareas:', err);
        this.error.set('No se pudieron cargar las tareas. ¿Está corriendo el backend?');
        this.cargando.set(false);
      }
    });
  }

  // Cambia el filtro y recarga desde el backend
  cambiarFiltro(filtro: Filtro): void {
    this.filtroActivo.set(filtro);
    this.cargarTareas();
  }

  crearTarea(): void {
    if (!this.nuevoTitulo.trim()) {
      return;
    }

    const nueva: Tarea = {
      titulo: this.nuevoTitulo.trim(),
      descripcion: this.nuevaDescripcion.trim(),
      prioridad: this.nuevaPrioridad
    };

    this.tareaService.crear(nueva).subscribe({
      next: () => {
        // Recargamos respetando el filtro (así una tarea nueva PENDIENTE
        // no aparece si estás viendo, por ejemplo, solo COMPLETADAS)
        this.cargarTareas();
        this.nuevoTitulo = '';
        this.nuevaDescripcion = '';
        this.nuevaPrioridad = 'MEDIA';
      },
      error: (err) => {
        console.error('Error al crear tarea:', err);
        this.error.set('No se pudo crear la tarea.');
      }
    });
  }

  eliminarTarea(id: number | undefined): void {
    if (id === undefined) return;

    this.tareaService.eliminar(id).subscribe({
      next: () => {
        this.tareas.update(actuales => actuales.filter(t => t.id !== id));
      },
      error: (err) => {
        console.error('Error al eliminar tarea:', err);
        this.error.set('No se pudo eliminar la tarea.');
      }
    });
    
  }
  // Cambiar el estado de una tarea (usa el PUT del backend)
  cambiarEstado(tarea: Tarea, nuevoEstado: EstadoTarea): void {
    // Construimos la tarea con el estado nuevo, conservando lo demás
    const actualizada: Tarea = { ...tarea, estado: nuevoEstado };

    this.tareaService.actualizar(tarea.id!, actualizada).subscribe({
      next: () => {
        // Recargamos respetando el filtro activo
        this.cargarTareas();
      },
      error: (err) => {
        console.error('Error al actualizar tarea:', err);
        this.error.set('No se pudo actualizar la tarea.');
      }
    });
  }
}