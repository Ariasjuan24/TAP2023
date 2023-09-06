package com.example.tap2023.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
public class Calculadora extends Stage {

    private Scene escena;
    private VBox vBox;
    private GridPane grdTeclado;
    private TextField txtResultado;
    private Button[][] arTeclas = new Button[4][4];
    /*las [][] significa que es un arreglo bidimensional*/
    private List<Double> numeros;
    private List<String> operadores;
    private boolean nvoNum;
    private char[] arDigitos = {'7', '8', '9', '/', '4', '5', '6', '*', '1', '2', '3', '-', '0', '.', '=', '+'};


    public Calculadora() {
        CrearUI();
        escena = new Scene(vBox, 200, 200);
        this.setTitle("Calculadora");
        this.setScene(escena);
        this.show();

        numeros = new ArrayList<>();
        operadores = new ArrayList<>();
        nvoNum = true;
    }

    private void CrearUI() {
        int pos = 0;
        grdTeclado = new GridPane();
        txtResultado = new TextField("0");
        txtResultado.setAlignment(Pos.BASELINE_RIGHT);
        txtResultado.setEditable(false);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                arTeclas[i][j] = new Button(arDigitos[pos] + "");
                arTeclas[i][j].setPrefSize(50, 50);
                int finalPos = pos;
                arTeclas[i][j].setOnAction((event) -> GenerarExpresion(arDigitos[finalPos] + ""));
                grdTeclado.add(arTeclas[i][j], i, j);
                pos++;
            }
        }
        vBox = new VBox(txtResultado, grdTeclado);
    }

    private void GenerarExpresion(String simbolo) {
        if (simbolo.matches("[0-9]") || simbolo.equals(".")) {
            if (nvoNum) {
                txtResultado.clear();
                nvoNum = false;
            }
            txtResultado.appendText(simbolo);
        } /*else if (simbolo.equals("C")) {
            txtResultado.clear();
            numeros.clear();
            operadores.clear();
            nvoNum = true;
            }   por si se llegase e implementar el botón para limpiar pantalla*/
        else if (simbolo.equals("=")) {
            try {
                double numeroActual = Double.parseDouble(txtResultado.getText());
                numeros.add(numeroActual);

                double resultado = calcularOperacion();
                txtResultado.setText(Double.toString(resultado));

                numeros.clear();
                operadores.clear();
                numeros.add(resultado);

                nvoNum = true;
            } catch (NumberFormatException e) {
                txtResultado.setText("Error");
            }
        } else {
            operadores.add(simbolo);
            try {
                double numeroActual = Double.parseDouble(txtResultado.getText());
                numeros.add(numeroActual);
            } catch (NumberFormatException e) {
                txtResultado.setText("Error");
            }
            txtResultado.clear();
            nvoNum = true;
        }
    }


    private double calcularOperacion() {
        double resultado = numeros.get(0);
        for (int i = 0; i < operadores.size(); i++) {
            String operador = operadores.get(i);
            double numero = numeros.get(i + 1);
            switch (operador) {
                case "+":
                    resultado += numero;
                    break;
                case "-":
                    resultado -= numero;
                    break;
                case "*":
                    resultado *= numero;
                    break;
                case "/":
                    if (numero != 0) {
                        resultado /= numero;
                    } else {
                        txtResultado.setText("Error");
                        return 0;
                    }
                    break;
                default:
                    break;
            }

        }

        /*switch (operador){
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 != 0) {
                    return num1 / num2;
                    }
                else {
                    txtResultado.setText("Error");
                    return 0;
                }
        }

        return num1;
  */
        return resultado;
    }



}

