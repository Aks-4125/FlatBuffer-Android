
package com.flatbuffer.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Aks4125 on 30/03/18.
 */
public class Utils {

    public static byte[] readRawResource(Context context, int resId) {
        InputStream stream = null;
        byte[] buffer = null;
        try {
            stream = context.getResources().openRawResource(resId);
            buffer = new byte[stream.available()];
            while (stream.read(buffer) != -1) ;
        } catch (IOException e) {
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                }
            }
        }
        return buffer;
    }
}
