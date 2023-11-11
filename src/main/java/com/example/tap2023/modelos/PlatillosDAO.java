package com.example.tap2023.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlatillosDAO extends ElementoRestaurante {
    private int idPlatillo;
    private String nomPlatillo;
    private int precio;
    private int idCategoria;


    public PlatillosDAO() {
        // Constructor vacío necesario para crear objetos desde la interfaz de usuario.
    }

    public int getIdPlatillo() {
        return idPlatillo;
    }

    public void setIdPlatillo(int idPlatillo) {
        this.idPlatillo = idPlatillo;
    }

    private int cantidad; // Agrega esta propiedad

    // ... otros métodos ...

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNomPlatillo() {
        return nomPlatillo;
    }

    public void setNomPlatillo(String nomPlatillo) {
        this.nomPlatillo = nomPlatillo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
    private CategoriasDAO categoria; // Agregar una referencia a la clase CategoriasDAO

    public CategoriasDAO getCategoria() {
        return categoria;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setCategoria(CategoriasDAO categoria) {
        this.categoria = categoria;
        this.idCategoria = categoria.getIdCategoria(); // Actualizar el ID de la categoría
    }
    public String toString(){
        return "ID: " + idPlatillo + ", Nombre: " + nomPlatillo + ", Precio: " + precio + ", Categoría: " + (categoria != null ? categoria.getIdCategoria() : "");
    }

    public void INSERTAR() {
        try {
            String query = "INSERT INTO tblPlatillos (nomPlatillo, precio, idCategoria) VALUES (?, ?, ?)";
            Connection conn = Conexion.conexion; // Asumiendo que Conexion.conexion es la conexión a la base de datos.
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, this.nomPlatillo);
            pstmt.setDouble(2, this.precio);
            pstmt.setInt(3, this.idCategoria);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ACTUALIZAR() {
        try {
            String query = "UPDATE tblPlatillos SET nomPlatillo = ?, precio = ?, idCategoria = ? WHERE idPlatillo = ?";
            Connection conn = Conexion.conexion; // Asumiendo que Conexion.conexion es la conexión a la base de datos.
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, this.nomPlatillo);
            pstmt.setDouble(2, this.precio);
            pstmt.setInt(3, this.idCategoria);
            pstmt.setInt(4, this.idPlatillo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ELIMINAR() {
        try {
            String query = "DELETE FROM tblPlatillos WHERE idPlatillo = ?";
            Connection conn = Conexion.conexion; // Asumiendo que Conexion.conexion es la conexión a la base de datos.
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, this.idPlatillo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<PlatillosDAO> listarPlatillosDesdeBaseDeDatos() {
        ObservableList<PlatillosDAO> listPla = FXCollections.observableArrayList();
        PlatillosDAO objPla;
        try {
            String query = "SELECT * FROM tblPlatillos";
            Connection conn = Conexion.conexion; // Asumiendo que Conexion.conexion es la conexión a la base de datos.
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                objPla = new PlatillosDAO();
                objPla.idPlatillo = res.getInt("idPlatillo");
                objPla.nomPlatillo = res.getString("nomPlatillo");
                objPla.precio = res.getInt("precio");
                objPla.idCategoria = res.getInt("idCategoria");

                CategoriasDAO categoria = obtenerCategoriaPorID(objPla.idCategoria);
                objPla.setCategoria(categoria);

                listPla.add(objPla);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listPla;
    }

    private static CategoriasDAO obtenerCategoriaPorID(int idCategoria) {
        ObservableList<CategoriasDAO> categorias = new CategoriasDAO().LISTARCATEGORIAS();
        for (CategoriasDAO categoria : categorias) {
            if (categoria.getIdCategoria() == idCategoria) {
                return categoria;
            }
        }
        return null;
    }
}
