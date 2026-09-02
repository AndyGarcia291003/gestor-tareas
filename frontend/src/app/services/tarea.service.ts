import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tarea, EstadoTarea } from '../models/tarea.model';

@Injectable({ providedIn: 'root' })   // Servicio disponible en toda la app (singleton)
export class TareaService {

  // La URL base de tu API (el backend corriendo en el puerto 8080)
  private readonly apiUrl = 'http://localhost:8080/api/tareas';

  // Inyecta HttpClient (forma moderna con inject(), en vez del constructor)
  private http = inject(HttpClient);

  // GET /api/tareas  -> todas las tareas
  listarTodas(): Observable<Tarea[]> {
    return this.http.get<Tarea[]>(this.apiUrl);
  }

  // GET /api/tareas?estado=PENDIENTE  -> filtradas por estado
  listarPorEstado(estado: EstadoTarea): Observable<Tarea[]> {
    return this.http.get<Tarea[]>(`${this.apiUrl}?estado=${estado}`);
  }

  // GET /api/tareas/{id}  -> una tarea por id
  obtenerPorId(id: number): Observable<Tarea> {
    return this.http.get<Tarea>(`${this.apiUrl}/${id}`);
  }

  // POST /api/tareas  -> crear una tarea
  crear(tarea: Tarea): Observable<Tarea> {
    return this.http.post<Tarea>(this.apiUrl, tarea);
  }

  // PUT /api/tareas/{id}  -> actualizar una tarea
  actualizar(id: number, tarea: Tarea): Observable<Tarea> {
    return this.http.put<Tarea>(`${this.apiUrl}/${id}`, tarea);
  }

  // DELETE /api/tareas/{id}  -> eliminar una tarea
  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}