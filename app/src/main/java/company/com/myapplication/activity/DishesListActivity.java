package company.com.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.io.IOException;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import company.com.myapplication.R;
import company.com.myapplication.adapter.DishesListAdapter;
import company.com.myapplication.listener.RecyclerRestaurantClickListener;
import company.com.myapplication.session.SessionCache;
import company.com.myapplication.utils.Utils;
import company.com.myapplication.webservice.client.ApiService;
import company.com.myapplication.webservice.client.ApiUtils;
import company.com.myapplication.webservice.dto.DishDto;
import company.com.myapplication.webservice.dto.RestaurantDto;
import retrofit2.Response;

public class DishesListActivity extends AppCompatActivity {
    private ApiService apiService;
    private List<DishDto> dishes;
    private Long restaurantId;
    private String restaurantName;
    private String restaurantAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dish_list);
        apiService = ApiUtils.getAPIService();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewDishes);
        final DishesListAdapter adapter = new DishesListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.setListener();
        restaurantId = getIntent().getLongExtra("restaurantId", 0);
        restaurantName = getIntent().getStringExtra("restaurantName");
        restaurantAddress = getIntent().getStringExtra("restaurantAddress");
        this.getDishesFromServer();
        adapter.setDishes(dishes);
        this.handleBackButton();
        this.handleConfirmOrderButton();
    }

    private void handleBackButton() {
        Button button = findViewById(R.id.dishesBackButton);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), RestaurantsListActivity.class);
            view.getContext().startActivity(intent);
        });
    }

    private void getDishesFromServer() {
        Response<List<DishDto>> response = null;
        try {
            response = apiService.getDishesByRestaurantId(restaurantId).execute();
        } catch (IOException e) {
            Utils.createToast(getApplicationContext(), getResources().getString(R.string.registration_user_created_network_problem));
        }
        if (response == null) {
            Utils.createToast(getApplicationContext(), getResources().getString(R.string.registration_user_created_network_problem));
        } else {
            dishes = response.body();
        }
    }

    private void handleConfirmOrderButton(){
        Button button = findViewById(R.id.orderButton);
        button.setOnClickListener(view ->{
            Intent intent = new Intent(view.getContext(), SummaryActivity.class);
            intent.putExtra("restaurantId",restaurantId);
            intent.putExtra("restaurantName",restaurantName);
            intent.putExtra("restaurantAddress",restaurantAddress);
            view.getContext().startActivity(intent);
        });
    }

    private void setListener() {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewDishes);
        recyclerView.addOnItemTouchListener(
                new RecyclerRestaurantClickListener(getApplicationContext(), recyclerView, new RecyclerRestaurantClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        DishDto dishDto = dishes.get(position);
                        SessionCache.getInstance().getDishes().add(dishDto);
                        Utils.createToast(getApplicationContext(), getResources().getString(R.string.added_dish));
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        DishDto dishDto = dishes.get(position);
                        SessionCache.getInstance().getDishes().remove(dishDto);
                        Utils.createToast(getApplicationContext(), getResources().getString(R.string.removed_dish));
                    }
                })
        );
    }
}
