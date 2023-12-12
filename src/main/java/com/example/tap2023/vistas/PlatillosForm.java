package com.example.tap2023.vistas;

import com.example.tap2023.modelos.CategoriasDAO;
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
    private ComboBox<CategoriasDAO> cmbCategorias; // Agregado ComboBox para seleccionar categorías
    private Button btnGuardar;
    private PlatillosDAO objPlaDAO;
    private TableView<PlatillosDAO> tbvPlatillos;

    public PlatillosForm(TableView<PlatillosDAO> tbvPla, PlatillosDAO objPlaDAO) {
        this.tbvPlatillos = tbvPla;
        this.objPlaDAO = objPlaDAO == null ? new PlatillosDAO() : objPlaDAO;
        CrearUI();

        // Crear la columna para mostrar idCategoria
        TableColumn<PlatillosDAO, Integer> colIdCategoria = new TableColumn<>("ID Categoría");
        colIdCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));

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

        cmbCategorias = new ComboBox<>();
        cmbCategorias.setItems(new CategoriasDAO().LISTARCATEGORIAS());
        cmbCategorias.setCellFactory(param -> new ListCell<CategoriasDAO>() {
            @Override
            protected void updateItem(CategoriasDAO item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getNomCategoria() == null) {
                    setText(null);
                } else {
                    setText(item.getNomCategoria());
                }
            }
        });
        cmbCategorias.setButtonCell(new ListCell<CategoriasDAO>() {
            @Override
            protected void updateItem(CategoriasDAO item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getNomCategoria() == null) {
                    setText(null);
                } else {
                    setText(item.getNomCategoria());
                }
            }
        });

        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> guardarPlatillo());
        //Eliminar la Columna ID Categoria si ya existe en la tabla
        TableColumn<PlatillosDAO, ?> existingColumn = tbvPlatillos.getColumns().stream()
                .filter(col -> "ID Categoría".equals(col.getText()))
                .findFirst()
                .orElse(null);
        if (existingColumn != null) {
            tbvPlatillos.getColumns().remove(existingColumn);
        }

        hBox = new HBox(txtNamePla, txtPrecio, cmbCategorias, btnGuardar); // Agregado ComboBox a la interfaz

        hBox.setSpacing(10);
        hBox.setPadding(new Insets(10));
    }

    private void guardarPlatillo() {
        objPlaDAO.setNomPlatillo(txtNamePla.getText());
        objPlaDAO.setPrecio(Double.parseDouble(txtPrecio.getText()));

        // Verifica si se ha seleccionado una categoría
        CategoriasDAO categoriaSeleccionada = cmbCategorias.getValue();
        if (categoriaSeleccionada != null) {
            objPlaDAO.setCategoria(categoriaSeleccionada);

            // Inserta el nuevo platillo en la base de datos
            objPlaDAO.INSERTAR();

            // Actualiza la tabla después de insertar el platillo
            tbvPlatillos.setItems(PlatillosDAO.listarPlatillosDesdeBaseDeDatos());
            tbvPlatillos.refresh();

            this.close();
        } else {
            // Muestra un mensaje indicando que se debe seleccionar una categoría
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("Selección de Categoría");
            alert.setContentText("Debes seleccionar una categoría para el platillo.");
            alert.showAndWait();
        }
    }


}
