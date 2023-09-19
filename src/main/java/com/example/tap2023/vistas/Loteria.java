package com.example.tap2023.vistas;

import javafx.scene.control.Alert;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
// antes de la modificación escribí esto
public class Loteria extends Stage {
    private Scene escena;
    private HBox hPrincipal, hBtnSeleccion;
    private VBox vTablilla, vMazo;
    private ImageView imvCarta;
    private Button[][] arBtnTablilla = new Button[4][4];
    private Button btnAnterior, btnSiguiente, btnIniciar, btnStop, btnLimpiarTablilla;
    private GridPane grdTablilla;
    private Timer timer;
    private Set<Integer> imagenesMostradas = new HashSet<>();
    private boolean tablillaBloqueada = false;
    private int clicksEnSiguiente = 0; //Contador de Clicks en ">"
    private int indiceTablillaActual = 0;
    private int botonesMarcados = 0;
    private int cartasMostradas = 0;



    private String[][] tablillasPredefinidas = {
            {
                    "01 Gallo.png", "2loteria.jpg", "3loteria.jpg", "4loteria.jpg",
                    "5loteria.jpg", "6loteria.jpg", "7loteria.jpg", "8loteria.jpg",
                    "9loteria.jpg", "10loteria.jpg", "11loteria.jpg", "12loteria.jpg",
                    "13loteria.jpg", "14loteria.jpg", "15loteria.jpg", "16loteria.jpg"
            },
            {
                    "17loteria.jpg", "18loteria.jpg", "19loteria.jpg", "20loteria.jpg",
                    "21loteria.jpg", "22loteria.jpg", "23loteria.jpg", "24loteria.jpg",
                    "25loteria.jpg", "26loteria.jpg", "27loteria.jpg", "28loteria.jpg",
                    "29loteria.jpg", "30loteria.jpg", "31loteria.jpg", "32loteria.jpg"
            },
            {
                    "33loteria.jpg", "34loteria.jpg", "35loteria.jpg", "36loteria.jpg",
                    "37loteria.jpg", "38loteria.jpg", "39loteria.jpg", "40loteria.jpg",
                    "41loteria.jpg", "42loteria.jpg", "43loteria.jpg", "44loteria.jpg",
                    "45loteria.jpg", "46loteria.jpg", "47loteria.jpg", "48loteria.jpg"
            },
            {
                    "49loteria.jpg", "50loteria.jpg", "51loteria.jpg", "52loteria.jpg",
                    "53loteria.jpg", "54loteria.jpg", "01 Gallo.png", "2loteria.jpg",
                    "3loteria.jpg", "4loteria.jpg", "5loteria.jpg", "6loteria.jpg",
                    "7loteria.jpg", "8loteria.jpg", "9loteria.jpg", "10loteria.jpg"
            },
            {
                    "11loteria.jpg", "12loteria.jpg", "13loteria.jpg", "14loteria.jpg",
                    "15loteria.jpg", "16loteria.jpg", "17loteria.jpg", "18loteria.jpg",
                    "19loteria.jpg", "20loteria.jpg", "21loteria.jpg", "22loteria.jpg",
                    "23loteria.jpg", "24loteria.jpg", "25loteria.jpg", "26loteria.jpg"
            }
    };



    public Loteria() {
        CrearUI();
        escena = new Scene(hPrincipal, 800, 600);
        this.setTitle("Loteria");
        this.setScene(escena);
        this.show();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                final int fila = i;
                final int columna = j;
                arBtnTablilla[i][j].setOnAction(event -> {
                    if (!tablillaBloqueada && !arBtnTablilla[fila][columna].isDisabled()) {
                        arBtnTablilla[fila][columna].setDisable(true);
                        botonesMarcados++;

                        // Verificar si se han marcado todos los botones
                        if (botonesMarcados == 16) {
                            mostrarMensajeGanador();
                        }
                    }
                });
            }
        }

    }

    private void mostrarMensajeGanador() {

        detenerTemporizador();

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("¡Felicidades!");
            alert.setHeaderText(null);
            alert.setContentText("¡Has ganado!");
            alert.showAndWait();
        });
    }

    private void CrearUI() {
        CrearTablilla();
        CrearMazo();

        btnAnterior = new Button("<");
        btnAnterior.setPrefSize(200, 100);


        btnSiguiente = new Button(">");
        btnSiguiente.setPrefSize(200, 100);

        btnAnterior.setDisable(true); //Bloquea el botón en la primera Tablilla
        btnAnterior.setOnAction(event-> mostrarTablillaAnterior()); //Maneja el evento del botón "<"
        btnSiguiente.setOnAction(event -> mostrarTablillaSiguiente());

        hBtnSeleccion = new HBox(btnAnterior, btnSiguiente);
        vTablilla = new VBox(grdTablilla, hBtnSeleccion);
        vTablilla.setSpacing(20);


        hPrincipal = new HBox(vTablilla, vMazo);
        hPrincipal.setPadding(new Insets(20));

        btnIniciar.setOnAction(event -> iniciarTemporizador());
        btnStop.setOnAction(event -> detenerTemporizador());
        btnLimpiarTablilla.setOnAction(event -> limpiarTablilla());

    }

    private void mostrarTablillaSiguiente() {
        if (indiceTablillaActual < tablillasPredefinidas.length - 1) {
            indiceTablillaActual++;
            mostrarTablilla(indiceTablillaActual);
            btnAnterior.setDisable(false);
            clicksEnSiguiente = 0;
        }
        btnSiguiente.setDisable(indiceTablillaActual == tablillasPredefinidas.length - 1);
    }

    private void mostrarTablillaAnterior() {
        if (indiceTablillaActual > 0) {
            indiceTablillaActual--;
            mostrarTablilla(indiceTablillaActual);
            btnSiguiente.setDisable(false);
            clicksEnSiguiente = 0;
        }
        btnAnterior.setDisable(indiceTablillaActual == 0);
    }

    private void mostrarTablilla(int indiceTablillaActual) {
        String[] tablilla = tablillasPredefinidas[indiceTablillaActual];
        int pos = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                try {
                    InputStream stream = new FileInputStream("src/main/java/com/example/tap2023/imagenes/" + tablilla[pos]);
                    Image imgCartaP = new Image(stream);
                    ImageView imv = new ImageView(imgCartaP);
                    arBtnTablilla[i][j].setGraphic(imv);
                    pos++;
                    imv.setFitHeight(120);
                    imv.setFitWidth(80);
                    imv.setPreserveRatio(true);
                    imv.setSmooth(true);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void limpiarTablilla() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (arBtnTablilla[i][j].isDisabled()) {
                    arBtnTablilla[i][j].setDisable(false);
                }
            }
        }
        tablillaBloqueada = false;
        btnIniciar.setText("Iniciar");
        detenerTemporizador();

        Image imgDorso = new Image(new File(
                "src/main/java/com/example/tap2023/imagenes/Dorso.JPG").toURI().toString());
        imvCarta.setImage(imgDorso);
    }

    private void detenerTemporizador() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        btnIniciar.setDisable(false);
        btnStop.setDisable(true);
        btnSiguiente.setDisable(false);
        btnAnterior.setDisable(false);
    }

    private void iniciarTemporizador() {
        btnIniciar.setDisable(true);
        btnStop.setDisable(false);
        btnAnterior.setDisable(true);
        btnSiguiente.setDisable(true);


        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> cambiarImagenAleatoria());
            }
        }, 0, 1000);
    }

    private void cambiarImagenAleatoria() {
        Random rand = new Random();
        int indiceAleatorio;
        String[] arImagenes = {"01 Gallo.png", "2loteria.jpg", "3loteria.jpg", "4loteria.jpg", "5loteria.jpg",
                "6loteria.jpg", "7loteria.jpg", "8loteria.jpg", "9loteria.jpg", "10loteria.jpg",
                "11loteria.jpg", "12loteria.jpg", "13loteria.jpg", "14loteria.jpg", "15loteria.jpg",
                "16loteria.jpg", "17loteria.jpg", "18loteria.jpg", "19loteria.jpg", "20loteria.jpg",
                "21loteria.jpg", "22loteria.jpg", "23loteria.jpg", "24loteria.jpg", "25loteria.jpg",
                "26loteria.jpg", "27loteria.jpg", "28loteria.jpg", "29loteria.jpg", "30loteria.jpg",
                "31loteria.jpg", "32loteria.jpg", "33loteria.jpg", "34loteria.jpg", "35loteria.jpg",
                "36loteria.jpg", "37loteria.jpg", "38loteria.jpg", "39loteria.jpg", "40loteria.jpg",
                "41loteria.jpg", "42loteria.jpg", "43loteria.jpg", "44loteria.jpg", "45loteria.jpg",
                "46loteria.jpg", "47loteria.jpg", "48loteria.jpg", "49loteria.jpg", "50loteria.jpg",
                "51loteria.jpg", "52loteria.jpg", "53loteria.jpg", "54loteria.jpg"};
        do {
            indiceAleatorio = rand.nextInt(arImagenes.length);
        } while (imagenesMostradas.contains(indiceAleatorio));

        imagenesMostradas.add(indiceAleatorio);

        if (imagenesMostradas.size() == arImagenes.length) {
            imagenesMostradas.clear();
        }

        // Incrementar el contador de cartas mostradas
        cartasMostradas++;

        // Verificar si se han mostrado todas las cartas sin marcar todos los botones
        if (cartasMostradas == arImagenes.length && botonesMarcados < 16) {
            mostrarMensajePerdedor();
        }

        try {
            InputStream stream = new FileInputStream("src/main/java/com/example/tap2023/imagenes/" + arImagenes[indiceAleatorio]);
            Image imgCartaP = new Image(stream);
            imvCarta.setImage(imgCartaP);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void mostrarMensajePerdedor() {
        // Detener el temporizador si está en funcionamiento
        detenerTemporizador();

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("¡Has perdido!");
            alert.setHeaderText(null);
            alert.setContentText("Has perdido el juego.");
            alert.showAndWait();
        });
    }


    private void CrearMazo() {
        Image imgDorso = new Image(new File("src/main/java/com/example/tap2023/imagenes/Dorso.JPG").toURI().toString());
        imvCarta = new ImageView(imgDorso);
        imvCarta.setFitWidth(200);
        imvCarta.setFitHeight(300);
        btnIniciar = new Button("Iniciar");
        btnStop = new Button("Detener");
        btnStop.setDisable(true);
        btnLimpiarTablilla = new Button("Limpiar");
        vMazo = new VBox(imvCarta, btnIniciar, btnStop, btnLimpiarTablilla);
    }

    private void CrearTablilla() {

        String[] arImagenes = {"01 Gallo.png", "2loteria.jpg", "3loteria.jpg", "4loteria.jpg", "5loteria.jpg",
                "6loteria.jpg", "7loteria.jpg", "8loteria.jpg", "9loteria.jpg", "10loteria.jpg",
                "11loteria.jpg", "12loteria.jpg", "13loteria.jpg", "14loteria.jpg", "15loteria.jpg",
                "16loteria.jpg", "17loteria.jpg", "18loteria.jpg", "19loteria.jpg", "20loteria.jpg",
                "21loteria.jpg", "22loteria.jpg", "23loteria.jpg", "24loteria.jpg", "25loteria.jpg",
                "26loteria.jpg", "27loteria.jpg", "28loteria.jpg", "29loteria.jpg", "30loteria.jpg",
                "31loteria.jpg", "32loteria.jpg", "33loteria.jpg", "34loteria.jpg", "35loteria.jpg",
                "36loteria.jpg", "37loteria.jpg", "38loteria.jpg", "39loteria.jpg", "40loteria.jpg",
                "41loteria.jpg", "42loteria.jpg", "43loteria.jpg", "44loteria.jpg", "45loteria.jpg",
                "46loteria.jpg", "47loteria.jpg", "48loteria.jpg", "49loteria.jpg", "50loteria.jpg",
                "51loteria.jpg", "52loteria.jpg", "53loteria.jpg", "54loteria.jpg"};

        List<String> listaImagenes = Arrays.asList(arImagenes);
        Collections.shuffle(listaImagenes);
        arImagenes = listaImagenes.toArray(new String[0]);

        //String[] tablillaActual = Arrays.copyOf(arImagenes, arImagenes.length);
        //todasTablillas.add(tablillaActual);


        grdTablilla = new GridPane();
        int pos = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ImageView imv;

                try {
                    InputStream stream = new FileInputStream("src/main/java/com/example/tap2023/imagenes/" + arImagenes[pos]);
                    Image imgCartaP = new Image(stream);
                    imv = new ImageView(imgCartaP);
                    pos++;
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                imv.setFitHeight(120);
                imv.setFitWidth(80);
                imv.setPreserveRatio(true); // Mantener la proporción de la imagen
                imv.setSmooth(true); // Mejorar la calidad de visualización

                arBtnTablilla[i][j] = new Button();
                arBtnTablilla[i][j].setGraphic(imv);

                arBtnTablilla[i][j].setPrefSize(100, 140);
                //grdTablilla.add(arBtnTablilla[i][j], i, j);

                final int fila = i;
                final int columna = j;
                arBtnTablilla[i][j].setOnAction(event -> {
                    if (!tablillaBloqueada && !arBtnTablilla[fila][columna].isDisabled()) {
                        // Realizar acciones al hacer clic en un botón de la tablilla
                        // Por ejemplo, cambiar el estilo o realizar alguna acción específica
                        // Bloquear solo el botón clickeado
                        arBtnTablilla[fila][columna].setDisable(true);
                    }
                });

                grdTablilla.add(arBtnTablilla[i][j], i, j);
            }

        }
    }
}
