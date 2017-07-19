package com.example.vadim.dpapp.helper;

import java.net.Socket;

public class Client {

    private Thread thread;
    private Socket socket;

    public Client(Thread thread, Socket socket) {
        this.thread = thread;
        this.socket = socket;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
