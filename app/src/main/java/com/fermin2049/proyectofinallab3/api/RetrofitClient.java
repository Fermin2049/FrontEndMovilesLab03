package com.fermin2049.proyectofinallab3.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.fermin2049.proyectofinallab3.models.Contract;
import com.fermin2049.proyectofinallab3.models.Inmueble;
import com.fermin2049.proyectofinallab3.models.Inquilino;
import com.fermin2049.proyectofinallab3.models.LoginResponse;
import com.fermin2049.proyectofinallab3.models.Pago;
import com.fermin2049.proyectofinallab3.models.Propietario;
import com.fermin2049.proyectofinallab3.models.RestablecerContrasenaRequest;
import com.fermin2049.proyectofinallab3.models.UpdatePasswordRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class RetrofitClient {
    private static final String URL_BASE = "http://192.168.1.2:5157/api/";
    private static final String TAG = "RetrofitClient";
    private static Retrofit retrofit = null;

    public static String getBaseUrl() {
        return URL_BASE;
    }

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
        return sharedPreferences.getString("jwt_token", "");
    }

    public static int getPropietarioIdFromToken(Context context) {
        String token = getToken(context);
        String[] parts = token.split("\\.");
        if (parts.length == 3) {
            String payload = new String(Base64.decode(parts[1], Base64.DEFAULT));
            try {
                JSONObject jsonObject = new JSONObject(payload);
                if (jsonObject.has("IdPropietario")) {
                    return jsonObject.getInt("IdPropietario");
                } else {
                    Log.e(TAG, "Token does not contain IdPropietario");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return -1; // Fallback value if IdPropietario is not found
    }

    public interface InmobliariaService {
        @FormUrlEncoded
        @POST("Propietarios/login")
        Call<LoginResponse> login(@Field("Usuario") String usuario, @Field("Clave") String clave);

        @POST("Propietarios")
        Call<Propietario> register(@Body Propietario propietario);

        @GET("Propietarios/me")
        Call<Propietario> getMyDetails();

        @GET("Inquilinos/{id}")
        Call<Inquilino> getInquilinoById(@Path("id") int id);

        @GET("Inquilinos/ByPropietario/{propietarioId}")
        Call<List<Inquilino>> getInquilinosByPropietarioId(@Path("propietarioId") int propietarioId);

        @GET("Contratos/ByPropietario/{propietarioId}")
        Call<List<Contract>> getContratosByPropietarioId(@Path("propietarioId") int propietarioId);

        @GET("Pagos/ByPropietario/{propietarioId}")
        Call<List<Pago>> getPagosByPropietarioId(@Path("propietarioId") int propietarioId);

        @GET("Inmuebles/ByPropietario/{propietarioId}")
        Call<List<Inmueble>> getInmueblesByPropietarioId(@Path("propietarioId") int propietarioId);

        @POST("Propietarios/{id}/restablecer-contrasena")
        Call<ResponseBody> restablecerContrasena(@Path("id") int id, @Body RestablecerContrasenaRequest request);

        @FormUrlEncoded
        @POST("Propietarios/solicitar-restablecimiento")
        Call<ResponseBody> solicitarRestablecimiento(@Field("email") String email);

        @Multipart
        @PUT("Propietarios/{id}")
        Call<Propietario> updatePropietarioWithImage(
                @Path("id") int id,
                @Part("idPropietario") RequestBody idPropietario,
                @Part("dni") RequestBody dni,
                @Part("apellido") RequestBody apellido,
                @Part("nombre") RequestBody nombre,
                @Part("telefono") RequestBody telefono,
                @Part("email") RequestBody email,
                @Part("currentPassword") RequestBody currentPassword,
                @Part("newPassword") RequestBody newPassword,
                @Part MultipartBody.Part fotoPerfil
        );

        @Multipart
        @PUT("Propietarios/{id}")
        Call<Propietario> updatePropietarioWithImageNoPassword(
                @Path("id") int id,
                @Part("dni") RequestBody dni,
                @Part("apellido") RequestBody apellido,
                @Part("nombre") RequestBody nombre,
                @Part("telefono") RequestBody telefono,
                @Part("email") RequestBody email,
                @Part MultipartBody.Part fotoPerfil
        );

        @Multipart
        @PUT("Inmuebles/{id}")
        Call<Inmueble> updateInmuebleWithImage(
                @Path("id") int id,
                @Part("idInmueble") RequestBody idInmueble,
                @Part("direccion") RequestBody direccion,
                @Part("uso") RequestBody uso,
                @Part("tipo") RequestBody tipo,
                @Part("ambientes") RequestBody ambientes,
                @Part("precio") RequestBody precio,
                @Part("estado") RequestBody estado,
                @Part MultipartBody.Part fotoInmueble
        );

        @Multipart
            @POST("Inmuebles")
            Call<Inmueble> addInmueble(
                @Part("direccion") RequestBody direccion,
                @Part("uso") RequestBody uso,
                @Part("tipo") RequestBody tipo,
                @Part("ambientes") RequestBody ambientes,
                @Part("precio") RequestBody precio,
                @Part("estado") RequestBody estado,
                @Part("idPropietario") RequestBody idPropietario,
                @Part MultipartBody.Part imagen
            );

        @PATCH("Inmuebles/{id}/estado")
        Call<Void> updateEstadoInmueble(@Path("id") int id, @Body String nuevoEstado);

        @PUT("Propietarios/{id}/update-password")
        Call<Void> updatePassword(@Path("id") int id, @Body UpdatePasswordRequest request);
    }
}