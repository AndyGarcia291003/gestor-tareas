package com.andy.gestor_tareas;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)   // activa Mockito en esta clase de prueba
class TareaServiceTest {

    @Mock
    private TareaRepository tareaRepository;   // repositorio FALSO (no toca la BD real)

    @InjectMocks
    private TareaService tareaService;         // el service real, con el mock inyectado

    @Test
    void listarTodas_devuelveLaListaDelRepositorio() {
        // ARRANGE (preparar): le decimos al mock qué debe devolver
        Tarea t1 = new Tarea();
        t1.setTitulo("Tarea 1");
        Tarea t2 = new Tarea();
        t2.setTitulo("Tarea 2");
        when(tareaRepository.findAll()).thenReturn(List.of(t1, t2));

        // ACT (actuar): llamamos al método que queremos probar
        List<Tarea> resultado = tareaService.listarTodas();

        // ASSERT (verificar): comprobamos que el resultado es el esperado
        assertEquals(2, resultado.size());
        verify(tareaRepository).findAll();   // confirma que el service sí llamó al repositorio
    }

    @Test
    void obtenerPorId_cuandoExiste_devuelveLaTarea() {
        // ARRANGE
        Tarea tarea = new Tarea();
        tarea.setId(1L);
        tarea.setTitulo("Existe");
        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));

        // ACT
        Tarea resultado = tareaService.obtenerPorId(1L);

        // ASSERT
        assertNotNull(resultado);
        assertEquals("Existe", resultado.getTitulo());
    }

    @Test
    void obtenerPorId_cuandoNoExiste_lanzaExcepcion() {
        // ARRANGE: el mock devuelve vacío (no encontró la tarea)
        when(tareaRepository.findById(99L)).thenReturn(Optional.empty());

        // ACT + ASSERT: verificamos que se lanza la excepción correcta
        assertThrows(RecursoNoEncontradoException.class, () -> {
            tareaService.obtenerPorId(99L);
        });
    }

    @Test
    void crear_guardaYdevuelveLaTarea() {
        // ARRANGE
        Tarea nueva = new Tarea();
        nueva.setTitulo("Nueva tarea");
        when(tareaRepository.save(nueva)).thenReturn(nueva);

        // ACT
        Tarea resultado = tareaService.crear(nueva);

        // ASSERT
        assertEquals("Nueva tarea", resultado.getTitulo());
        verify(tareaRepository).save(nueva);
    }

    @Test
    void eliminar_cuandoNoExiste_lanzaExcepcion() {
        // ARRANGE: el mock dice que esa tarea no existe
        when(tareaRepository.existsById(99L)).thenReturn(false);

        // ACT + ASSERT
        assertThrows(RecursoNoEncontradoException.class, () -> {
            tareaService.eliminar(99L);
        });
        // Y confirmamos que NUNCA intentó borrar
        verify(tareaRepository, never()).deleteById(any());
    }
}