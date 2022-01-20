package com.example.bibliotecanacional.Bliblioapi;

import com.example.bibliotecanacional.modelo.BiblioRespuesta;
import com.example.bibliotecanacional.modelo.LibroRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BiblioapiService {

    @GET ("search/{value}/{page}")
    Call<BiblioRespuesta> obtenerListaLibros(@Path("value")String value,@Path ("page") int page );

    @GET("books/{valueD}")
    Call<LibroRespuesta> obtenerDetalleLibros(@Path("valueD")String value );
}
