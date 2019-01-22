
package company.com.myapplication.adapter;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import company.com.myapplication.R;
import company.com.myapplication.database.model.HistoryEntity;


public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryViewHolder> {
    private final LayoutInflater layoutInflater;
    private List<HistoryEntity> historyEntities;

    public HistoryListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.activity_recycler_view_item, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder historyViewHolder, int position) {
        if (historyEntities != null) {
            HistoryEntity current = historyEntities.get(position);
            historyViewHolder.itemRestaurantName.setText(current.getRestaurantName());
            historyViewHolder.itemRestaurantAddress.setText(current.getRestaurantAddress());
            historyViewHolder.itemPayment.setText(current.getPayment().toString() +" zl");
            Random random = new Random();
            int imageNumber = random.nextInt(6) +1;
            switch (imageNumber)  {
                case 1:{
                    historyViewHolder.foodImage.setImageResource(R.drawable.food1);
                    break;
                }
                case 2:{
                    historyViewHolder.foodImage.setImageResource(R.drawable.food2);
                    break;
                }
                case 3:{
                    historyViewHolder.foodImage.setImageResource(R.drawable.food3);
                    break;
                }
                case 4:{
                    historyViewHolder.foodImage.setImageResource(R.drawable.food4);
                    break;
                }
                case 5:{
                    historyViewHolder.foodImage.setImageResource(R.drawable.food5);
                    break;
                }
                case 6:{
                    historyViewHolder.foodImage.setImageResource(R.drawable.food6);
                    break;
                }
                case 7:{
                    historyViewHolder.foodImage.setImageResource(R.drawable.food7);
                    break;
                }
                default:{
                    historyViewHolder.foodImage.setImageResource(R.drawable.food1);
                }
            }
        } else {
            historyViewHolder.itemRestaurantAddress.setText(" ");
            historyViewHolder.itemRestaurantName.setText(" ");
            historyViewHolder.itemPayment.setText(" ");
            historyViewHolder.foodImage.setImageResource(R.drawable.ic_launcher_background);
        }
    }

    public void setHistoryEntities(List<HistoryEntity> entities) {
        historyEntities = entities;
        this.notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (historyEntities != null) {
            return historyEntities.size();
        } else {
            return 0;
        }
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemRestaurantName;
        private final TextView itemRestaurantAddress;
        private final TextView itemPayment;
        private final ImageView foodImage;

        private HistoryViewHolder(View itemView) {
            super(itemView);
            itemRestaurantName = itemView.findViewById(R.id.itemRestaurantName);
            itemRestaurantAddress = itemView.findViewById(R.id.itemRestaurantAddress);
            itemPayment = itemView.findViewById(R.id.itemPayment);
            foodImage = itemView.findViewById(R.id.foodImage);
        }
    }
}
