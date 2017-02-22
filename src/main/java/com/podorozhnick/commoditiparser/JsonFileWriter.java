package com.podorozhnick.commoditiparser;

import com.google.gson.Gson;

public class JsonFileWriter<T> {

    private T t;
    private Gson gson;

    public JsonFileWriter(T t) {
        this.t = t;
        this.gson = new Gson();
    }

    public void write(String filePath) {
        String string = gson.toJson(t);
        new FileSaver(string, filePath).save();
    }
}
