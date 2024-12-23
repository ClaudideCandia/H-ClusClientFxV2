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

    private boolean Single = false;
    private boolean Avarage = false;

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

        // Azione per il pulsante "Home"
        home.setOnAction((ActionEvent event) -> {
            try {
                switchScene(event, "/com/hclusclientfxv2/menu"); // Cambia scena
                ClientFx riferimentoClient = ottieniClient(); // Ottiene il client
                riferimentoClient.sendToServer("home"); // Notifica al server il ritorno alla home
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Azione per il pulsante "Single Link Distance"
        singleLinkDistanceButton.setOnAction(event -> {
            labelSelezioneSLD.setText("Selezionato!");
            labelSelezioneSLD.setStyle("-fx-text-fill: green;");
            Single = true;
            Avarage = false;
            labelSelezioneALD.setText(""); // Pulisce l'altra etichetta
        });

        // Azione per il pulsante "Average Link Distance"
        avarageLinkDistanceButton.setOnAction(event -> {
            labelSelezioneALD.setText("Selezionato!");
            labelSelezioneALD.setStyle("-fx-text-fill: green;");
            Single = false;
            Avarage = true;
            labelSelezioneSLD.setText(""); // Pulisce l'altra etichetta
        });

        // Inizializza il client e riceve la lista delle tabelle dal server
        ClientFx riferimentoClient = ottieniClient();
        List<String> listaTabelle = riferimentoClient.reciveListFromServer();
        comboTabelle.getItems().addAll(listaTabelle); // Popola la ComboBox

        // Azione per il pulsante "Conferma"
        confermaDB.setOnAction(event -> {
            String temp = fieldDepth.getText();
            int depth = Integer.parseInt(temp); // Profondità dell'albero
            int mode = Single ? 1 : 2; // Modalità di clustering: 1 = Single Link, 2 = Average Link
            String tableName = comboTabelle.getSelectionModel().getSelectedItem();

            // Invio dei dati al server
            riferimentoClient.sendToServer(tableName);
            riferimentoClient.sendIntToServer(depth);
            riferimentoClient.sendIntToServer(mode);

            // Ricezione del risultato dal server e aggiornamento dell'interfaccia
            String risultato = riferimentoClient.receiveFromServer();
            textAreaDB.setText(risultato);
            fieldDepth.setText("nome file");
        });

        // Azione per il pulsante "Salva"
        salvaButton.setOnAction(event -> {
            riferimentoClient.sendToServer("salva"); // Notifica al server di salvare
            String nomeFile = fieldDepth.getText();
            riferimentoClient.sendToServer(nomeFile); // Invia il nome del file da salvare
        });
    }
}
