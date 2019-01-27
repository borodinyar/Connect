package com.example.myapplication;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class Java8Base64Image {
    /*
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] args) {
        String imagePath = "C:\\base64\\image.jpg";
        String base64ImageString = encoder(imagePath);
        System.out.println("Base64ImageString = " + base64ImageString);
        decoder(base64ImageString, "C:\\base64\\decoderimage.jpg");

        System.out.println("DONE!");

    }
    */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encoder(String imagePath) {
        String base64Image = "";
        File file = new File(imagePath);
        try (FileInputStream imageInFile = new FileInputStream(file)) {
            // Reading a Image file from file system
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            base64Image = Base64.getEncoder().encodeToString(imageData);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
        return base64Image;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void decoder(String base64Image, String pathFile) {
        try (FileOutputStream imageOutFile = new FileOutputStream(pathFile)) {
            // Converting a Base64 String into Image byte array
            byte[] imageByteArray = Base64.getDecoder().decode(base64Image);
            imageOutFile.write(imageByteArray);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
    }
}
