package com.bnelson.triton.server.util;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by brnel on 7/20/2017.
 */
public class FileReaderWriterUtil {

    public static boolean exists(File file, boolean makeIfNotExist){
        if(!file.exists()){
            if(makeIfNotExist){
                return file.mkdirs();
            }
            return false;
        }
        return true;
    }

    public static <T> boolean writeToFile(File file, Gson gson, T t){
        try(BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))){
            out.write(gson.toJson(t).getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static <T> T readFromFile(File file, Gson gson, Class<T> clazz){
        try(FileReader in = new FileReader(file)){
            return gson.fromJson(in, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
