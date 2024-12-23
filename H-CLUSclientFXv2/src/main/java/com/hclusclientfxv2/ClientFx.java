package com.hclusclientfxv2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;


//classe singoletto che instaura la connessione al server alla prima invocazione,
//inoltre fornisce i metodi per ottenere e inviare in modo sicuro dati da/al server
public class ClientFx {
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private static ClientFx instance;

    private ClientFx() {
        int port = 8080;
        String ip = "127.0.0.1"; //identifica l'host locale.
        try {
            clientSocket = new Socket(ip, port);
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            System.out.println("Connessione stabilita con il server su IP " + ip + " e porta " + port);
        } catch (IOException e) {
            System.err.println("Errore durante la connessione al server: " + e.getMessage());
            // Reset delle variabili per evitare NullPointerException
            clientSocket = null;
            out = null;
            in = null;
        }
    }
    //metodo che rilascia l'istanza del client al chiamante
    public static synchronized ClientFx ottieniClient() {
        if (instance == null) {
            instance = new ClientFx();
        }
        return instance;
    }

    private void checkConnection() {
        if (clientSocket == null || out == null || in == null) {
            throw new IllegalStateException("Connessione al server non stabilita.");
        }
    }


    //seguono i metodi per inviare/ricevere da/al server.


    public void sendToServer(String messaggio) {
        checkConnection();
        try {
            out.writeObject(messaggio);
        } catch (IOException e) {
            throw new RuntimeException("Errore durante l'invio del messaggio al server", e);
        }
    }

    public void sendIntToServer(int messaggio) {
        checkConnection();
        try {
            out.writeObject(messaggio);
        } catch (IOException e) {
            throw new RuntimeException("Errore durante l'invio del messaggio al server", e);
        }
    }

    public String receiveFromServer() {
        checkConnection();
        try {
            return (String) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Errore durante la ricezione del messaggio dal server", e);
        }
    }

    public List<String> reciveListFromServer() {
        checkConnection();
        try {
            return (List<String>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Errore durante la ricezione della lista dal server", e);
        }
    }
}

