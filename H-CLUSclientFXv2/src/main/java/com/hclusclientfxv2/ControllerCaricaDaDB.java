package com.hclusclientfxv2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.util.List;

import static com.hclusclientfxv2.ClientFx.ottieniClient;
import static com.hclusclientfxv2.ControllerControllo.switchScene;

public class ControllerCaricaDaDB {
    @FXML
    Label label1DaDB;
    @FXML
    Label labelSelezioneSLD;
    @FXML
    Label labelSelezioneALD;
    @FXML
    ComboBox<String> comboTabelle;
    @FXML
    Button singleLinkDistanceButton;
    @FXML
    Button avarageLinkDistanceButton;
    @FXML
    Button salvaButton;
    @FXML
    Button confermaDB;
    @FXML
    Button home;
    @FXML
    TextArea textAreaDB;
    @FXML
    TextField fieldDepth;

    boolean Single=false;
    boolean Avarage=false;


   public ComboBox<String> getComboTabelle(){
       return comboTabelle;
   }


    public void initialize() {

        home.setOnAction((ActionEvent event)->{
            try {
                switchScene(event,"/com/hclusclientfxv2/menu");
                //pseudo inizializzo il riferimento al client
                ClientFx riferimentoClient = ottieniClient();
                riferimentoClient.sendToServer("home");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Azione per il pulsante Single Link Distance
        singleLinkDistanceButton.setOnAction(event -> {
            labelSelezioneSLD.setText("Selezionato!");
            labelSelezioneSLD.setStyle("-fx-text-fill: green;");
            Single = true;
            Avarage = false;
            labelSelezioneALD.setText(""); // Pulisce l'altra etichetta
        });

        // Azione per il pulsante Average Link Distance
        avarageLinkDistanceButton.setOnAction(event -> {
            labelSelezioneALD.setText("Selezionato!");
            labelSelezioneALD.setStyle("-fx-text-fill: green;");
            Single = false;
            Avarage = true;
            labelSelezioneSLD.setText(""); // Pulisce l'altra etichetta
        });
        //pseudo inizializzo il riferimento al client
        ClientFx riferimentoClient = ottieniClient();
        //ricevo la lista delle tabelle dal server
        List<String> listaTabelle;
        listaTabelle=riferimentoClient.reciveListFromServer();
        //popolo la comboBox con la lista delle tabelle ottenuta dal server
        comboTabelle.getItems().addAll(listaTabelle);

        //azione per il pulsante confermaDb
        confermaDB.setOnAction(event -> {
            String temp;
            temp = fieldDepth.getText();
            int depth = Integer.parseInt(temp);
            int mode;
            if(Single==true){
               mode =1;
            }else {
                mode=2;
            }
            String tableName=comboTabelle.getSelectionModel().getSelectedItem();
            riferimentoClient.sendToServer(tableName);
            riferimentoClient.sendIntToServer(depth);
            riferimentoClient.sendIntToServer(mode);
            String risultato = riferimentoClient.receiveFromServer();
            textAreaDB.setText(risultato);
            fieldDepth.setText("nome file");
        });
        //sezione della logica dell'interfaccia corrente che gestisce il salvataggio.
        salvaButton.setOnAction(event ->{
            riferimentoClient.sendToServer("salva");
            String nomeFile = fieldDepth.getText();
            riferimentoClient.sendToServer(nomeFile);
        });

    }

}


