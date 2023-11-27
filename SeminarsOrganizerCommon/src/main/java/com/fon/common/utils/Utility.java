/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;

/**
 *
 * @author Aleksa
 */
public class Utility {

    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    public static SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy | HH:mm");

    public static String encloseWithSingleQuotes(String string) {
        return "'" + string + "'";
    }

    public static boolean isStringNullOrBlank(String string) {
        return string == null || string.isBlank();
    }

    public static <T> T getDeepCopy(T obj) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        oos.writeObject(obj);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        
        return (T) ois.readObject();
    }
}
