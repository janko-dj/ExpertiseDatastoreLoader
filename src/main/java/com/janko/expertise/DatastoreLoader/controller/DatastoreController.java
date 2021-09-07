package com.janko.expertise.DatastoreLoader.controller;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.janko.expertise.DatastoreLoader.cache.SNCache;
import com.janko.expertise.DatastoreLoader.model.DatastoreResponse;
import com.janko.expertise.DatastoreLoader.service.DatastoreService;
import com.janko.expertise.DatastoreLoader.util.ResponseTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DatastoreController {

    private final DatastoreService datastoreService;
    private final SNCache snCache;

    @Autowired
    public DatastoreController(DatastoreService datastoreService, SNCache snCache) {
        this.datastoreService = datastoreService;
        this.snCache = snCache;
    }

    @GetMapping("/all-machine-ids")
    public ResponseEntity<Object> getAllMachinesIds() {
        return new ResponseEntity<>(snCache.getSnList(), HttpStatus.OK);
    }

    @GetMapping("/{kind}/{id}/data")
    public ResponseEntity<Object> getSingleMachineData(@PathVariable(value = "kind") String kind,
                                                       @PathVariable(value = "id") String id) {
        Key key = datastoreService.getTaskKey(kind, id);
        Entity entity = datastoreService.getEntityByKey(key);
        DatastoreResponse datastoreResponse = ResponseTransformer.transformResponse(entity, id);
        return new ResponseEntity<>(datastoreResponse, HttpStatus.OK);
    }
}
