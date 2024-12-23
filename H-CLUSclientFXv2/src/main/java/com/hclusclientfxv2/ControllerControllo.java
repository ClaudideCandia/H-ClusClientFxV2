package com.hclusclientfxv2;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe general-purpose usata come supporto ad altri controller.
 */
public class ControllerControllo {

    private static Stage stage;
    private static Scene scene;
    private static FXMLLoader root;

    /**
     * Metodo che cambia e inizializza una nuova scena.
     * @param event evento che innesca lo switch.
     * @param s nome della scena da caricare.
     * @throws IOException
     */
    public static void switchScene(ActionEvent event , String s) throws IOException {
        switch (s){

        }
        root = new FXMLLoader(APP.class.getResource(s + ".fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root.load());
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Metodo che cambia e inizializza una nuova scena ed imposta un nuovo titolo alla finestra.
     * @param event evento che innesca lo switch.
     * @param s nome della scena da caricare.
     * @param s1 titolo da impostare.
     * @throws IOException
     */
    public static void switchScene(ActionEvent event, String s, String s1) throws IOException {
        root = new FXMLLoader(APP.class.getResource(s + ".fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root.load());
        stage.setScene(scene);
        stage.setTitle(s1);
        stage.show();
    }


}
