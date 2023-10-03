package com.example.tap2023.modelos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Conexion {
    private static String server ="localhost";
    private static String user = "admintap";
    private static String pass = "123";
    private static String db = "restaurante";

    public static Connection conexion; // static trataremos de que sea global para no instanciar conexiones y conexiones en la bd
    public static void createConnection(){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mariadb://"+server+":53306/"+db,user,pass);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

