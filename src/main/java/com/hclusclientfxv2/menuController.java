package com.hclusclientfxv2;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

import static com.hclusclientfxv2.ControllerControllo.switchScene;
//classe che gestisce il comportamento della schermata principale dell'interfaccia.
public class menuController {
    ClientFx rif;
    @FXML
   private Label etichetta1;

    String DataBase="DataBase";
    String File="File";
    String sceltaComboMenu="";
    public String getSceltaComboMenu() {return sceltaComboMenu;}
    @FXML
    private ComboBox<String> comboSelettore;

    public void inizializzaComboSelettore(){
        comboSelettore.setItems(FXCollections.observableArrayList(DataBase,File));
    }

    @FXML
    ImageView imageViewMenu;


    @FXML
   private Button ConfermaSelezione;

    @FXML
    public void initialize() {
        inizializzaComboSelettore();
        Image immagineMenu = new Image(getClass().getResourceAsStream("/com/immagini/dataminigimage.jpeg"));
        imageViewMenu.setImage(immagineMenu);
        ConfermaSelezione.setOnAction(event -> {
            // Recupera la scelta selezionata nella ComboBox
            String scelta = comboSelettore.getSelectionModel().getSelectedItem();

            // Verifica che sia stata fatta una scelta
            if (scelta == null) {
                etichetta1.setText("Inserire scelta -> premere conferma!");
            } else if(scelta.equals(DataBase)){
                etichetta1.setText("DataBase");
                sceltaComboMenu=DataBase;
                final ClientFx clientRif = rif.ottieniClient();
                clientRif.sendToServer(DataBase);
                System.out.println(sceltaComboMenu);
                try {

                    switchScene(event,"/com/hclusclientfxv2/caricaDaDB");

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else{
                etichetta1.setText("File");
                final ClientFx clientRif = rif.ottieniClient();
                clientRif.sendToServer(File);
                System.out.println(sceltaComboMenu);
                try {
                    switchScene(event,"/com/hclusclientfxv2/caricaDaFile");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }


}