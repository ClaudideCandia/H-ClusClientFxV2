package com.hclusclientfxv2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * Classe singoletto che gestisce la connessione al server.
 * <p>
 * La classe crea una connessione al server alla prima invocazione e fornisce
 * metodi sicuri per inviare e ricevere dati. Utilizza il pattern Singleton per
 * garantire una singola istanza della connessione.
 * </p>
 */
public class ClientFx {

    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private static ClientFx instance;

    /**
     * Costruttore privato della classe.
     * <p>
     * Stabilisce la connessione al server utilizzando l'indirizzo IP locale
     * (127.0.0.1) e la porta 8080. In caso di errore durante la connessione,
     * le variabili di connessione vengono resettate per prevenire errori futuri.
     * </p>
     */
    private ClientFx() {
        int port = 8080;
        //String ip = "127.0.0.1"; // Indirizzo IP locale
       String ip = "146.59.145.214";
        try {
            clientSocket = new Socket(ip, port);
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            System.out.println("Connessione stabilita con il server su IP " + ip + " e porta " + port);
        } catch (IOException e) {
            System.err.println("Errore durante la connessione al server: " + e.getMessage());
            clientSocket = null;
            out = null;
            in = null;
        }
    }

    /**
     * Restituisce l'istanza della classe ClientFx.
     * <p>
     * Se l'istanza non esiste, la crea e stabilisce la connessione al server.
     * Il metodo è sincronizzato per garantire la sicurezza nei contesti multithread.
     * </p>
     *
     * @return l'unica istanza della classe ClientFx.
     */
    public static synchronized ClientFx ottieniClient() {
        if (instance == null) {
            instance = new ClientFx();
        }
        return instance;
    }

    /**
     * Controlla se la connessione al server è attiva.
     * <p>
     * Se la connessione non è stabilita, genera un'eccezione {@link IllegalStateException}.
     * </p>
     */
    private void checkConnection() {
        if (clientSocket == null || out == null || in == null) {
            throw new IllegalStateException("Connessione al server non stabilita.");
        }
    }

    /**
     * Invia una stringa al server.
     *
     * @param messaggio il messaggio da inviare al server.
     * @throws RuntimeException se si verifica un errore durante l'invio.
     */
    public void sendToServer(String messaggio) {
        checkConnection();
        try {
            out.writeObject(messaggio);
        } catch (IOException e) {
            throw new RuntimeException("Errore durante l'invio del messaggio al server", e);
        }
    }

    /**
     * Invia un numero intero al server.
     *
     * @param messaggio il messaggio intero da inviare al server.
     * @throws RuntimeException se si verifica un errore durante l'invio.
     */
    public void sendIntToServer(int messaggio) {
        checkConnection();
        try {
            out.writeObject(messaggio);
        } catch (IOException e) {
            throw new RuntimeException("Errore durante l'invio del messaggio al server", e);
        }
    }

    /**
     * Riceve una stringa dal server.
     *
     * @return il messaggio ricevuto dal server.
     * @throws RuntimeException se si verifica un errore durante la ricezione.
     */
    public String receiveFromServer() {
        checkConnection();
        try {
            return (String) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Errore durante la ricezione del messaggio dal server", e);
        }
    }

    /**
     * Riceve una lista di stringhe dal server.
     *
     * @return la lista ricevuta dal server.
     * @throws RuntimeException se si verifica un errore durante la ricezione.
     */
    public List<String> reciveListFromServer() {
        checkConnection();
        try {
            return (List<String>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Errore durante la ricezione della lista dal server", e);
        }
    }
}
