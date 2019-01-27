package company.com.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import company.com.myapplication.R;
import company.com.myapplication.session.SessionCache;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.handleLoggedAsHandler();
        this.handleLogoutButton();
        this.handleHistoryButton();
        this.handleOrderButton();
    }

    private void handleLoggedAsHandler(){
        TextView textView = findViewById(R.id.loggedAsHeader);
        textView.setText(getResources().getString(R.string.home_logged_as) +"  "+ SessionCache.getInstance().getLoggedUsername());
    }

    private void handleOrderButton(){
        Button button = findViewById(R.id.home_order);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), RestaurantsListActivity.class);
            view.getContext().startActivity(intent);
        });
    }

    private void handleLogoutButton(){
        Button button = findViewById(R.id.home_logout);
        button.setOnClickListener(view -> {
            SessionCache.removeInstance();
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            view.getContext().startActivity(intent);
        });
    }

    private void handleHistoryButton(){
        Button button = findViewById(R.id.home_history);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), HistoryListActivity.class);
            view.getContext().startActivity(intent);
        });
    }
}
