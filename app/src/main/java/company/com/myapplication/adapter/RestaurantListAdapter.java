package company.com.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import company.com.myapplication.R;
import company.com.myapplication.webservice.dto.RestaurantDto;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder> {
    private final LayoutInflater layoutInflater;
    private List<RestaurantDto> restaurants;

    public RestaurantListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.restaurant_view_item, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder restaurantViewHolder, int position) {
        if (restaurants != null) {
            RestaurantDto current = restaurants.get(position);
            restaurantViewHolder.itemRestaurantName.setText(current.getName());
            restaurantViewHolder.itemRestaurantAddress.setText(current.getAddress());
        }
    }

    public void setRestaurants(List<RestaurantDto> objects) {
        restaurants = objects;
        this.notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
// mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (restaurants != null) {
            return restaurants.size();
        } else {
            return 0;
        }
    }

    class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemRestaurantName;
        private final TextView itemRestaurantAddress;

        private RestaurantViewHolder(View itemView) {
            super(itemView);
            itemRestaurantName = itemView.findViewById(R.id.restaurantName);
            itemRestaurantAddress = itemView.findViewById(R.id.restaurantAddress);
        }
    }
}


