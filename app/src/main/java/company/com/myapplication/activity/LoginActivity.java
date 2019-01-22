package company.com.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import company.com.myapplication.R;
import company.com.myapplication.session.SessionCache;
import company.com.myapplication.utils.Utils;
import company.com.myapplication.webservice.client.ApiService;
import company.com.myapplication.webservice.client.ApiUtils;
import company.com.myapplication.webservice.dto.UserDto;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private String username, password;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        apiService = ApiUtils.getAPIService();
        this.handleBackButton();
        this.handleContinueButton();
    }

    private void handleBackButton() {
        Button backButton = findViewById(R.id.loginBackButton);
        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            view.getContext().startActivity(intent);
        });
    }

    private void handleContinueButton() {
        Button continueButton = findViewById(R.id.loginContinueButton);
        continueButton.setOnClickListener(view -> {
            boolean isInputsCorrect = this.handleInputs();
            if (isInputsCorrect) {
                UserDto userDto = new UserDto();
                userDto.setUsername(username);
                userDto.setPassword(password);
                Response<Boolean> response = null;
                try {
                    response = apiService.login(userDto).execute();
                } catch (IOException e) {
                    Utils.createToast(getApplicationContext(), getResources().getString(R.string.registration_user_created_network_problem));
                }
                if(response==null){
                    return;
                }
                if (response.body() != null) {
                    if (response.body()) {
                        SessionCache.getInstance().setLoggedUsername(username);
                        Intent intent = new Intent(view.getContext(), HomeActivity.class);
                        view.getContext().startActivity(intent);
                    } else {
                        Utils.createToast(getApplicationContext(), getResources().getString(R.string.login_bad_form));

                    }
                }

            } else {
                Utils.createToast(getApplicationContext(), getResources().getString(R.string.registration_form_errors));
            }
        });
    }

    private boolean handleInputs() {
        final EditText usernameInput, passwordInput;
        usernameInput = findViewById(R.id.login_username);
        passwordInput = findViewById(R.id.login_password);
        if (!Utils.isInputEmpty(usernameInput)) {
            username = usernameInput.getText().toString();
        }
        if (!Utils.isInputEmpty(passwordInput)) {
            password = passwordInput.getText().toString();
        }
        return username != null && password != null;
    }
}
