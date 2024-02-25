package org.example.chatpgvserver.models;

import java.sql.*;

public class DBconnection {
    private static final String url = "jdbc:mysql://localhost:3306/chatPGV";
    private static final String user = "root";
    private static final String password = "1234";
    private static Connection connect = null;
    private static PreparedStatement sentencia = null;
    private static ResultSet resultado = null;

    /**
     * Método para obtener una conexión a la base de datos.
     * @return Connection - Objeto de conexión a la base de datos.
     */
    public static Connection getConnection() {
        try {
            if (connect == null || connect.isClosed()) {
                connect = DriverManager.getConnection(url, user, password);
                System.out.println("¡Conexión exitosa!");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return connect;
    }

    /**
     * Método para cerrar la conexión a la base de datos.
     */
    public static void closeConnection() {
        if (connect != null) {
            try {
                // Cierra la conexión con la base de datos
                connect.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                // Manejo de excepciones al cerrar la conexión
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            } finally {
                // Establece la conexión como nula una vez cerrada
                connect = null;
            }
        }
    }

    public static String consultas(String statementSql){
        connect=DBconnection.getConnection();
        if(connect!=null){
            try {
                sentencia = connect.prepareStatement(statementSql);
                resultado = sentencia.executeQuery();
                ResultSetMetaData metaData = resultado.getMetaData();
                int columnCount = metaData.getColumnCount();
                StringBuilder result = new StringBuilder();
                while (resultado.next()) {
                    StringBuilder row = new StringBuilder();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        Object value = resultado.getObject(columnName);
                        row.append(columnName).append(":").append(value).append(" ");
                    }
                    result.append(row);
                }
                DBconnection.closeConnection();
                sentencia.close();
                return result.toString();
            } catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return null;
    }


    /**
     * Método para ejecutar consultas de cambios (INSERT, UPDATE, DELETE) en la base de datos.
     * @param sql - Consulta SQL a ejecutar.
     * @return boolean - True si la consulta se realizó con éxito, False en caso contrario.
     */
    public static boolean ExecuteChangesSql(String sql) {
        connect = DBconnection.getConnection();
        if (connect != null) {
            PreparedStatement statement = null;
            try {
                statement = connect.prepareStatement(sql);


                // Ejecuta la consulta de cambios y devuelve true si se realizan con éxito
                int filasAfectadas = statement.executeUpdate();
                System.out.println("Filas afectadas: " + filasAfectadas);
                return true;

            } catch (SQLException e) {
                // Manejo de excepciones en caso de error en la consulta
                System.out.println("Error: " + e.getMessage());
                return false;
            } finally {
                // Cierra el PreparedStatement y la conexión a la base de datos
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                DBconnection.closeConnection();
            }
        }
        return false;
    }
}
