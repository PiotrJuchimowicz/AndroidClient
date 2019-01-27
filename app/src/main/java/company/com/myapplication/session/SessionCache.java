package company.com.myapplication.session;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import company.com.myapplication.webservice.dto.DishDto;

public class SessionCache {
    private static SessionCache INSTANCE;
    private String username;
    private List<DishDto> dishes;

    private SessionCache() {
        dishes = new LinkedList<>();
    }

    public static synchronized SessionCache getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SessionCache();
        }
        return INSTANCE;
    }

    public String getLoggedUsername() {
        return username;
    }

    public void setLoggedUsername(String username) {
        this.username = username;
    }

    public List<DishDto> getDishes() {
        return dishes;
    }

    public void removeDishes(){
        ListIterator<DishDto> listIterator = dishes.listIterator();
        while (listIterator.hasNext()){
            listIterator.next();
            listIterator.remove();
        }
    }

    public static void removeInstance(){
        INSTANCE = null;
    }
}
