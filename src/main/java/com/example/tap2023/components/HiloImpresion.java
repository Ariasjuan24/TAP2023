package com.example.tap2023.components;

import com.example.tap2023.vistas.TareaImpresion;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;

import java.util.Queue;

public class HiloImpresion extends Thread {
    private TableView<TareaImpresion> tablaTareas;
    private ProgressBar barraProgreso;
    private boolean running;
    private Queue<TareaImpresion> colaTareas;

    public HiloImpresion(TableView<TareaImpresion> tablaTareas, ProgressBar barraProgreso, Queue<TareaImpresion> colaTareas) {
        this.tablaTareas = tablaTareas;
        this.barraProgreso = barraProgreso;
        this.colaTareas = colaTareas;
        this.running = false;
    }

    public void iniciar() {
        running = true;
        start();
    }

    @Override
    public void run() {
        while (running) {
            if (!colaTareas.isEmpty()) {
                TareaImpresion tarea = colaTareas.poll();
                // Simulación de impresión
                simularImpresion(tarea);
                // Elimina la tarea después de la simulación
                Platform.runLater(() -> tablaTareas.getItems().remove(tarea));
            }

            try {
                sleep(1000); // Espera para verificar la cola periódicamente
            } catch (InterruptedException e) {
                System.out.println("Hilo de impresión interrumpido.");
                if (!running) {
                    break;
                }
            }
        }
    }

    private void simularImpresion(TareaImpresion tarea) {
        int totalHojas = tarea.getNumeroHojas();
        for (int hoja = 1; hoja <= totalHojas && running; hoja++) {
            double progreso = (double) hoja / totalHojas;
            updateProgressBar(progreso);

            try {
                sleep(100); // Simula el tiempo de impresión por hoja
            } catch (InterruptedException e) {
                System.out.println("Hilo de impresión interrumpido durante la simulación.");
                if (!running) {
                    break;
                }
            }
        }

        // Reinicia la barra de progreso
        updateProgressBar(0);
    }

    private void updateProgressBar(double value) {
        Platform.runLater(() -> barraProgreso.setProgress(value));
    }

    public void detener() {
        running = false;
        interrupt(); // Interrumpe el hilo para salir del bucle de espera
    }

    public boolean isRunning() {
        return running;
    }
}
