package com.example.bibliotecanacional.modelo;

import java.util.ArrayList;

public class BiblioRespuesta {
    private String error;
    private String total;
    private String page;
    private ArrayList <Biblioteca> books;



    public ArrayList<Biblioteca> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Biblioteca> books) {
        this.books = books;
    }
}
