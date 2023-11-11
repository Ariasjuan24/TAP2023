package com.example.tap2023.vistas;

import com.example.tap2023.components.HiloImpresion;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class SimuladorImpresion extends Stage {
    private TableView<TareaImpresion> tablaTareas;
    private Button btnAgregarTarea;
    private Button btnIniciarDetener;
    private ProgressBar barraProgreso;
    private HiloImpresion hiloImpresion;
    private Queue<TareaImpresion> colaTareas;

    public SimuladorImpresion() {
        CrearUI();
        Scene escena = new Scene(new VBox(tablaTareas, new HBox(btnAgregarTarea, btnIniciarDetener), barraProgreso), 400, 300);
        this.setTitle("Simulador de Impresión");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tablaTareas = new TableView<>();
        inicializarTabla();

        btnAgregarTarea = new Button("Agregar Tarea");
        btnAgregarTarea.setOnAction(event -> agregarTarea());

        btnIniciarDetener = new Button("Iniciar Simulador");
        btnIniciarDetener.setOnAction(event -> alternarSimulacion());

        barraProgreso = new ProgressBar(0);

        colaTareas = new LinkedList<>();
    }

    private void inicializarTabla() {
        TableColumn<TareaImpresion, Integer> colNoArchivo = new TableColumn<>("No. Archivo");
        TableColumn<TareaImpresion, String> colNombreArchivo = new TableColumn<>("Nombre de Archivo");
        TableColumn<TareaImpresion, Integer> colNumHojas = new TableColumn<>("Número de Hojas");
        TableColumn<TareaImpresion, Date> colHoraAcceso = new TableColumn<>("Hora de Acceso");

        colNoArchivo.setCellValueFactory(cellData -> cellData.getValue().numeroArchivoProperty().asObject());
        colNombreArchivo.setCellValueFactory(cellData -> cellData.getValue().nombreArchivoProperty());
        colNumHojas.setCellValueFactory(cellData -> cellData.getValue().numeroHojasProperty().asObject());
        colHoraAcceso.setCellValueFactory(cellData -> cellData.getValue().horaAccesoProperty());

        tablaTareas.getColumns().addAll(colNoArchivo, colNombreArchivo, colNumHojas, colHoraAcceso);
    }

    private void agregarTarea() {
        int numeroArchivo = (int) (Math.random() * 1000);
        String nombreArchivo = generarNombreAleatorio();
        int numeroHojas = new Random().nextInt(50) + 1;
        Date horaAcceso = new Date();

        TareaImpresion nuevaTarea = new TareaImpresion(numeroArchivo, nombreArchivo, numeroHojas, horaAcceso);
        colaTareas.add(nuevaTarea);

        // Agregar la tarea a la tabla visualmente
        tablaTareas.getItems().add(nuevaTarea);
    }

    private String generarNombreAleatorio() {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fechaActual = formatoFecha.format(new Date());
        return "Archivo_" + fechaActual + ".txt";
    }

    private void alternarSimulacion() {
        if (hiloImpresion == null || !hiloImpresion.isRunning()) {
            hiloImpresion = new HiloImpresion(tablaTareas, barraProgreso, colaTareas);
            hiloImpresion.iniciar();
            btnIniciarDetener.setText("Detener Simulador");
        } else {
            hiloImpresion.detener();
            btnIniciarDetener.setText("Iniciar Simulador");
        }
    }
}
