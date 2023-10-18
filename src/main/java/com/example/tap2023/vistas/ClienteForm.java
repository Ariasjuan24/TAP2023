package com.example.tap2023.vistas;

import com.example.tap2023.modelos.CategoriasDAO;
import com.example.tap2023.modelos.ClientesDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ClienteForm  extends Stage {
    private Scene escena;
    private HBox hBox;
    private TextField txtNameCli;
    private Button btnGuardar;
    private CategoriasDAO objCliDAO;
    TableView<CategoriasDAO> tbvClientes;
    public ClienteForm(TableView<ClientesDAO> tbvCli, ClientesDAO objCliDAO){
        this.tbvClientes = tbvCli;
        this.objCliDAO = objCliDAO == null ? new ClientesDAO() : objCliDAO;
        CrearUI();
        escena = new Scene(hBox);
        this.setTitle("Gestión de Clientes");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        txtNameCli = new TextField();
        txtNameCli.setText(objCliDAO.getNombreCliente());
        txtNameCli.setPromptText("Nombre del Cliente");
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> guardarCliente());
        hBox = new HBox(txtNameCli, btnGuardar);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(10));
    }

    private void guardarCliente(){
        objCliDAO.setnombreCliente(txtNameCli.getText());
        if ( objCliDAO.getIdCliente() > 0 )
            objCliDAO.ACTUALIZAR();
        else
            objCliDAO.INSERTAR();
        tbvClientes.setItems(objCliDAO.LISTARCATEGORIAS());
        tbvClientes.refresh();
        this.close();
    }
}
