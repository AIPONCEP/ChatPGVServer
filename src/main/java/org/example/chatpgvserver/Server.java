package org.example.chatpgvserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import com.google.gson.Gson;
import org.example.chatpgvserver.models.objects.Message;
import org.example.chatpgvserver.models.objects.User;
import static java.lang.System.out;
import static org.example.chatpgvserver.models.DBconnection.ExecuteChangesSql;
import static org.example.chatpgvserver.models.DBconnection.consultas;

/**
 * @author Jose David Falcon, Ana Isabel Ponce Pérez
 * Clase Server
 * Representa el servidor de chat.
 */
public class Server {
    public static void main(String[] args) {
        try {
            // Se crea un socket servidor en el puerto 49898
            ServerSocket serverSocket = new ServerSocket(49898);
            out.println("Servidor en espera de conexiones...");

            while (true) {
                // Aceptar la conexión del cliente
                Socket clientSocket = serverSocket.accept();
                out.println("Cliente conectado desde: " + clientSocket.getInetAddress().getHostAddress());

                // Crear un hilo para manejar la conexión del cliente
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clase interna ClientHandler
     * Maneja las solicitudes de los clientes en hilos separados.
     */
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        /**
         * Constructor de la clase ClientHandler.
         * @param clientSocket Socket del cliente.
         */
        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                // Crear streams de entrada y salida para la comunicación con el cliente
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                // Leer el comando y el JSON del cliente
                String comando = in.readLine();
                String json = in.readLine();

                // Procesar la solicitud del cliente según el comando recibido
                switch (comando){
                    case "Insertar usuario":
                        // Insertar usuario en la base de datos
                        ExecuteChangesSql("INSERT INTO usuarios (nombre, contraseña, tlf) VALUES ('"
                                +getDataUser(json).getName()+
                                "', '"+getDataUser(json).getPassword()+
                                "', "+getDataUser(json).getTlf()+
                                ")");
                        break;

                    case "Insertar mensaje":
                        // Insertar mensaje en la base de datos
                        ExecuteChangesSql("INSERT INTO mensajes (id_remitente, id_destinatario, txt_Mensaje, fecha) VALUES ("
                                +getDataMessage(json).getId_remitente()+
                                ", "+getDataMessage(json).getId_destinatario()+
                                ", '"+getDataMessage(json).getTxt_Mensaje()+"'" +
                                ", '" + getDataMessage(json).getFecha() + "')");
                        break;

                    case "Select usuarios":
                        // Seleccionar usuarios de la base de datos
                        Gson gsonNombre = new Gson();
                        String datosUsuario = gsonNombre.fromJson(json, String.class);
                        System.out.println(datosUsuario);
                        out.println(consultas("SELECT nombre, id FROM usuarios WHERE id != " + datosUsuario.substring(3)));
                        break;

                    case "Select usuario":
                        // Seleccionar un usuario de la base de datos
                        Gson gson = new Gson();
                        String datos = gson.fromJson(json, String.class);
                        System.out.println(datos);

                        String[] partes = datos.split(" ");
                        String nombre = partes[0];
                        String contraseña = partes[1];

                        // Construir la consulta SQL con las partes divididas
                        String consultaSQL = "SELECT id FROM usuarios WHERE nombre = '" + nombre + "' AND contraseña = '" + contraseña + "'";

                        // Ejecutar la consulta SQL
                        String resultadoConsulta = consultas(consultaSQL);

                        // Verificar si la consulta devuelve resultados
                        if (resultadoConsulta != null && !resultadoConsulta.isEmpty()) {
                            // Enviar el resultado al cliente
                            out.println(resultadoConsulta);
                        } else {
                            // Enviar un mensaje al cliente indicando que no se encontraron resultados
                            out.println("No se encontró ningún usuario con el nombre y contraseña proporcionados.");
                        }
                        break;

                    case  "Select mensaje":
                        // Seleccionar mensajes de la base de datos
                        Gson gsonSms = new Gson();
                        String datosSms = gsonSms.fromJson(json, String.class);
                        System.out.println(datosSms);

                        String[] partesSms = datosSms.split(" ");
                        String id_remitente = partesSms[0];
                        String id_destinatario = partesSms[1];

                        // Consulta SQL para obtener todos los mensajes entre dos usuarios
                        String consultaMensajeSql = "SELECT usuarios.nombre, mensajes.txt_Mensaje " +
                                "FROM mensajes " +
                                "INNER JOIN usuarios ON mensajes.id_remitente = usuarios.id " +
                                "WHERE (mensajes.id_remitente = " + id_remitente + " AND mensajes.id_destinatario = " + id_destinatario + ") " +
                                "OR (mensajes.id_remitente = " + id_destinatario + " AND mensajes.id_destinatario = " + id_remitente + ")";

                        String resultadoConsultaSms = consultas(consultaMensajeSql);
                        System.out.println(resultadoConsultaSms);

                        // Verificar si la consulta devuelve resultados
                        if (resultadoConsultaSms != null && !resultadoConsultaSms.isEmpty()) {
                            // Enviar el resultado al cliente
                            out.println(resultadoConsultaSms);
                        } else {
                            // Enviar un mensaje al cliente indicando que no se encontraron resultados
                            out.println("Error, no se encontraron conversaciones registradas.");
                        }
                        break;
                }

                // Cierra las conexiones con el cliente
                in.close();
                out.close();
                clientSocket.close();
                out.println("Conexion cerrada");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Método getDataUser
     * para obtener un objeto User a partir de un JSON.
     * @param json El JSON que contiene los datos del usuario.
     * @return Un objeto User creado a partir del JSON.
     */
    public static User getDataUser(String json){
        // Convierte el JSON a un objeto Java
        Gson gson = new Gson();
        User user = gson.fromJson(json, User.class);
        return user;
    }

    /**
     * Método getDataMessage
     * para obtener un objeto Message a partir de un JSON.
     * @param json El JSON que contiene los datos del mensaje.
     * @return Un objeto Message creado a partir del JSON.
     */
    public static Message getDataMessage(String json){
        Gson gson = new Gson();
        Message message = gson.fromJson(json, Message.class);
        return message;
    }
}


