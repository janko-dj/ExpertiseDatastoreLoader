package com.janko.expertise.DatastoreLoader.service;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatastoreService implements ServiceInterface {

    private final Datastore datastore;

    @Autowired
    public DatastoreService(Datastore datastore) {
        this.datastore = datastore;
    }

    public void putEntity(Entity entity) {
        datastore.put(entity);
    }

    public Key getTaskKey(String kind, String key) {
        return datastore.newKeyFactory().setKind(kind).newKey(key);
    }

    public Entity getEntityByKey(Key key) {
        return datastore.get(key);
    }
}
