package company.com.myapplication.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import company.com.myapplication.R;
import company.com.myapplication.adapter.DishesListAdapter;
import company.com.myapplication.database.model.HistoryEntity;
import company.com.myapplication.database.view_model.HistoryViewModel;
import company.com.myapplication.gps.AppLocationService;
import company.com.myapplication.session.SessionCache;
import company.com.myapplication.utils.Utils;
import company.com.myapplication.webservice.client.ApiService;
import company.com.myapplication.webservice.client.ApiUtils;
import company.com.myapplication.webservice.dto.DishDto;
import company.com.myapplication.webservice.dto.OrderRequestDto;
import company.com.myapplication.webservice.dto.OrderResponseDto;

public class SummaryActivity extends AppCompatActivity {
    private ApiService apiService;
    private List<DishDto> dishes = SessionCache.getInstance().getDishes();
    private Long restaurantId;
    private String restaurantName;
    private String restaurantAddress;
    private AppLocationService appLocationService;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        apiService = ApiUtils.getAPIService();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewSummary);
        final DishesListAdapter adapter = new DishesListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        restaurantId = getIntent().getLongExtra("restaurantId", 0);
        restaurantName = getIntent().getStringExtra("restaurantName");
        restaurantAddress = getIntent().getStringExtra("restaurantAddress");
        adapter.setDishes(dishes);
        appLocationService = new AppLocationService(
                SummaryActivity.this);
        this.handleBackButton();
        this.handleOrderButton();
    }

    //wyslac,zapisac w bazie,toast z response
    private void handleOrderButton() {
        Button button = findViewById(R.id.summaryOrderButton);
        button.setOnClickListener(view -> {
            OrderRequestDto orderRequestDto = new OrderRequestDto();
            orderRequestDto.setUsername(SessionCache.getInstance().getLoggedUsername());
            Location gpsLocation = appLocationService
                    .getLocation(LocationManager.GPS_PROVIDER);
            if (gpsLocation != null) {
                double latitude = gpsLocation.getLatitude();
                double longitude = gpsLocation.getLongitude();
              address = "Latitude: " + gpsLocation.getLatitude() +
                        " Longitude: " + gpsLocation.getLongitude();
            }
            else {
                address = "Bialystok, Wiejska 45A";
            }
            this.insertIntoHistory();
            this.createOrder();
        });
    }

    private void handleBackButton() {
        Button button = findViewById(R.id.summaryBackButton);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), DishesListActivity.class);
            intent.putExtra("restaurantId", restaurantId);
            intent.putExtra("restaurantName", restaurantName);
            intent.putExtra("restaurantAddress", restaurantAddress);
            view.getContext().startActivity(intent);
        });
    }

    private void insertIntoHistory() {
        HistoryViewModel historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setUsername(SessionCache.getInstance().getLoggedUsername());
        historyEntity.setRestaurantName(restaurantName);
        historyEntity.setRestaurantAddress(restaurantAddress);
        Double payment = 0D;
        for (DishDto dishDto : SessionCache.getInstance().getDishes()) {
            payment = payment + dishDto.getPrice();
        }
        historyEntity.setPayment(payment);
        historyViewModel.insert(historyEntity);
    }

    private void createOrder() {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setAddress(address);
        orderRequestDto.setUsername(SessionCache.getInstance().getLoggedUsername());
        orderRequestDto.setDishes(SessionCache.getInstance().getDishes());
        OrderResponseDto orderResponseDto = null;
        try {
            orderResponseDto = apiService.createOrder(orderRequestDto).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (orderResponseDto != null) {
            String toastText = getResources().getString(R.string.toastSummary) +" " + getResources().getString(R.string.toastSummary_date)+" " + orderResponseDto.getOrderDate() +" " + getResources().getString(R.string.toastSummary_restaurantAddress)+" " + orderResponseDto.getAddress()+" " + getResources().getString(R.string.toastSummary_price) +" " +orderResponseDto.getPrice()+"zl";
            ConstraintLayout constraintLayout = (ConstraintLayout)findViewById(R.id.summaryConstraintLayout);
            Snackbar snackbar = Snackbar.make(constraintLayout,toastText,Snackbar.LENGTH_LONG);
            snackbar.show();
            SessionCache.getInstance().removeDishes();
        }
    }
}
