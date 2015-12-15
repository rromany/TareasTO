package com.tareasto.rob.tareasto;

public class Lista_entrada {
    private int idImagen;
    private String textoEncima;
    private String textoDebajo;
    //private String textoCLK;
    private int alarma;
    private int listo;
    private int hor;
    private int min;

    public Lista_entrada(int idImagen, String textoEncima, String textoDebajo, Integer hor, Integer min, int alarma, int listo) {
        this.idImagen = idImagen;
        this.textoEncima = textoEncima;
        this.textoDebajo = textoDebajo;
        this.hor = hor;
        this.min = min;
        this.alarma = alarma;
        this.listo = listo;

    }

    public String get_textoEncima() {
        return textoEncima;
    }

    public String get_textoDebajo() {
        return textoDebajo;
    }

    public int get_idImagen() {
        return idImagen;
    }


    public Integer get_hor() {
        return hor;
    }

    public Integer get_min() {
        return min;
    }

    public int get_alarma() {
        return alarma;
    }

    public int get_listo() {
        return listo;
    }
}