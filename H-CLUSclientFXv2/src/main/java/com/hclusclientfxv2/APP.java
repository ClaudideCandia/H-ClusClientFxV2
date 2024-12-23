//definizione  del package.
package com.hclusclientfxv2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.io.IOException;


public class APP extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(APP.class.getResource("menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/immagini/logoUniba.png")));
        stage.setTitle("H-CLUSclientFXv2");
        stage.setScene(scene);
        stage.show();


    }
    //entry point dell'applicazione.
    public static void main(String[] args) {
        launch();//metodo statico ereditato dalla classe javafx.application.Application. È il punto di ingresso
        // per le applicazioni JavaFX e ha il compito di avviare il toolkit JavaFX e configurare l'applicazione.
    }
}