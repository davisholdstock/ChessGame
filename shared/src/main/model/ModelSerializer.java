package model;

import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.StringReader;

public class ModelSerializer {
    public static <T> T deserialize(String json, Class<T> responseClass) {
        return deserialize(new StringReader(json), responseClass);
    }

    public static <T> T deserialize(Reader reader, Class<T> responseClass) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create().fromJson(reader, responseClass);
    }
}
