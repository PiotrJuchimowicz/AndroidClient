package company.com.myapplication.webservice.client;

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "http://192.168.43.150:8080/";

    public static ApiService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}
