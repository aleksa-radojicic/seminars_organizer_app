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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Aleksa
 */
public class Utility {

    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    public static SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy | HH:mm");

    public static final String STRING_EMPTY = "";
    public static final String STRING_31_LENGTH = "R9ZltX2Pc5hGQ7b4FV3s2JrmDq0NoKU";
    public static final String STRING_61_LENGTH = "JkLm9#$pO67hTcVb8@kFgAs8+qWe3YnHj6GzXxCvB4!oPmIu1yTrahrdtjfyx";
    public static final String STRING_101_LENGTH = "3yXEUJshm4dYAf9kUFfTQ8IWojDjq2dyDWgl43yDYMtb4tfqdulJ1ZDABJECFZA1MaWDx5x87l760L74A6wZ1M9Dn8K0GYwr6a5Wd";
    public static final String STRING_201_LENGTH = "qlgdzChEwl08EyWWdAYAVrQZP6aDDPoPPyCVhc1je2lwfl5bRaiKWbqLBXzW25tC0IXdSe129YLbP0IXndbJadavEjz6wPZyDHTzcNdWalNDiKum2S1jG9FczcbFwljPIDLXV8GnOhcl3Abuzw6ChYtZmQnbIaRZlFTKsGfG2E0zEaCoYYga0U8jUZjL1tj9zkfhIT7EG";

    public static Date GET_DATE_FUTURE() throws ParseException {
        return DATE_FORMAT.parse("01.01.3333");
    }

    public static Date GET_DATE_PAST() throws ParseException {
        return DATE_FORMAT.parse("01.01.1999");
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
