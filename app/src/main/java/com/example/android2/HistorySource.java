package com.example.android2;

import java.util.List;

public class HistorySource {

    private final HistoryDao historyDao;
    private List<History> histories;

    public HistorySource(HistoryDao historyDao) {
        this.historyDao = historyDao;
    }

    public List<History> getHistories(){
        if (histories == null){
            histories = historyDao.getAllHistory();
        }
        return histories;
    }

    public long getCountHistories(){
        return historyDao.getCountHistory();
    }

    public void addHistory(History history){
        historyDao.insertHistory(history);
        histories = historyDao.getAllHistory();
    }

    public void removeHistory(long id){
        historyDao.deleteHistoryById(id);
        histories = historyDao.getAllHistory();
    }
}
