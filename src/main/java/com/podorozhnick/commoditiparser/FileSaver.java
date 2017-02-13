package com.podorozhnick.commoditiparser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileSaver {

    private String input;
    private String fileName;

    public FileSaver(String input, String fileName) {
        this.input = input;
        this.fileName = fileName;
    }

    public void save() {
        File file = new File(fileName);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(input);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
