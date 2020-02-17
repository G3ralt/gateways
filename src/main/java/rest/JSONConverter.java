package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONConverter {

    private static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

    public static String getJSONfromObject(Object object) {
        return GSON.toJson(object);
    }

}
