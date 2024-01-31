package org.example.chatpgvserver.models.objects;

public class Message {
    private int id;

    private int id_remitente;

    private int id_destinatario;

    private String txt_Mensaje;

    private String fecha;

    public Message(int id, int id_remitente, int id_destinatario, String txt_Mensaje, String fecha) {
        this.id = id;
        this.id_remitente = id_remitente;
        this.id_destinatario = id_destinatario;
        this.txt_Mensaje = txt_Mensaje;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_remitente() {
        return id_remitente;
    }

    public void setId_remitente(int id_remitente) {
        this.id_remitente = id_remitente;
    }

    public int getId_destinatario() {
        return id_destinatario;
    }

    public void setId_destinatario(int id_destinatario) {
        this.id_destinatario = id_destinatario;
    }

    public String getTxt_Mensaje() {
        return txt_Mensaje;
    }

    public void setTxt_Mensaje(String txt_Mensaje) {
        this.txt_Mensaje = txt_Mensaje;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
