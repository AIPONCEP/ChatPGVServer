package org.example.chatpgvserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.Gson;
import org.example.chatpgvserver.models.objects.MiObjeto;
import org.example.chatpgvserver.models.objects.User;


import static java.lang.System.out;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(49899);
            out.println("Servidor en espera de conexiones...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                out.println("Cliente conectado desde: " + clientSocket.getInetAddress().getHostAddress());

                // Establece flujos de entrada y salida para texto
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                // Lee la cadena JSON del cliente
                String comando = in.readLine();
                String json = in.readLine();

                switch (comando){
                    case "Insertar":
                        System.out.println(getDataUser(json).getName());
                        break;


                }

                // Cierra las conexiones con el cliente
                in.close();
                out.close();
                clientSocket.close();
                System.out.println("Conexiones cerradas");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User getDataUser(String json){
        // Convierte el JSON a un objeto Java
        Gson gson = new Gson();
        User user = gson.fromJson(json, User.class);
        return user;
    }
}


