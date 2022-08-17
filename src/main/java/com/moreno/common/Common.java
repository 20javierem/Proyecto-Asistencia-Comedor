package com.moreno.common;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.IOException;

public class Common {
    private static FirebaseOptions options;

    public static void initFireBase(){
        FileInputStream refreshToken;
        try {
            refreshToken = new FileInputStream("credentials.json");
            options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .setDatabaseUrl("https://comedor-universitario-a09f3-default-rtdb.firebaseio.com")
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FirebaseDatabase getDatabase(){
        return FirebaseDatabase.getInstance();
    }

    public static FirebaseAuth getAuth(){
        FirebaseApp defaultApp = FirebaseApp.initializeApp(options);
        return FirebaseAuth.getInstance(defaultApp);
    }

}
