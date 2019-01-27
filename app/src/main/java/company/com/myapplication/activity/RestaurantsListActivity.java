package company.com.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import company.com.myapplication.R;
import company.com.myapplication.adapter.DishesListAdapter;
import company.com.myapplication.adapter.RestaurantListAdapter;
import company.com.myapplication.listener.RecyclerRestaurantClickListener;
import company.com.myapplication.session.SessionCache;
import company.com.myapplication.utils.Utils;
import company.com.myapplication.webservice.client.ApiService;
import company.com.myapplication.webservice.client.ApiUtils;
import company.com.myapplication.webservice.dto.RestaurantDto;
import retrofit2.Response;

public class RestaurantsListActivity extends AppCompatActivity {
    private ApiService apiService;
    private List<RestaurantDto> restaurants;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurants_list);
        apiService = ApiUtils.getAPIService();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewRestaurant);
        final RestaurantListAdapter adapter = new RestaurantListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.setListener();
        this.getRestaurantsFromServer();
        adapter.setRestaurants(restaurants);
        this.handleBackButton();
        SessionCache.getInstance().removeDishes();
    }

    private void handleBackButton(){
        Button button = findViewById(R.id.restaurantsBackButton);
        button.setOnClickListener(view->{
            Intent intent = new Intent(view.getContext(), HomeActivity.class);
            view.getContext().startActivity(intent);
        });
    }
    private void getRestaurantsFromServer(){
        Response<List<RestaurantDto>> response = null;
        try {
            response = apiService.getRestaurants().execute();
        } catch (IOException e) {
            Utils.createToast(getApplicationContext(),getResources().getString(R.string.registration_user_created_network_problem));
        }
        if(response==null){
            Utils.createToast(getApplicationContext(),getResources().getString(R.string.registration_user_created_network_problem));
        }
        else {
            restaurants = response.body();
        }
    }

    private void setListener(){
        RecyclerView recyclerView = findViewById(R.id.recyclerViewRestaurant);
        recyclerView.addOnItemTouchListener(
                new RecyclerRestaurantClickListener(getApplicationContext(), recyclerView ,new RecyclerRestaurantClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        Intent intent = new Intent(getApplicationContext(),DishesListActivity.class);
                        intent.putExtra("restaurantId",restaurants.get(position).getId());
                        intent.putExtra("restaurantName",restaurants.get(position).getName());
                        intent.putExtra("restaurantAddress",restaurants.get(position).getAddress());
                        view.getContext().startActivity(intent);
                    }
                })
        );
    }
}
