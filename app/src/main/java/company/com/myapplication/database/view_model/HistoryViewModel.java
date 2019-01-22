package company.com.myapplication.database.view_model;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import company.com.myapplication.database.model.HistoryEntity;
import company.com.myapplication.database.repository.HistoryRepository;

public class HistoryViewModel extends AndroidViewModel {
    private HistoryRepository repository;
    private LiveData<List<HistoryEntity>> historyLiveData;

    public HistoryViewModel(Application application){
        super(application);
        repository = new HistoryRepository(application);
        //TODO test data
        /*HistoryEntity A = new HistoryEntity();
        A.setPayment(120.00);
        A.setRestaurantAddress("Wesola 16");
        A.setRestaurantName("Mily Burger");
        A.setUsername("Juchas");
        HistoryEntity B = new HistoryEntity();
        B.setPayment(128.00);
        B.setRestaurantAddress("Zwierzyniecka 2");
        B.setRestaurantName("Pelati");
        B.setUsername("Juchas");
        HistoryEntity C = new HistoryEntity();
        C.setPayment(20.00);
        C.setRestaurantAddress("Dluga");
        C.setRestaurantName("Mc Donald");
        C.setUsername("Kuc");
        repository.insert(A);
        repository.insert(B);
        repository.insert(C);*/
        historyLiveData = repository.getAll();
    }

    public void insert(HistoryEntity historyEntity){
        repository.insert(historyEntity);
    }

    public void delete(Long id){
        repository.delete(id);
    }

    public void update(HistoryEntity historyEntity){
        repository.update(historyEntity);
    }

    public LiveData<HistoryEntity> getOne(Long id){
        return repository.getOne(id);
    }

    public LiveData<List<HistoryEntity>> getHistoryLiveData() {
        return historyLiveData;
    }

    public void setHistoryLiveData(LiveData<List<HistoryEntity>> historyLiveData) {
        this.historyLiveData = historyLiveData;
    }
}
