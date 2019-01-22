package company.com.myapplication.webservice.client;

import company.com.myapplication.webservice.dto.UserDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/users")
    Call<UserDto> saveUser(@Body UserDto userDto);
    @POST("/users/login")
    Call<Boolean> login(@Body UserDto userDto);
}
