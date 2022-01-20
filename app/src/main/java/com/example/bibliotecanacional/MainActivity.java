package com.example.bibliotecanacional;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bibliotecanacional.Bliblioapi.BiblioapiService;
import com.example.bibliotecanacional.modelo.BiblioRespuesta;
import com.example.bibliotecanacional.modelo.Biblioteca;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static  final String TAG ="Biblioteca";
    private Retrofit retrofit;
    private EditText Busqueda;

    private int page;
    private listadoLibrosAdapter listadoLibrosAdapter;

    private boolean listafinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.librosEncontrados);
        listadoLibrosAdapter = new listadoLibrosAdapter(this);
        recyclerView.setAdapter(listadoLibrosAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0){
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                    if(listafinal){
                        if((visibleItemCount+pastVisibleItems)>=totalItemCount){
                            Log.i(TAG,"Final de la lista.");
                            listafinal=false;
                            page++;
                            obtenerDatos(page);
                        }
                    }
                }
            }
        });


        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.itbook.store/1.0/")
                .addConverterFactory(GsonConverterFactory.create())
               .build();


        Busqueda = findViewById(R.id.Busqueda);
        ImageButton enviarBusqueda = findViewById(R.id.enviarBusqueda);
        listafinal=true;

        enviarBusqueda.setOnClickListener(view -> {

            if (!Busqueda.getText().toString().isEmpty()){
                listadoLibrosAdapter.limpiarLista();
                page=1;
                obtenerDatos(page);
            }else {
                Toast.makeText(getApplicationContext(), "Debes escribir tu consulta.", Toast.LENGTH_LONG).show();}

        });

    }

    private void obtenerDatos(int page) {
        BiblioapiService service = retrofit.create(BiblioapiService.class);
        Call<BiblioRespuesta> biblioRespuestaCall = service.obtenerListaLibros(Busqueda.getText().toString(),page);{
            biblioRespuestaCall.enqueue(new Callback<BiblioRespuesta>() {
                @Override
                public void onResponse(@NotNull Call<BiblioRespuesta> call, @NotNull Response<BiblioRespuesta> response) {
                    listafinal=true;
                    if (response.isSuccessful()){
                        BiblioRespuesta biblioRespuesta = response.body();
                        ArrayList <Biblioteca> listaBiblioteca = biblioRespuesta.getBooks();
                        listadoLibrosAdapter.agregarListaLibros(listaBiblioteca);

                    } else {
                        Log.e(TAG, "onResponse:"+ response.errorBody());
                    }

                }

                @Override
                public void onFailure(@NotNull Call<BiblioRespuesta> call, @NotNull Throwable t) {
                    listafinal=true;
                    Log.e(TAG, "onFailure:"+ t.getMessage());
                }
            });
        }
    }

}