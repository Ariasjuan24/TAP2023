package com.example.tap2023.vistas;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import com.example.tap2023.modelos.PlatillosDAO;

import java.util.ArrayList;
import java.util.List;

public class PedidoForm extends Stage {

    private TableView<PlatillosDAO> tbvPlatillos;
    private ObservableList<PlatillosDAO> pedidoList;
    private List<PlatillosDAO> productosSeleccionados;

    public PedidoForm(ObservableList<PlatillosDAO> pedidoList) {
        this.pedidoList = pedidoList;
        this.productosSeleccionados = new ArrayList<>();
        CrearUI();
        Scene scene = new Scene(new HBox(tbvPlatillos));
        this.setScene(scene);
        this.setTitle("Selección de Productos");
        this.show();
    }

    private void CrearUI() {
        tbvPlatillos = new TableView<>();
        TableColumn<PlatillosDAO, Integer> tbcIdPlatillo = new TableColumn<>("ID");
        tbcIdPlatillo.setCellValueFactory(new PropertyValueFactory<>("idPlatillo"));

        TableColumn<PlatillosDAO, String> tbcNomPlatillo = new TableColumn<>("Platillo");
        tbcNomPlatillo.setCellValueFactory(new PropertyValueFactory<>("nomPlatillo"));



        TableColumn<PlatillosDAO, Double> tbcPrecio = new TableColumn<>("Precio");
        tbcPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<PlatillosDAO, Integer> tbcCantidad = new TableColumn<>("Cantidad");
        tbcCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        TableColumn<PlatillosDAO, Void> tbcAgregar = new TableColumn<>("Agregar");
        tbcAgregar.setCellFactory(param -> new TableCell<>() {
            final Button btnPlus = new Button("+");
            final Button btnMinus = new Button("-");
            final Button btnAgregar = new Button("Agregar");

            {
                btnPlus.setOnAction(event -> {
                    PlatillosDAO platillo = getTableView().getItems().get(getIndex());
                    platillo.setCantidad(platillo.getCantidad() + 1);
                    tbvPlatillos.refresh();
                });

                btnMinus.setOnAction(event -> {
                    PlatillosDAO platillo = getTableView().getItems().get(getIndex());
                    if (platillo.getCantidad() > 0) {
                        platillo.setCantidad(platillo.getCantidad() - 1);
                        tbvPlatillos.refresh();
                    }
                });

                btnAgregar.setOnAction(event -> {
                    PlatillosDAO platillo = getTableView().getItems().get(getIndex());
                    if (platillo.getCantidad() > 0) {
                        productosSeleccionados.add(platillo);
                        mostrarProductosSeleccionados();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(btnPlus, btnMinus, btnAgregar);
                    buttons.setSpacing(5);
                    setGraphic(buttons);
                }
            }
        });

        tbvPlatillos.getColumns().addAll(tbcIdPlatillo, tbcNomPlatillo, tbcPrecio, tbcCantidad, tbcAgregar);
        tbvPlatillos.setItems(PlatillosDAO.listarPlatillosDesdeBaseDeDatos());
    }

    private void mostrarProductosSeleccionados() {
        // Implementa la lógica para mostrar los productos seleccionados
        System.out.println("Productos Seleccionados:");
        for (PlatillosDAO platillo : productosSeleccionados) {
            System.out.println("Producto: " + platillo.getNomPlatillo());
            System.out.println("Cantidad: " + platillo.getCantidad());
            System.out.println("------------------------");
        }
    }
}
