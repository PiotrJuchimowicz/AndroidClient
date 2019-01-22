package company.com.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import company.com.myapplication.R;
import company.com.myapplication.adapter.HistoryListAdapter;
import company.com.myapplication.database.model.HistoryEntity;
import company.com.myapplication.database.view_model.HistoryViewModel;

public class HistoryListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_list);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final HistoryListAdapter adapter = new HistoryListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        HistoryViewModel historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        historyViewModel.getHistoryLiveData().observe(this, adapter::setHistoryEntities);
        this.handleBackButton();
    }

    private void handleBackButton(){
       Button backButton = findViewById(R.id.historyBackButton);
       backButton.setOnClickListener(view ->{
           Intent intent = new Intent(view.getContext(), HomeActivity.class);
           view.getContext().startActivity(intent);
       });
    }
}
