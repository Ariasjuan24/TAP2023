package com.example.tap2023.vistas;

import com.example.tap2023.components.ButtonCell;
import com.example.tap2023.modelos.CategoriasDAO;
import com.example.tap2023.modelos.ElementoRestaurante;
import com.example.tap2023.modelos.PlatillosDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class Restaurante extends Stage {

    private VBox vBox;
    private TableView<CategoriasDAO> tbvCategorias;
    private TableView<PlatillosDAO> tbvPlatillos;
    private Button btnAgregar, btnAgregar2, btnRealizarPedido;
    private CategoriasDAO categoriasDAO;
    private PlatillosDAO platillosDAO;
    private ObservableList<PlatillosDAO> pedidoList;

    public Restaurante() {
        CrearUI();
        Panel panel = new Panel("Taquería el Tecno");
        panel.getStyleClass().add("panel-primary");
        BorderPane content = new BorderPane();
        content.setPadding(new Insets(20));
        Button button = new Button("Hello BootstrapFX");
        button.getStyleClass().setAll("btn", "btn-success");

        btnRealizarPedido = new Button("Realizar Pedido");
        btnRealizarPedido.getStyleClass().setAll("btn", "btn-success");
        btnRealizarPedido.setOnAction(event -> mostrarPantallaPedido());

        pedidoList = FXCollections.observableArrayList();
        vBox.getChildren().add(btnRealizarPedido);

        content.setCenter(vBox);
        panel.setBody(content);

        Scene scene = new Scene(panel);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        this.setTitle("BootstrapFX");
        this.setScene(scene);
        this.sizeToScene();
        this.show();
    }

    private void CrearUI() {
        categoriasDAO = new CategoriasDAO();
        tbvCategorias = new TableView<CategoriasDAO>();
        platillosDAO = new PlatillosDAO();
        tbvPlatillos = new TableView<PlatillosDAO>();
        CrearTable();
        btnAgregar = new Button("Agregar");
        btnAgregar.getStyleClass().setAll("btn", "btn-success");
        btnAgregar2 = new Button("Agregar");
        btnAgregar2.getStyleClass().setAll("btn", "btn-success");
        btnAgregar.setOnAction(event -> new CategoriaForm(tbvCategorias, null));
        btnAgregar2.setOnAction(event -> new PlatillosForm(tbvPlatillos, null));
        vBox = new VBox(tbvCategorias, btnAgregar, tbvPlatillos, btnAgregar2);
    }

    private void mostrarPantallaPedido() {
        // Abre una nueva ventana para gestionar el pedido
        PedidoForm pedidoForm = new PedidoForm(pedidoList);
    }

    private void CrearTable() {
        TableColumn<CategoriasDAO, Integer> tbcIdCat = new TableColumn<>("ID");
        tbcIdCat.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));

        TableColumn<CategoriasDAO, String> tbcNomCat = new TableColumn<>("Categoria");
        tbcNomCat.setCellValueFactory(new PropertyValueFactory<>("nomCategoria"));

        TableColumn<CategoriasDAO, String> tbcEditar = new TableColumn<>("EDITAR");
        tbcEditar.setCellFactory(
                new Callback<TableColumn<CategoriasDAO, String>, TableCell<CategoriasDAO, String>>() {
                    @Override
                    public TableCell<CategoriasDAO, String> call(TableColumn<CategoriasDAO, String> param) {
                        return new ButtonCell(1, tbvCategorias);
                    }
                }
        );

        TableColumn<CategoriasDAO, String> tbcEliminar = new TableColumn<>("ELIMINAR");
        tbcEliminar.setCellFactory(
                new Callback<TableColumn<CategoriasDAO, String>, TableCell<CategoriasDAO, String>>() {
                    @Override
                    public TableCell<CategoriasDAO, String> call(TableColumn<CategoriasDAO, String> param) {
                        return new ButtonCell(2, tbvCategorias);
                    }
                }
        );

        tbvCategorias.getColumns().addAll(tbcIdCat, tbcNomCat, tbcEditar, tbcEliminar);
        tbvCategorias.setItems(categoriasDAO.LISTARCATEGORIAS());

        TableColumn<PlatillosDAO, Integer> tbcIdPlatillo = new TableColumn<>("ID");
        tbcIdPlatillo.setCellValueFactory(new PropertyValueFactory<>("idPlatillo"));

        TableColumn<PlatillosDAO, String> tbcNomPlatillo = new TableColumn<>("Platillo");
        tbcNomPlatillo.setCellValueFactory(new PropertyValueFactory<>("nomPlatillo"));

        TableColumn<PlatillosDAO, Double> tbcPrecio = new TableColumn<>("Precio");
        tbcPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<PlatillosDAO, Integer> tbcIdCategoria = new TableColumn<>("ID Categoría");
        tbcIdCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));

        TableColumn<PlatillosDAO, String> tbcEditarPlatillo = new TableColumn<>("EDITAR");
        tbcEditarPlatillo.setCellFactory(
                new Callback<TableColumn<PlatillosDAO, String>, TableCell<PlatillosDAO, String>>() {
                    @Override
                    public TableCell<PlatillosDAO, String> call(TableColumn<PlatillosDAO, String> param) {
                        return new ButtonCell(1, tbvPlatillos);
                    }
                }
        );

        TableColumn<PlatillosDAO, String> tbcEliminarPlatillo = new TableColumn<>("ELIMINAR");
        tbcEliminarPlatillo.setCellFactory(
                new Callback<TableColumn<PlatillosDAO, String>, TableCell<PlatillosDAO, String>>() {
                    @Override
                    public TableCell<PlatillosDAO, String> call(TableColumn<PlatillosDAO, String> param) {
                        return new ButtonCell(2, tbvPlatillos);
                    }
                }
        );

        tbvPlatillos.getColumns().addAll(tbcIdPlatillo, tbcNomPlatillo, tbcPrecio, tbcIdCategoria, tbcEditarPlatillo, tbcEliminarPlatillo);
        tbvPlatillos.setItems(platillosDAO.listarPlatillosDesdeBaseDeDatos());
    }
}
