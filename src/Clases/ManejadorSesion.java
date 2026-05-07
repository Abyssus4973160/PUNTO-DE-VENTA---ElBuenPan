/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author ferna
 */
import java.io.*;
import java.util.Properties;

public class ManejadorSesion {
    private static final String FILE_NAME = "session.properties";

    public static void guardarSesion(String token) {
        Properties props = new Properties();
        props.setProperty("token", token);
        try (FileOutputStream out = new FileOutputStream(FILE_NAME)) {
            props.store(out, "Sesion de El Buen Pan");
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static String leerToken() {
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(FILE_NAME)) {
            props.load(in);
            return props.getProperty("token");
        } catch (IOException e) { return null; }
    }

    public static void borrarSesion() {
        File file = new File(FILE_NAME);
        if (file.exists()) file.delete();
    }
}
