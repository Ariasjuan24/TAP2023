package com.example.tap2023.modelos;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class ClientesDAO {

    private int idCliente;
    private String nombreCliente;
    private String direccionCliente;
    private String telefonoCliente;

    public int getIdCategoria() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }


    public void INSERTAR(){
        try {
            String query = "INSERT INTO tblCategorias" +
                    "(nomCategoria) VALUES('" + this.nombreCliente + "')";
            Statement stmt = Conexion.conexion.createStatement(); //Statement es una instrucción/clase para una consulta a la BD
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void ACTUALIZAR(){
        try{
            String query = "UPDATE tblCliente SET nombreCliente = '"+this.nombreCliente+"' "+
                    "WHERE idCliente = "+this.idCliente;//referencia a DML
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){ //la clase base es exception. Clase padre de las excepciones para nosotros
            e.printStackTrace();//quiere decir que todas las excepciones que se generen aqui, esta exception la va a cachar
        }//porque es la clase padre. Exception abarca IOException, SQLException, etc...(Excepction(clase) e(objeto))
    }
    public void ELIMINAR(){
        try {
            String query = "DELETE FROM tblCliente WHERE idCliente = "+this.idCliente;
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public ObservableList<ClientesDAO> LISTARCATEGORIAS(){
        ObservableList<ClientesDAO> listCli = FXCollections.observableArrayList();
        ClientesDAO objCli;
        try {
            String query = "SELECT * FROM tblClientes";
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                objCli = new ClientesDAO();//cada renglon es un objeto de clientesDAO
                objCli.idCliente = res.getInt("idCliente");
                objCli.nombreCliente = res.getString("nombreCliente");
                listCli.add(objCli);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listCli;
    }


}
