package company.com.myapplication.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;
import company.com.myapplication.database.AppDatabase;
import company.com.myapplication.database.dao.HistoryDao;
import company.com.myapplication.database.model.HistoryEntity;
import company.com.myapplication.session.SessionCache;

public class HistoryRepository {
    private HistoryDao historyDao;
    private LiveData<List<HistoryEntity>> historyEntities;

    public HistoryRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        historyDao = appDatabase.getHistoryDao();
        historyEntities = historyDao.getAll(SessionCache.getInstance().getLoggedUsername());

    }

    public LiveData<HistoryEntity> getOne(final Long dishId) {
        return historyDao.getOne(dishId);
    }

    public LiveData<List<HistoryEntity>> getAll() {
        return historyDao.getAll(SessionCache.getInstance().getLoggedUsername());
    }

    public void insert(final HistoryEntity historyEntity) {
        new insertAsyncHistory(historyDao).execute(historyEntity);
    }

    public void update(final HistoryEntity historyEntity) {
        new updateAsyncHistory(historyDao).execute(historyEntity);
    }

    public void delete(final Long id) {
        final LiveData<HistoryEntity> historyLiveData = this.getOne(id);
        if (historyLiveData != null) {
            new deleteAsyncHistory(historyDao).execute(historyLiveData.getValue());
        }
    }

    private static class deleteAsyncHistory extends AsyncTask<HistoryEntity, Void, Void> {

        private HistoryDao asyncHistoryDao;

        deleteAsyncHistory(HistoryDao dao) {
            asyncHistoryDao = dao;
        }

        @Override
        protected Void doInBackground(final HistoryEntity... params) {
            asyncHistoryDao.delete(params[0]);
            return null;
        }
    }

    private static class updateAsyncHistory extends AsyncTask<HistoryEntity, Void, Void> {

        private HistoryDao asyncHistoryDao;

        updateAsyncHistory(HistoryDao dao) {
            asyncHistoryDao = dao;
        }

        @Override
        protected Void doInBackground(final HistoryEntity... params) {
            asyncHistoryDao.update(params[0]);
            return null;
        }
    }

    private static class insertAsyncHistory extends AsyncTask<HistoryEntity, Void, Void> {

        private HistoryDao asyncHistoryDao;

        insertAsyncHistory(HistoryDao dao) {
            asyncHistoryDao = dao;
        }

        @Override
        protected Void doInBackground(final HistoryEntity... params) {
            asyncHistoryDao.insert(params[0]);
            return null;
        }

    }
}
