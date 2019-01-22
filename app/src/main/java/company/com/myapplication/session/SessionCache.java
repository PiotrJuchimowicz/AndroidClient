package company.com.myapplication.session;

public class SessionCache {
    private static SessionCache INSTANCE;
    private String username;

    private SessionCache() {
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
}
