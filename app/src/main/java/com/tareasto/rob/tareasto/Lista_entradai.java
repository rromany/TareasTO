package com.tareasto.rob.tareasto;


public class Lista_entradai {
    private int idImagen;
    private String texto;


    public Lista_entradai (int idImagen, String texto) {
        this.idImagen = idImagen;
        this.texto = texto;

    }

    public String get_texto() {
        return texto;
    }



    public int get_idImagen() {
        return idImagen;
    }

}
