package com.fermin2049.proyectofinallab3.api;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CustomDateTypeAdapter extends TypeAdapter<Date> {
    private final SimpleDateFormat dateFormat;

    public CustomDateTypeAdapter() {
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.forLanguageTag("es-AR"));
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/Argentina/Buenos_Aires"));
    }

    @Override
    public void write(JsonWriter out, Date value) throws IOException {
        out.value(dateFormat.format(value));
    }

    @Override
    public Date read(JsonReader in) throws IOException {
        try {
            return dateFormat.parse(in.nextString());
        } catch (ParseException e) {
            throw new IOException(e);
        }
    }
}