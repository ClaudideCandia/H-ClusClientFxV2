package com.hclusclientfxv2;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

import static com.hclusclientfxv2.ControllerControllo.switchScene;

/**
 * Classe che gestisce il comportamento della schermata principale dell'interfaccia.
 * Fornisce funzionalità per selezionare opzioni, gestire eventi utente e cambiare schermata.
 */
public class menuController {

    /** Istanza del client per comunicare con il server. */
    ClientFx rif;

    /** Etichetta per visualizzare messaggi o istruzioni all'utente. */
    @FXML
    private Label etichetta1;

    /** Stringa rappresentante l'opzione "DataBase" nel menu. */
    String DataBase = "DataBase";

    /** Stringa rappresentante l'opzione "File" nel menu. */
    String File = "File";

    /** Variabile che memorizza la scelta effettuata nel menu. */
    String sceltaComboMenu = "";

    /**
     * Restituisce la scelta effettuata dall'utente nella ComboBox.
     * @return la scelta effettuata dall'utente.
     */
    public String getSceltaComboMenu() {
        return sceltaComboMenu;
    }

    /** ComboBox che permette all'utente di selezionare un'opzione ("DataBase" o "File"). */
    @FXML
    private ComboBox<String> comboSelettore;

    /**
     * Inizializza la ComboBox con le opzioni disponibili.
     */
    public void inizializzaComboSelettore() {
        comboSelettore.setItems(FXCollections.observableArrayList(DataBase, File));
    }

    /** ImageView per visualizzare un'immagine di benvenuto nel menu principale. */
    @FXML
    ImageView imageViewMenu;

    /** Pulsante per confermare la scelta effettuata nella ComboBox. */
    @FXML
    private Button ConfermaSelezione;

    /** Etichetta con messaggio **/
    @FXML
    Label labelServer1;

    /** Etichetta con messaggio **/
    @FXML
    Label labelServer2;

    /** Etichetta con messaggio **/
    @FXML
    Label labelServer3;

    /** scorciatoia localHost **/
    @FXML
    Button localButton;

    /** Campo di testo per l'IP **/
    @FXML
    TextField textFieldIP;

    /** bottone conferma server **/
    @FXML
    Button confermaServerButton;
    /** stringa ausiliaria per scelta server **/
   static String ip;

    /** flag per controllo eventi **/
   static boolean done;



    /**
     * Metodo di inizializzazione che configura il comportamento della schermata principale.
     * Inizializza la ComboBox, imposta l'immagine del menu e definisce le azioni per il pulsante di conferma.
     */
    @FXML
    public void initialize() {
        // Inizializza la ComboBox con le opzioni.
        inizializzaComboSelettore();

        // Imposta l'immagine nel menu principale.
        Image immagineMenu = new Image(getClass().getResourceAsStream("/com/immagini/dataminigimage.jpeg"));
        imageViewMenu.setImage(immagineMenu);

        //implementazione scelta server.
        ConfermaSelezione.setDisable(true);
        localButton.setOnAction(event -> {
            ip = "127.0.0.1";
            labelServer3.setText("Selezionato!");
            labelServer3.setStyle("-fx-text-fill: green;");
            confermaServerButton.setDisable(true);
            //riabilito il resto del programma.
            ConfermaSelezione.setDisable(false);
            done=true;
        });

        confermaServerButton.setOnAction(event -> {
            ip = textFieldIP.getText();
            localButton.setDisable(true);
            ConfermaSelezione.setDisable(false);
            done=true;
        });

        // Configura l'azione del pulsante "ConfermaSelezione".
        ConfermaSelezione.setOnAction(event -> {
            // Recupera la scelta selezionata nella ComboBox.
            String scelta = comboSelettore.getSelectionModel().getSelectedItem();

            // Verifica che sia stata fatta una scelta e gestisce l'azione corrispondente.
            if (scelta == null) {
                etichetta1.setText("Inserire scelta -> premere conferma!");
            } else if (scelta.equals(DataBase)) {
                etichetta1.setText("DataBase");
                sceltaComboMenu = DataBase;
                final ClientFx clientRif = rif.ottieniClient(ip);
                clientRif.sendToServer(DataBase);
                System.out.println(sceltaComboMenu);
                try {
                    switchScene(event, "/com/hclusclientfxv2/caricaDaDB");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                etichetta1.setText("File");
                final ClientFx clientRif = rif.ottieniClient(ip);
                clientRif.sendToServer(File);
                System.out.println(sceltaComboMenu);
                try {
                    switchScene(event, "/com/hclusclientfxv2/caricaDaFile");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        if(done){
            confermaServerButton.setDisable(true);
            localButton.setDisable(true);
            ConfermaSelezione.setDisable(false);
        }

    }
}
