// DataUtils.java
package com.erns.canvaspre.utils;

import android.content.Context;
import android.util.Log;

import com.erns.canvaspre.model.Painting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase utilitaria para manejar la lectura y parseo de archivos de datos.
 */
public class DataUtils {

    private static final String TAG = "DataUtils";

    /**
     * Lee el archivo Pictures.txt desde assets y retorna una lista de objetos Painting.
     *
     * @param context Contexto de la aplicación.
     * @return Lista de pinturas.
     */
    public static List<Painting> loadPaintingsFromAssets(Context context) {
        List<Painting> paintings = new ArrayList<>();
        BufferedReader reader = null;

        try {
            InputStream is = context.getAssets().open("Pictures.txt");
            reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("\\|");
                if (tokens.length < 8) {
                    Log.e(TAG, "Formato incorrecto en la línea: " + line);
                    continue;
                }

                int id = Integer.parseInt(tokens[0].trim());
                String artist = tokens[1].trim();
                String title = tokens[2].trim();
                String imageLink = tokens[3].trim();
                int roomId = Integer.parseInt(tokens[4].trim());
                float x = Float.parseFloat(tokens[5].trim());
                float y = Float.parseFloat(tokens[6].trim());
                String description = tokens[7].trim();

                Painting painting = new Painting(id, artist, title, imageLink, roomId, x, y, description);
                paintings.add(painting);
            }

        } catch (IOException e) {
            Log.e(TAG, "Error al leer el archivo Pictures.txt", e);
        } catch (NumberFormatException e) {
            Log.e(TAG, "Error al parsear números en Pictures.txt", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "Error al cerrar el lector", e);
                }
            }
        }

        return paintings;
    }
}
