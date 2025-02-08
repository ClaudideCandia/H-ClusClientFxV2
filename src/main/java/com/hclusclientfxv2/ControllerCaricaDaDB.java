package com.hclusclientfxv2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.List;

import static com.hclusclientfxv2.ClientFx.ottieniClient;
import static com.hclusclientfxv2.ControllerControllo.switchScene;

/**
 * Controller della scena "Carica Da DB".
 * <p>
 * Gestisce la logica dell'interfaccia grafica per caricare dati da un database,
 * permettendo la selezione di tabelle, la configurazione dei parametri per il clustering,
 * e l'invio delle richieste al server tramite la classe {@link ClientFx}.
 * </p>
 */
public class ControllerCaricaDaDB {

    @FXML
    private Label label1DaDB;
    @FXML
    private Label labelSelezioneSLD;
    @FXML
    private Label labelSelezioneALD;
    @FXML
    private ComboBox<String> comboTabelle;
    @FXML
    private Button singleLinkDistanceButton;
    @FXML
    private Button avarageLinkDistanceButton;
    @FXML
    private Button salvaButton;
    @FXML
    private Button confermaDB;
    @FXML
    private Button home;
    @FXML
    private TextArea textAreaDB;
    @FXML
    private TextField fieldDepth;

    int mode =0; // Modalità di clustering: 1 = Single Link, 2 = Average Link
/*
    private boolean Single = false;
    private boolean Avarage = false;

 */

    /**
     * Restituisce la ComboBox utilizzata per selezionare le tabelle del database.
     *
     * @return la ComboBox contenente la lista delle tabelle.
     */
    public ComboBox<String> getComboTabelle() {
        return comboTabelle;
    }

    /**
     * Metodo di inizializzazione della scena.
     * <p>
     * Configura le azioni dei pulsanti, inizializza il client {@link ClientFx} e
     * popola la ComboBox con le tabelle ricevute dal server. Gestisce anche la logica
     * di selezione dei metodi di clustering e di interazione con il server.
     * </p>
     */
    public void initialize() {
        //disabilito il pulsante salva per prevenire input inattesi.
        salvaButton.setDisable(true);
        // Azione per il pulsante "Home"
        home.setOnAction((ActionEvent event) -> {
            try {
                switchScene(event, "/com/hclusclientfxv2/menu"); // Cambia scena
                ClientFx riferimentoClient = ottieniClient(); // Ottiene il client
                riferimentoClient.sendToServer("home"); // Notifica al server il ritorno alla home
            } catch (IOException e) {
                mostraErrore("Errore durante la comunicazione con il server: " + e.getMessage());
            }
        });

        // Azione per il pulsante "Single Link Distance"
        singleLinkDistanceButton.setOnAction(event -> {
            labelSelezioneSLD.setText("Selezionato!");
            labelSelezioneSLD.setStyle("-fx-text-fill: green;");
            mode = 1;
            labelSelezioneALD.setText(""); // Pulisce l'altra etichetta
        });

        // Azione per il pulsante "Average Link Distance"
        avarageLinkDistanceButton.setOnAction(event -> {
            labelSelezioneALD.setText("Selezionato!");
            labelSelezioneALD.setStyle("-fx-text-fill: green;");
            mode = 2;
            labelSelezioneSLD.setText(""); // Pulisce l'altra etichetta
        });

        // Inizializza il client e riceve la lista delle tabelle dal server
        ClientFx riferimentoClient = ottieniClient();
        List<String> listaTabelle = riferimentoClient.reciveListFromServer();
        comboTabelle.getItems().addAll(listaTabelle); // Popola la ComboBox

        // Azione per il pulsante "Conferma"
        confermaDB.setOnAction(event -> {
            try {
                String temp = fieldDepth.getText();
                int depth = Integer.parseInt(temp); // Profondità dell'albero

                String tableName = comboTabelle.getSelectionModel().getSelectedItem();

                // Controllo dei parametri
                if (tableName == null || tableName.trim().isEmpty()) {
                    throw new IOException("Errore: Il nome della tabella non può essere nullo o vuoto.");
                }

                if (depth <= 0) {
                    throw new IOException("Errore: Il valore di depth deve essere un intero positivo.");
                }

                if (mode < 1 || mode > 2) {
                    throw new IOException("Errore: Selezionare una modalità di linking (Single o Avarage).");
                }

                // Invio dei dati al server
                riferimentoClient.sendToServer(tableName);
                riferimentoClient.sendIntToServer(depth);
                riferimentoClient.sendIntToServer(mode);

                // Ricezione del risultato dal server e aggiornamento dell'interfaccia
                String risultato = riferimentoClient.receiveFromServer();
                //riabilito il pulsante salva in una zona "sicura".
                salvaButton.setDisable(false);
                textAreaDB.setText(risultato);
                fieldDepth.setText("nome file");
                confermaDB.setDisable(true);

            } catch (NumberFormatException e) {
                mostraErrore("Errore: Il valore della profondità deve essere un numero valido.");
            } catch (IOException e) {
                mostraErrore("Errore durante la comunicazione con il server: " + e.getMessage());
            }
        });

        // Azione per il pulsante "Salva"
        salvaButton.setOnAction(event -> {
            try {
                String nomeFile = fieldDepth.getText();

                // Controllo che il nome del file non sia vuoto o nullo
                if (nomeFile == null || nomeFile.trim().isEmpty() || nomeFile.equals("nome file")) {
                    throw new IOException("Errore: Il nome del file non può essere nullo o vuoto.");
                }

                // Notifica al server di salvare
                riferimentoClient.sendToServer("salva");
                riferimentoClient.sendToServer(nomeFile); // Invia il nome del file da salvare
            } catch (IOException e) {
                mostraErrore("Errore durante il salvataggio: " + e.getMessage());
            }
        });

    }

    /**
     * Mostra un messaggio di errore all'utente.
     *
     * @param messaggio Il messaggio di errore da mostrare.
     * eccezione personalizzata, usata per impedire allo user di inviare dati errati al server.
     * istanzia un oggetto Alert per informare l'utente (graficamente) che i dati che si vuole inviare non soddisfano i requisiti per essere spediti al server.
     */
    private void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

}
