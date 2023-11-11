package com.example.tap2023.vistas;

import javafx.beans.property.*;

import java.util.Date;

public class TareaImpresion {
    private final IntegerProperty numeroArchivo;
    private final StringProperty nombreArchivo;
    private final IntegerProperty numeroHojas;
    private final ObjectProperty<Date> horaAcceso;

    public TareaImpresion(int numeroArchivo, String nombreArchivo, int numeroHojas, Date horaAcceso) {
        this.numeroArchivo = new SimpleIntegerProperty(numeroArchivo);
        this.nombreArchivo = new SimpleStringProperty(nombreArchivo);
        this.numeroHojas = new SimpleIntegerProperty(numeroHojas);
        this.horaAcceso = new SimpleObjectProperty<>(horaAcceso);
    }

    public int getNumeroArchivo() {
        return numeroArchivo.get();
    }

    public IntegerProperty numeroArchivoProperty() {
        return numeroArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo.get();
    }

    public StringProperty nombreArchivoProperty() {
        return nombreArchivo;
    }

    public int getNumeroHojas() {
        return numeroHojas.get();
    }

    public IntegerProperty numeroHojasProperty() {
        return numeroHojas;
    }

    public Date getHoraAcceso() {
        return horaAcceso.get();
    }

    public ObjectProperty<Date> horaAccesoProperty() {
        return horaAcceso;
    }
}
