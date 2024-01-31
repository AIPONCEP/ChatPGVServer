package org.example.chatpgvserver.models.objects;

import com.google.gson.annotations.SerializedName;

public class MiObjeto {
    @SerializedName("nombre")
    private String nombre;

    @SerializedName("num")
    private Integer num;

    public MiObjeto(String nombre, Integer num) {
        this.nombre = nombre;
        this.num = num;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
