package com.example.vadim.dpapp;

import com.example.vadim.dpapp.helper.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("Welcome to Server side");


        ServerSocket servers = null;
        List<Client> clientList = null;
        Socket fromclient = null;
        clientList = new ArrayList<>();
        MyXMLHelper xmlHelper;
        MySQLHelper sqlHelper;
        try {
            xmlHelper = new MyXMLHelper();
            sqlHelper = new MySQLHelper();
            sqlHelper.addContragent(xmlHelper.LoadContragent());

            servers = new ServerSocket(4444);
        } catch (IOException e) {
            System.out.println("Couldn't listen to port 4444");
            System.exit(-1);
        }
        System.out.println("Waiting for a clients...");
        Timer timer = new Timer();
        timer.schedule(new TimerXML(),1000,20000);

        while(true) {
            try {
                fromclient = servers.accept();
                Thread thread = new Thread(new Worker(fromclient));
                clientList.add(new Client(thread,fromclient));
                thread.start();

                System.out.println("Client connected " + fromclient.getInetAddress());
            } catch (IOException e) {
                System.out.println("Can't accept");
                System.exit(-1);
            }
        }
    }
}
