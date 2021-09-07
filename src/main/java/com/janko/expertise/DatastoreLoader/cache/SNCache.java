package com.janko.expertise.DatastoreLoader.cache;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SNCache {

    private List<String> snList;

    public SNCache() {
    }

    public SNCache(List<String> snList) {
        this.snList = snList;
    }

    public List<String> getSnList() {
        return snList;
    }

    public void setSnList(List<String> snList) {
        this.snList = snList;
    }
}
