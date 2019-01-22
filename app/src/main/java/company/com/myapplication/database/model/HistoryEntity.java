package company.com.myapplication.database.model;

import java.time.LocalDateTime;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "History_T")
public class HistoryEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @NonNull
    private String restaurantName;
    @NonNull
    private String restaurantAddress;
    @NonNull
    private Double payment;
    @NonNull
    private String username;//TODO should be userId and OneToMany

    public HistoryEntity() {
    }

    public HistoryEntity(@NonNull Long id, @NonNull String restaurantName, @NonNull String restaurantAddress, @NonNull Double payment, @NonNull String username) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.payment = payment;
        this.username = username;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    @NonNull
    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(@NonNull String restaurantName) {
        this.restaurantName = restaurantName;
    }

    @NonNull
    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(@NonNull String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    @NonNull
    public Double getPayment() {
        return payment;
    }

    public void setPayment(@NonNull Double payment) {
        this.payment = payment;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }
}
