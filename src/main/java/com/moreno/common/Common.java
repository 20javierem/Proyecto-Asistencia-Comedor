package com.moreno.common;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.IOException;

public class Common {

    public static void initFireBase(){
        FileInputStream refreshToken = null;
        try {
            refreshToken = new FileInputStream("credentials.json");
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .setDatabaseUrl("https://proyecto-movilesii-default-rtdb.firebaseio.com/")
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FirebaseDatabase getDatabase(){
        return FirebaseDatabase.getInstance();
    }

}
