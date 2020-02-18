package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.PeripheralDevice;

public class JSONConverter {

    private static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

    public static String getJSONfromObject(Object object) {
        return GSON.toJson(object);
    }

    public static PeripheralDevice getPeripheralDeviceFromJSON(String str) {
        return GSON.fromJson(str, PeripheralDevice.class);
    }
}
