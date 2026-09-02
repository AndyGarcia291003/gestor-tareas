// Los mismos estados y prioridades del backend (deben coincidir exactamente)
export type EstadoTarea = 'PENDIENTE' | 'EN_PROGRESO' | 'COMPLETADA';
export type PrioridadTarea = 'BAJA' | 'MEDIA' | 'ALTA';

// La forma de una tarea tal como la devuelve tu API
export interface Tarea {
  id?: number;              // opcional (?): al crear una tarea nueva todavía no tiene id
  titulo: string;
  descripcion?: string;     // opcional: puede venir vacía
  estado?: EstadoTarea;     // opcional al crear: el backend la pone en PENDIENTE por defecto
  prioridad?: PrioridadTarea;
  fechaCreacion?: string;   // la genera el backend; llega como texto (fecha en formato ISO)
}