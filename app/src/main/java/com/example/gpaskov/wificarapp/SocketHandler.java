package com.example.gpaskov.wificarapp;

import java.net.Socket;

/**
 * Created by gpaskov on 06-04-16.
 */
public class SocketHandler {

    private static Socket socket;

    public static synchronized Socket getSocket() {
        return socket;
    }

    public static synchronized void setSocket(Socket socket) {
        SocketHandler.socket = socket;
    }
}
