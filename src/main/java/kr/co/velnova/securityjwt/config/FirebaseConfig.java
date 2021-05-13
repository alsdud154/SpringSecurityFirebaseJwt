package kr.co.velnova.securityjwt.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;

@Configuration
public class FirebaseConfig {

    @Value("${spring.firebase.account-file-url.user}")
    private String userFirebaseUrl;

    @Value("${spring.firebase.account-file-url.admin}")
    private String adminFirebaseUrl;

    // 관리자 FirebaseApp 설정
    @Bean
    public void adminFirebaseApp() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(adminFirebaseUrl))
                .createScoped(Collections.singletonList("https://www.googleapis.com/auth/cloud-platform"));

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();

        FirebaseApp.initializeApp(options, "adminFirebaseApp");
    }

    // 사용자 FirebaseApp 설정
    @Bean
    public void userFirebaseApp() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(userFirebaseUrl))
                .createScoped(Collections.singletonList("https://www.googleapis.com/auth/cloud-platform"));

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();

        FirebaseApp.initializeApp(options,"userFirebaseApp");

    }
}
