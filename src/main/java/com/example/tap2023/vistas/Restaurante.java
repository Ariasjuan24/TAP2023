package com.example.tap2023.vistas;

import com.example.tap2023.components.ButtonCell;
import com.example.tap2023.modelos.CategoriasDAO;
import com.example.tap2023.modelos.ClientesDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class Restaurante extends Stage {

    private VBox vBox;
    private TableView<CategoriasDAO> tbvCategorias;
    private TableView<ClientesDAO> tbvClientes;
    private Button btnAgregar;
    private CategoriasDAO categoriasDAO; //si quiero usar una clase de otra parte solo la instanciamos
    private ClientesDAO clientesDAO;

    public Restaurante() {
        CrearUI();
        Panel panel = new Panel("Restaurante");
        panel.getStyleClass().add("panel-primary");                            //(2)
        BorderPane content = new BorderPane();
        content.setPadding(new Insets(20));
        //Button button = new Button("Hello BootstrapFX");
        //button.getStyleClass().setAll("btn","btn-success");                     //(2)
        content.setCenter(vBox);
        panel.setBody(content);

        Scene scene = new Scene(panel);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());       //(3)

        this.setTitle("BootstrapFX");
        this.setScene(scene);
        this.sizeToScene();
        this.show();
    }
    private void CrearUI(){

        categoriasDAO = new CategoriasDAO();
        tbvCategorias = new TableView<CategoriasDAO>();
        clientesDAO = new ClientesDAO();
        tbvClientes = new TableView<ClientesDAO>();
        CrearTable();
        btnAgregar = new Button("Agregar");
        btnAgregar.getStyleClass().setAll("btn","btn-success");
        btnAgregar.setOnAction(event -> new CategoriaForm(tbvCategorias, null));
        btnAgregar.setOnAction(event -> new ClienteForm(tbvClientes, null));
        vBox = new VBox(tbvCategorias, btnAgregar, tbvClientes);
    }

    private void CrearTable(){// generamos este metodo porque hay varias instrucciones que tenemos que definir
        TableColumn<CategoriasDAO, Integer> tbcIdCat = new TableColumn<>("ID");
        tbcIdCat.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));

        TableColumn<CategoriasDAO, Integer> tbcNomCat = new TableColumn<>("Categoria");
        tbcNomCat.setCellValueFactory(new PropertyValueFactory<>("nomCategoria"));

        TableColumn<CategoriasDAO, String> tbcEditar = new TableColumn<CategoriasDAO, String>("EDITAR");
        tbcEditar.setCellFactory(
                new Callback<TableColumn<CategoriasDAO, String>, TableCell<CategoriasDAO, String>>() {
                    @Override
                    public TableCell<CategoriasDAO, String> call(TableColumn<CategoriasDAO, String> param) {
                        return new ButtonCell(1);
                    }
                }
        );

        TableColumn<CategoriasDAO, String> tbcEliminar = new TableColumn<CategoriasDAO, String>("ELIMINAR");
        tbcEliminar.setCellFactory(
                new Callback<TableColumn<CategoriasDAO, String>, TableCell<CategoriasDAO, String>>() {
                    @Override
                    public TableCell<CategoriasDAO, String> call(TableColumn<CategoriasDAO, String> param) {
                        return new ButtonCell(2);
                    }
                }
        );



        tbvCategorias.getColumns().addAll(tbcIdCat, tbcNomCat, tbcEditar, tbcEliminar);
        tbvCategorias.setItems(categoriasDAO.LISTARCATEGORIAS());
    }

    private void CrearTable1(){// generamos este metodo porque hay varias instrucciones que tenemos que definir
        TableColumn<ClientesDAO, Integer> tbcIdCli = new TableColumn<>("ID");
        tbcIdCli.setCellValueFactory(new PropertyValueFactory<>("idCliente"));

        TableColumn<ClientesDAO, Integer> tbcNomCli = new TableColumn<>("Cliente");
        tbcNomCli.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));

        TableColumn<ClientesDAO, String> tbcEditar = new TableColumn<ClientesDAO, String>("EDITAR");
        tbcEditar.setCellFactory(
                new Callback<TableColumn<ClientesDAO, String>, TableCell<ClientesDAO, String>>() {
                    @Override
                    public TableCell<ClientesDAO, String> call(TableColumn<ClientesDAO, String> param) {
                        return new ButtonCell(1);
                    }
                }
        );

        TableColumn<ClientesDAO, String> tbcEliminar = new TableColumn<ClientesDAO, String>("ELIMINAR");
        tbcEliminar.setCellFactory(
                new Callback<TableColumn<ClientesDAO, String>, TableCell<ClientesDAO, String>>() {
                    @Override
                    public TableCell<ClientesDAO, String> call(TableColumn<ClientesDAO, String> param) {
                        return new ButtonCell(2);
                    }
                }
        );



        tbvClientes.getColumns().addAll(tbcIdCli, tbcNomCli, tbcEditar, tbcEliminar);
        tbvClientes.setItems(clientesDAO.LISTARCATEGORIAS());
    }

}
