package com.example.bibliotecanacional;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.bibliotecanacional.Bliblioapi.BiblioapiService;
import com.example.bibliotecanacional.modelo.LibroRespuesta;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Librodetalle extends AppCompatActivity  {

    private ImageView detaImage;
    private TextView detaTitle;
    private TextView detaSubtitle;
    private TextView detaAuthors;
    private TextView detaPublisher;
    private TextView detaPages;
    private TextView detaYear;
    private TextView detaRating;
    private TextView detaDescription;
    private TextView detaPrice;
    private String detalleb;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_libro);

        detaImage = findViewById(R.id.detaImage);
        detaTitle = findViewById(R.id.detaTitle);
        detaSubtitle = findViewById(R.id.detaSubtitle);
        detaAuthors = findViewById(R.id.detaAuthors);
        detaPublisher = findViewById(R.id.detaPublisher);
        detaPages = findViewById(R.id.detaPages);
        detaYear = findViewById(R.id.detaYear);
        detaRating= findViewById(R.id.detaRating);
        detaDescription = findViewById(R.id.detaDescription);
        detaPrice = findViewById(R.id.detaPrice);


        Intent mpv = getIntent();
        detalleb = mpv.getStringExtra("detalleb");
        detaSubtitle.setText((CharSequence) detalleb);
        obtenerdetalles(this);


    }

    private void obtenerdetalles(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.itbook.store/1.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        BiblioapiService service = retrofit.create(BiblioapiService.class);
        Call<LibroRespuesta> libroRespuestaCall = service.obtenerDetalleLibros(detalleb);{
            libroRespuestaCall.enqueue(new Callback<LibroRespuesta>() {
                @Override
                public void onResponse(@NotNull Call<LibroRespuesta> call, @NotNull Response<LibroRespuesta> response) {
                    if (response.isSuccessful()){
                        LibroRespuesta libroRespuesta = response.body();

                        detaTitle.setText(libroRespuesta.getTitle());
                        detaSubtitle.setText(libroRespuesta.getSubtitle());
                        detaAuthors.setText(libroRespuesta.getAuthors());
                        detaPublisher.setText(libroRespuesta.getPublisher());
                        detaPages.setText(libroRespuesta.getPages());
                        detaYear.setText(libroRespuesta.getYear());
                        detaRating.setText(libroRespuesta.getRating());
                        detaDescription.setText(libroRespuesta.getDesc());
                        detaPrice.setText(libroRespuesta.getPrice());

                        Glide.with(context)
                                .load(libroRespuesta.getImage())
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(detaImage);

                    } else {
                        Log.e(TAG, "onResponse:"+ response.errorBody());
                    }
                }
                @Override
                public void onFailure(@NotNull Call<LibroRespuesta> call, @NotNull Throwable t) {
                    Log.e(TAG, "onFailure:"+ t.getMessage());
                }
            });
        }
    }


}

