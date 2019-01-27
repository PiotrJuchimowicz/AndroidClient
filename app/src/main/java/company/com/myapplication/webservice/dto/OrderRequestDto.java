package company.com.myapplication.webservice.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderRequestDto {
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("dishes")
    @Expose
    private List<DishDto> dishes;

    public OrderRequestDto() {
    }

    public OrderRequestDto(String address, String username, List<DishDto> dishes) {
        this.address = address;
        this.username = username;
        this.dishes = dishes;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<DishDto> getDishes() {
        return dishes;
    }

    public void setDishes(List<DishDto> dishes) {
        this.dishes = dishes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
