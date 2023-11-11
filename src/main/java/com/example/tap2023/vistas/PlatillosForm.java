package com.example.tap2023.vistas;

import com.example.tap2023.modelos.PlatillosDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PlatillosForm extends Stage {
    private Scene escena;
    private HBox hBox;
    private TextField txtNamePla;
    private TextField txtPrecio;
    private Button btnGuardar;
    private PlatillosDAO objPlaDAO;
    private TableView<PlatillosDAO> tbvPlatillos;

    public PlatillosForm(TableView<PlatillosDAO> tbvPla, PlatillosDAO objPlaDAO) {
        this.tbvPlatillos = tbvPla;
        this.objPlaDAO = objPlaDAO == null ? new PlatillosDAO() : objPlaDAO;
        CrearUI();
        // Crear la columna para mostrar idCategoria
        TableColumn<PlatillosDAO, Integer> colIdCategoria = new TableColumn<>("ID Categoría");
        colIdCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria")); // Asegúrate de que tu clase PlatillosDAO tenga un método getIdCategoria()

        // Agregar la columna a la TableView
        tbvPlatillos.getColumns().addAll(colIdCategoria);
        escena = new Scene(hBox);
        this.setTitle("Gestión de Platillos");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtNamePla = new TextField();
        txtNamePla.setText(objPlaDAO.getNomPlatillo());
        txtNamePla.setPromptText("Nombre del platillo");

        txtPrecio = new TextField();
        txtPrecio.setText(Double.toString(objPlaDAO.getPrecio()));

        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> guardarPlatillo());

        hBox = new HBox(txtNamePla, btnGuardar);

        hBox.setSpacing(10);
        hBox.setPadding(new Insets(10));
    }

    private void guardarPlatillo() {
        objPlaDAO.setNomPlatillo(txtNamePla.getText());
        objPlaDAO.setPrecio(Double.parseDouble(txtPrecio.getText())); // Convierte el texto a double
        if (objPlaDAO.getIdPlatillo() > 0)
            objPlaDAO.ACTUALIZAR();
        else
            objPlaDAO.INSERTAR();
        tbvPlatillos.setItems(PlatillosDAO.listarPlatillosDesdeBaseDeDatos());
        tbvPlatillos.refresh();
        this.close();
    }
}
