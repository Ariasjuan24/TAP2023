package com.example.tap2023.modelos;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class CategoriasDAO {

    public int idCategoria; //era private
    public String nomCategoria; //era private

    public void INSERTAR(){
        try {
            String query = "INSERT INTO tblCategorias" +
                    "(nomCategoria) VALUES('" + this.nomCategoria + "')";
            Statement stmt = Conexion.conexion.createStatement(); //Statement es una instrucción/clase para una consulta a la BD
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void ACTUALIZAR(){
        try{
            String query = "UPDATE tblCategorias SET nomCategoria = '"+this.nomCategoria+"' "+
                    "WHERE idCategoria = "+this.idCategoria;//referencia a DML
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){ //la clase base es exception. Clase padre de las excepciones para nosotros
            e.printStackTrace();//quiere decir que todas las excepciones que se generen aqui, esta exception la va a cachar
        }//porque es la clase padre. Exception abarca IOException, SQLException, etc...(Excepction(clase) e(objeto))
    }
    public void ELIMINAR(){
        try {
            String query = "DELETE FROM tblCategorias WHERE idCategoria = "+this.idCategoria;
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public ObservableList<CategoriasDAO> LISTARCATEGORIAS(){
        ObservableList<CategoriasDAO> listCat = FXCollections.observableArrayList();
        CategoriasDAO objC;
        try {
            String query = "SELECT * FROM tblCategorias";
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                objC = new CategoriasDAO();//cada renglon es un objeto de categoriasDAO
                objC.idCategoria = res.getInt("idCategoria");
                objC.nomCategoria = res.getString("nomCategoria");
                listCat.add(objC);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listCat;
    }


}
//CRUD    create read update delete
// cuando defines un Array, la cantidad de espacio preservada para el arreglo es estatica y el Arraylist es dinamico
// el arreglo puede crecer confome uno quiera