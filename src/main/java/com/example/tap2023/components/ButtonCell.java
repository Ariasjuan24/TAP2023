package com.example.tap2023.components;

import com.example.tap2023.modelos.CategoriasDAO;
import com.example.tap2023.modelos.ElementoRestaurante;
import com.example.tap2023.modelos.PlatillosDAO;
import com.example.tap2023.vistas.CategoriaForm;
import com.example.tap2023.vistas.PlatillosForm;
import javafx.scene.control.*;

import java.util.Optional;

public class ButtonCell<T extends ElementoRestaurante> extends TableCell<T, String> {
    private Button btnCelda, btnCelda1;
    private int opc;
    private TableView<CategoriasDAO> tbvCategorias;
    private CategoriasDAO objCat;
    private TableView<PlatillosDAO> tbvPlatillos;
    private PlatillosDAO objPla;

    public ButtonCell(int opc, TableView<PlatillosDAO> tbvPlatillos){
        this.opc = opc;
        String txtBtn = this.opc == 1 ? "Editar" : "Eliminar";
        btnCelda = new Button(txtBtn);
        btnCelda1 = new Button(txtBtn);
        btnCelda.setOnAction(event -> accionBoton());
        btnCelda1.setOnAction(event -> accionBoton1());
    }

    private void accionBoton1() {
        tbvPlatillos = (TableView<PlatillosDAO>) ButtonCell.this.getTableView();
        objPla = tbvPlatillos.getItems().get(ButtonCell.this.getIndex());

        if (this.opc ==1) {
            new PlatillosForm(tbvPlatillos, objPla);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Tópicos Avanzados de Programación");
            alert.setHeaderText("Confirmación del Sistema");
            alert.setContentText("¿Deseas eliminar el Platillo?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                objPla.ELIMINAR();
                tbvPlatillos.setItems(objPla.listarPlatillosDesdeBaseDeDatos());
                tbvPlatillos.refresh();
            }
        }
    }

    private void accionBoton() {
        tbvCategorias = (TableView<CategoriasDAO>) ButtonCell.this.getTableView();
        objCat = tbvCategorias.getItems().get(ButtonCell.this.getIndex());

        if (this.opc ==1) {
            new CategoriaForm(tbvCategorias, objCat);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Tópicos Avanzados de Programación");
            alert.setHeaderText("Confirmación del Sistema");
            alert.setContentText("¿Deseas eliminar la categoría?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                objCat.ELIMINAR();
                tbvCategorias.setItems(objCat.LISTARCATEGORIAS());
                tbvCategorias.refresh();
            }
        }

    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if ( !empty ){
            this.setGraphic(btnCelda);
        }
    }
}
