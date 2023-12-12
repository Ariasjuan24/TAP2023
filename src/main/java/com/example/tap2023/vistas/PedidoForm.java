package com.example.tap2023.vistas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.tap2023.modelos.PlatillosDAO;

import java.util.ArrayList;
import java.util.List;

public class PedidoForm extends Stage {

    private TableView<PlatillosDAO> tbvPlatillos;
    private TableView<PlatillosDAO> tbvDetalleVenta;
    private ObservableList<PlatillosDAO> pedidoList;
    private List<PlatillosDAO> productosSeleccionados;

    public PedidoForm(ObservableList<PlatillosDAO> pedidoList) {
        this.pedidoList = pedidoList;
        this.productosSeleccionados = new ArrayList<>();
        CrearUI();
        Scene scene = new Scene(createLayout());
        this.setScene(scene);
        this.setTitle("Selección de Productos");
        this.show();
    }
    private VBox createLayout() {
        VBox root = new VBox();
        root.setSpacing(10);

        // Agregar las tablas y el botón a un VBox
        VBox vBox = new VBox(tbvPlatillos, tbvDetalleVenta, createButtonBox());
        vBox.setSpacing(10);

        // Agregar el VBox a un ScrollPane para permitir el desplazamiento vertical
        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        // Agregar el ScrollPane al VBox principal
        root.getChildren().addAll(scrollPane);
        return root;
    }

    private VBox createButtonBox() {
        VBox buttonBox = new VBox();
        buttonBox.setSpacing(10);

        // Botón para confirmar el pedido
        Button btnConfirmarPedido = new Button("Confirmar Pedido");
        btnConfirmarPedido.setOnAction(event -> confirmarPedido());

        // Agregar el botón al VBox de botones
        buttonBox.getChildren().addAll(btnConfirmarPedido);

        return buttonBox;
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

        // Configuramos tbvDetalleVenta
        tbvDetalleVenta = new TableView<>();

        TableColumn<PlatillosDAO, Integer> tbcIdDetalle = new TableColumn<>("ID");
        tbcIdDetalle.setCellValueFactory(new PropertyValueFactory<>("idPlatillo"));

        TableColumn<PlatillosDAO, String> tbcNomDetalle = new TableColumn<>("Platillo");
        tbcNomDetalle.setCellValueFactory(new PropertyValueFactory<>("nomPlatillo"));

        TableColumn<PlatillosDAO, Double> tbcPrecioDetalle = new TableColumn<>("Precio");
        tbcPrecioDetalle.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<PlatillosDAO, Integer> tbcCantidadDetalle = new TableColumn<>("Cantidad");
        tbcCantidadDetalle.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        TableColumn<PlatillosDAO, Void> tbcEliminarDetalle = new TableColumn<>("Eliminar");
        tbcEliminarDetalle.setCellFactory(param -> new TableCell<>() {
            final Button btnEliminar = new Button("Eliminar");

            {
                btnEliminar.setOnAction(event -> {
                    PlatillosDAO platillo = getTableView().getItems().get(getIndex());
                    productosSeleccionados.remove(platillo);
                    mostrarProductosSeleccionados();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnEliminar);
                }
            }
        });

        tbvDetalleVenta.getColumns().addAll(tbcIdDetalle, tbcNomDetalle, tbcPrecioDetalle, tbcCantidadDetalle, tbcEliminarDetalle);

        // Configuramos el botón "Confirmar Pedido"
        Button btnConfirmarPedido = new Button("Confirmar Pedido");
        btnConfirmarPedido.setOnAction(event -> confirmarPedido());

        // Colocamos tbvDetalleVenta y btnConfirmarPedido en un contenedor VBox
        VBox detallePedidoBox = new VBox(tbvDetalleVenta, btnConfirmarPedido);

        // Colocamos tbvPlatillos y el contenedor anterior en un contenedor HBox
        HBox hBox = new HBox(tbvPlatillos, detallePedidoBox);

        Scene scene = new Scene(hBox);
        this.setScene(scene);
        this.setTitle("Selección de Productos");
        this.show();
    }

    private void confirmarPedido() {
        double precioTotal = calcularPrecioTotal();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pedido Confirmado");
        alert.setHeaderText(null);
        alert.setContentText("Precio Total: $" + precioTotal);

        alert.showAndWait();
    }

    private double calcularPrecioTotal() {
        double precioTotal = 0;

        for (PlatillosDAO platillo : productosSeleccionados) {
            precioTotal += platillo.getPrecio() * platillo.getCantidad();
        }
        return precioTotal;
    }

    private void mostrarProductosSeleccionados() {
        // Implementa la lógica para mostrar los productos seleccionados en tbvDetalleVenta
        tbvDetalleVenta.setItems(FXCollections.observableArrayList(productosSeleccionados));
    }
}
