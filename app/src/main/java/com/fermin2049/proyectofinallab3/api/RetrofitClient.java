package com.fermin2049.proyectofinallab3.api;

import android.content.Context;
import android.content.SharedPreferences;

import com.fermin2049.proyectofinallab3.models.Property;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.io.IOException;

public class RetrofitClient {
    private static final String URL_BASE = "http://192.168.1.2:5157/api/";
    private static Retrofit retrofit = null;

    public static InmobliariaService getInmobiliariaService(Context context) {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().setLenient().create();

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();
                            Request.Builder requestBuilder = original.newBuilder()
                                    .header("Authorization", "Bearer " + getToken(context));
                            Request request = requestBuilder.build();
                            return chain.proceed(request);
                        }
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit.create(InmobliariaService.class);
    }

    private static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("jwt_token", "");
    }

    //Creamos interfas
    public interface InmobliariaService {
        @FormUrlEncoded
        @POST("Propietarios/login")
        Call<String> login(@Field("Usuario") String usuario, @Field("Clave") String clave);

        @GET("Propietarios/me")
        Call<Property> getMyDetails();
    }
}