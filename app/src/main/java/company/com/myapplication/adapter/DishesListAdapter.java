package company.com.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import company.com.myapplication.R;
import company.com.myapplication.webservice.dto.DishDto;

public class DishesListAdapter extends RecyclerView.Adapter<DishesListAdapter.DishesViewHolder> {
    private final LayoutInflater layoutInflater;
    private List<DishDto> dishes;

    public DishesListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public DishesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.dish_view_item, parent, false);
        return new DishesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishesViewHolder dishesViewHolder, int position) {
        if (dishes != null) {
            DishDto current = dishes.get(position);
            dishesViewHolder.itemDishName.setText(current.getName());
            dishesViewHolder.itemDishPrice.setText(current.getPrice() + " zl");
        }
    }




    public void setDishes(List<DishDto> objects) {
        dishes = objects;
        this.notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
// mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (dishes != null) {
            return dishes.size();
        } else {
            return 0;
        }
    }

    public class DishesViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemDishName;
        private final TextView itemDishPrice;

        private DishesViewHolder(View itemView) {
            super(itemView);
            itemDishName = itemView.findViewById(R.id.dishName);
            itemDishPrice = itemView.findViewById(R.id.dishPrice);
        }
    }
}