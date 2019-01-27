package company.com.myapplication.webservice.client;

import java.util.List;

import company.com.myapplication.webservice.dto.DishDto;
import company.com.myapplication.webservice.dto.OrderRequestDto;
import company.com.myapplication.webservice.dto.OrderResponseDto;
import company.com.myapplication.webservice.dto.RestaurantDto;
import company.com.myapplication.webservice.dto.UserDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST("/users")
    Call<UserDto> saveUser(@Body UserDto userDto);
    @POST("/users/login")
    Call<Boolean> login(@Body UserDto userDto);
    @GET("/restaurants")
    Call<List<RestaurantDto>> getRestaurants();
    @GET("/dishes/byRestaurantId")
    Call<List<DishDto>> getDishesByRestaurantId(@Query("restaurantId") Long restaurantId);
    @POST("/orders")
    Call<OrderResponseDto> createOrder(@Body OrderRequestDto orderRequestDto);
}
