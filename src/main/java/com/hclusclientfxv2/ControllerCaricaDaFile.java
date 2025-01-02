package com.hclusclientfxv2;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.io.IOException;

import static com.hclusclientfxv2.ControllerControllo.switchScene;

/**
 * Classe che gestisce il comportamento della schermata dell'interfaccia corrispondente.
 * Fornisce la logica per le azioni degli utenti, come tornare alla home e caricare un file.
 */
public class ControllerCaricaDaFile {

    /** Pulsante per confermare la scelta del file. */
    @FXML
    Button confermaSceltaFile;

    /** Pulsante per tornare alla schermata principale (home). */
    @FXML
    Button home;

    /** Etichetta per indicare lo stato o fornire istruzioni all'utente. */
    @FXML
    Label etichetta1caricaDaFile;

    /** Campo di testo in cui l'utente inserisce il percorso del file da caricare. */
    @FXML
    TextField fieldFile;

    /** Etichetta per mostrare il risultato del caricamento o eventuali errori. */
    @FXML
    Label etichettaRisultato;

    /** Area di testo per visualizzare i risultati ricevuti dal server. */
    @FXML
    TextArea textAreaRisultati;

    /** Istanza del client per la comunicazione con il server. */
    ClientFx temp;

    /**
     * Metodo di inizializzazione che configura i comportamenti dei pulsanti e delle azioni correlate.
     */
    public void initialize() {
        // Ottiene un riferimento al client per la comunicazione con il server.
        ClientFx rifC = temp.ottieniClient();

        // Configura l'azione del pulsante "home" per tornare alla schermata principale.
        home.setOnAction((ActionEvent event) -> {
            try {
                switchScene(event, "/com/hclusclientfxv2/menu");
                rifC.sendToServer("home");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Configura l'azione del pulsante "confermaSceltaFile" per gestire il caricamento del file.
        confermaSceltaFile.setOnAction((ActionEvent e) -> {
            String esito = fieldFile.getText();

            // Controlla se il campo di testo è vuoto.
            if (esito.equals("")) {
                etichettaRisultato.setText("file non valido!");
                etichettaRisultato.setStyle("-fx-text-fill: red");

                // Imposta un messaggio temporaneo per l'utente.
                Timeline timeline = new Timeline(new KeyFrame(
                        Duration.seconds(3.5),
                        event -> etichettaRisultato.setText("")
                ));
                timeline.setCycleCount(1);
                timeline.play();
            } else {
                // Implementa la logica di caricamento del file.
                String nomeFile = fieldFile.getText();
                rifC.sendToServer(nomeFile);

                // Riceve il risultato dal server e lo visualizza nell'area di testo.
                String risultato = (String) rifC.receiveFromServer();
                textAreaRisultati.setText(risultato);
            }
        });
    }
}

