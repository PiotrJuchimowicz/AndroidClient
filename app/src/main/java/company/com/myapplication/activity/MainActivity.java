package company.com.myapplication.activity;


import android.app.ActivityManager;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import company.com.myapplication.R;
import company.com.myapplication.database.model.HistoryEntity;
import company.com.myapplication.database.repository.HistoryRepository;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.handleRegistrationButton();
        this.handleLoginButton();
        this.handleExitButton();
    }

    private void handleExitButton(){
        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(view -> {
            finish();
            System.exit(0);
        });
    }

    private void handleRegistrationButton() {
        Button registrationButton = findViewById(R.id.registrationButton);
        registrationButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), RegistrationActivity.class);
            view.getContext().startActivity(intent);
        });
    }

    private void handleLoginButton() {
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), LoginActivity.class);
            view.getContext().startActivity(intent);
        });
    }
}

