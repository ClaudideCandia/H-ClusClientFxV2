//definizione  del package.
package com.hclusclientfxv2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import com.hclusclientfxv2.ClientFx.*;

/**
 * Classe principale dell'applicazione H-CLUSclientFXv2.
 * <p>
 * Estende {@link javafx.application.Application} per sfruttare il framework JavaFX,
 * che fornisce il ciclo di vita di un'applicazione grafica. Questo include la gestione
 * dell'avvio, della configurazione della finestra principale (stage) e dell'interfaccia utente.
 * </p>
 * <p>
 * La classe avvia l'interfaccia utente caricando il file FXML e configurando la finestra principale.
 * </p>
 */
public class APP extends Application {

    /**
     * Configura e mostra la finestra principale dell'applicazione.
     *
     * @param stage il contenitore principale della finestra.
     * @throws IOException se il file FXML non viene caricato correttamente.
     */
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(APP.class.getResource("menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/immagini/logoUniba.png")));
        stage.setTitle("H-CLUSclientFXv2");
        stage.setOnCloseRequest(event -> {
            try {
                ClientFx riferimentoClient = ClientFx.ottieniClient();
                riferimentoClient.sendToServer("Close");
            } catch (RuntimeException e) {
                System.err.println("Chiusura del client: " + e.getMessage());
            } finally {
                Platform.exit();  // Chiude il ciclo di vita JavaFX
                System.exit(0);   // Termina il processo
            }
        });
        stage.setScene(scene);
        stage.show();


    }
    /**
     * Punto di ingresso dell'applicazione JavaFX.
     *
     * @param args argomenti della riga di comando.
     */
    public static void main(String[] args) {
        launch();//metodo statico ereditato dalla classe javafx.application.Application. È il punto di ingresso
        // per le applicazioni JavaFX e ha il compito di avviare il toolkit JavaFX e configurare l'applicazione.
    }
}