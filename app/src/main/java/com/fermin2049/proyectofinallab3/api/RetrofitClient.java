package com.fermin2049.proyectofinallab3.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class RetrofitClient {
    private static final String URL_BASE = "http://192.168.1.2:5157/api/";

    public static InmobliariaService getInmobiliariaService() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(InmobliariaService.class);
    }

    //Creamos interfas
    public interface InmobliariaService {
        @FormUrlEncoded
        @POST("Propietarios/login")
        Call<String> login(@Field("Usuario") String usuario, @Field("Clave") String clave);
    }
}