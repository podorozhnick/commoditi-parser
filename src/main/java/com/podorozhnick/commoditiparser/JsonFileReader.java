package com.podorozhnick.commoditiparser;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonFileReader<T> {

    private String filePath;
    private Gson gson;

    public JsonFileReader(String filePath) {
        this.filePath = filePath;
        this.gson = new Gson();
    }

    public T read(Class<T> tClass) {
        T object = null;
        try {
            FileReader reader = new FileReader(filePath);
            object = gson.fromJson(reader, tClass);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }
}
