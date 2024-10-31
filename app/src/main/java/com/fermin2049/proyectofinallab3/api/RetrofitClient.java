package com.fermin2049.proyectofinallab3.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fermin2049.proyectofinallab3.models.Contract;
import com.fermin2049.proyectofinallab3.models.Inmueble;
import com.fermin2049.proyectofinallab3.models.Inquilino;
import com.fermin2049.proyectofinallab3.models.LoginResponse;
import com.fermin2049.proyectofinallab3.models.Pago;
import com.fermin2049.proyectofinallab3.models.Propietario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class RetrofitClient {
    private static final String URL_BASE = "http://192.168.1.2:5157/api/";
    private static final String TAG = "RetrofitClient";
    private static Retrofit retrofit = null;

    public static InmobliariaService getInmobiliariaService(Context context) {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new CustomDateTypeAdapter())
                    .setLenient()
                    .create();

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();
                            String token = getToken(context);
                            Log.d(TAG, "Token: " + token);
                            Request.Builder requestBuilder = original.newBuilder()
                                    .header("Authorization", "Bearer " + token);
                            Request request = requestBuilder.build();
                            return chain.proceed(request);
                        }
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit.create(InmobliariaService.class);
    }

    private static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("jwt_token", "");
        Log.d(TAG, "Retrieved token: " + token);
        return token;
    }

    public interface InmobliariaService {
        @FormUrlEncoded
        @POST("Propietarios/login")
        Call<LoginResponse> login(@Field("Usuario") String usuario, @Field("Clave") String clave);

        @POST("Propietarios")
        Call<Propietario> register(@Body Propietario propietario);

        @GET("Propietarios/me")
        Call<Propietario> getMyDetails();

        @GET("Inquilinos/ByPropietario/{propietarioId}")
        Call<List<Inquilino>> getInquilinosByPropietarioId(@Path("propietarioId") int propietarioId);

        @GET("Contratos/ByPropietario/{propietarioId}")
        Call<List<Contract>> getContratosByPropietarioId(@Path("propietarioId") int propietarioId);

        @GET("Pagos/ByPropietario/{propietarioId}")
        Call<List<Pago>> getPagosByPropietarioId(@Path("propietarioId") int propietarioId);

        @GET("Inmuebles/ByPropietario/{propietarioId}")
        Call<List<Inmueble>> getInmueblesByPropietarioId(@Path("propietarioId") int propietarioId);
    }
}