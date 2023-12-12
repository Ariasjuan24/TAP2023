package com.example.tap2023.components;

import com.example.tap2023.modelos.CategoriasDAO;
import com.example.tap2023.modelos.ElementoRestaurante;
import com.example.tap2023.modelos.PlatillosDAO;
import com.example.tap2023.vistas.CategoriaForm;
import com.example.tap2023.vistas.PlatillosForm;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import javafx.scene.control.ButtonType;


import java.util.Optional;

public class ButtonCell<T extends ElementoRestaurante> extends TableCell<T, String> {
    private Button btnCelda;
    private int opc;
    private TableView<T> tableView;

    public ButtonCell(int opc, TableView<T> tableView) {
        this.opc = opc;
        this.tableView = tableView;
        String txtBtn = this.opc == 1 ? "Editar" : "Eliminar";
        btnCelda = new Button(txtBtn);
        btnCelda.setOnAction(event -> accionBoton());
    }

    private void accionBoton() {
        T obj = getTableView().getItems().get(getIndex());
        if (obj != null) {
            if (opc == 1) {
                if (obj instanceof CategoriasDAO) {
                    new CategoriaForm((TableView<CategoriasDAO>) tableView, (CategoriasDAO) obj);
                } else if (obj instanceof PlatillosDAO) {
                    new PlatillosForm((TableView<PlatillosDAO>) tableView, (PlatillosDAO) obj);
                }
            } else if (opc == 2) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación del Sistema");
                alert.setHeaderText("Confirmación del Sistema");
                alert.setContentText("¿Deseas eliminar el elemento?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if (obj instanceof CategoriasDAO) {
                        ((CategoriasDAO) obj).ELIMINAR();
                        tableView.setItems((ObservableList<T>) ((CategoriasDAO) obj).LISTARCATEGORIAS());
                    } else if (obj instanceof PlatillosDAO) {
                        ((PlatillosDAO) obj).ELIMINAR();
                        tableView.setItems((ObservableList<T>) ((PlatillosDAO) obj).listarPlatillosDesdeBaseDeDatos());
                    }
                }
            }
        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setGraphic(btnCelda);
        } else {
            setGraphic(null);
        }
    }
}
