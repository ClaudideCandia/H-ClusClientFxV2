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
//classe che gestisce il comportamento della schermata dell'interfaccia corrispondente.
public class ControllerCaricaDaFile {
    @FXML
    Button confermaSceltaFile;
    @FXML
    Button home;
    @FXML
    Label etichetta1caricaDaFile;
    @FXML
    TextField fieldFile;
    @FXML
    Label etichettaRisultato;
    @FXML
    TextArea textAreaRisultati;

    ClientFx temp;

    public void initialize(){
        ClientFx rifC = temp.ottieniClient();
        home.setOnAction((ActionEvent event)->{
            try {
                switchScene(event,"/com/hclusclientfxv2/menu");
                rifC.sendToServer("home");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        confermaSceltaFile.setOnAction((ActionEvent e) -> {
            String esito= fieldFile.getText();
            if (esito.equals("")){
                etichettaRisultato.setText("file non valido!");
                etichettaRisultato.setStyle("-fx-text-fill: red");
                Timeline timeline = new Timeline(new KeyFrame(
                        Duration.seconds(3.5),
                        event -> etichettaRisultato.setText("")
                ));
                timeline.setCycleCount(1);
                timeline.play();
            }else{
                //implementa logica di caricamento file
                String nomeFile = fieldFile.getText();
                rifC.sendToServer(nomeFile);
                String risultato = (String) rifC.receiveFromServer();
                textAreaRisultati.setText(risultato);

            }
        });
    }
}
