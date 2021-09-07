package com.janko.expertise.DatastoreLoader.service;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;

public interface ServiceInterface {

    /**
     * Method for updating Datastore.
     * @param entity, with which we update Datastore
     */
    void putEntity(Entity entity);

    /**
     * Method for retrieving the needed Key
     * @param kind
     * @param id
     * @return
     */
    Key getTaskKey(String kind, String id);

    /**
     * Method for retrieving Entity by key from Datastore.
     * @param key
     * @return
     */
    Entity getEntityByKey(Key key);
}
