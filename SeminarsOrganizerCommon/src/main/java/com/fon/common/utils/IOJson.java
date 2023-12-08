/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Aleksa
 */
public class IOJson {

    private static final String PARENT_PATH = "SeminarsOrganizerCommon/src/test/resources/com//fon/common";

    public static void serializeJson(Object obj, String fileName) {
        File file = getJSONPath(fileName);

        try (FileWriter fw = new FileWriter(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(obj, fw);
        } catch (IOException ex) {
            throw new AssertionError(ex.getMessage());
        }
    }

    public static Object deserializeJson(String fileName, Class className) {
        File file = getJSONPath(fileName);

        try (FileReader in = new FileReader(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Object objDeserialized = gson.fromJson(in, className);
            return objDeserialized;
        } catch (IOException ex) {
            throw new AssertionError(ex.getMessage());
        }
    }

    private static File getJSONPath(String fileName) {
        File file = new File(System.getProperty("user.dir"));
        String pathname = String.format("%s/%s/test_%s.json",
                file.getParentFile(),
                PARENT_PATH,
                fileName);
        file = new File(pathname);
        return file;
    }
}
