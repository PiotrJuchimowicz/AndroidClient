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

public class RegistrationActivity extends AppCompatActivity {
    private String username, password, name, surname;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        apiService = ApiUtils.getAPIService();
        this.handleBackButton();
        this.handleContinueButton();
    }

    private void handleBackButton() {
        Button backButton = findViewById(R.id.registrationBackButton);
        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            view.getContext().startActivity(intent);
        });
    }

    private void handleContinueButton() {
        Button continueButton = findViewById(R.id.registrationContinueButton);
        continueButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), HomeActivity.class);
            boolean isInputsCorrect = this.handleInputs();
            if (!isInputsCorrect) {
                Utils.createToast(getApplicationContext(), getResources().getString(R.string.registration_form_errors));
            } else {
                int status = this.postData();
                if (status == 400) {
                    Utils.createToast(getApplicationContext(), getResources().getString(R.string.registration_user_created_400));

                }
                if (status == 500) {
                    Utils.createToast(getApplicationContext(), getResources().getString(R.string.registration_user_created_500));

                }
                if (status == 200) {
                    Utils.createToast(getApplicationContext(), getResources().getString(R.string.registration_user_created_success));
                    SessionCache.getInstance().setLoggedUsername(username);
                    view.getContext().startActivity(intent);
                }
            }
        });
    }

    private boolean handleInputs() {
        final EditText usernameInput, passwordInput, nameInput, surnameInput;
        usernameInput = findViewById(R.id.registration_username);
        passwordInput = findViewById(R.id.registration_password);
        nameInput = findViewById(R.id.registration_name);
        surnameInput = findViewById(R.id.registration_surname);
        if (!Utils.isInputEmpty(usernameInput)) {
            username = usernameInput.getText().toString();
        }
        if (!Utils.isInputEmpty(passwordInput)) {
            password = passwordInput.getText().toString();
        }
        if (!Utils.isInputEmpty(nameInput)) {
            name = nameInput.getText().toString();
        }
        if (!Utils.isInputEmpty(surnameInput)) {
            surname = surnameInput.getText().toString();
        }
        return username != null && password != null && name != null && surname != null;
    }

    private int postData() {
        UserDto userDto = new UserDto(username, password, name, surname);
        Response<UserDto> response = null;
        try {
            response = apiService.saveUser(userDto).execute();
        } catch (IOException e) {
            Utils.createToast(getApplicationContext(), getResources().getString(R.string.registration_user_created_network_problem));
            return 500;
        }
        return response.code();
        /*apiService.saveUser(userDto).enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if (response.code() == 400) {
                    Utils.createToast(getApplicationContext(), getResources().getString(R.string.registration_user_created_500));

                }
                if (response.code() == 500) {
                    Utils.createToast(getApplicationContext(), getResources().getString(R.string.registration_user_created_400));

                }

                if (response.isSuccessful()) {
                    Log.i("INFO", "post submitted to API." + response.body().toString());
                    Utils.createToast(getApplicationContext(), getResources().getString(R.string.registration_user_created_success));
                }
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                Utils.createToast(getApplicationContext(), getResources().getString(R.string.registration_user_created_network_problem));
                Log.e("ERROR", "Unable to submit post to API.");
            }
        });*/
    }

}
