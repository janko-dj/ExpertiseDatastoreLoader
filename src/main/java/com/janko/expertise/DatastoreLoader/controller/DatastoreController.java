package com.janko.expertise.DatastoreLoader.controller;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.janko.expertise.DatastoreLoader.cache.GPSCCache;
import com.janko.expertise.DatastoreLoader.cache.SNCache;
import com.janko.expertise.DatastoreLoader.model.CoordinatesResponse;
import com.janko.expertise.DatastoreLoader.model.DatastoreResponse;
import com.janko.expertise.DatastoreLoader.model.MachineIds;
import com.janko.expertise.DatastoreLoader.service.ServiceInterface;
import com.janko.expertise.DatastoreLoader.util.ResponseTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class DatastoreController {

    private final ServiceInterface datastoreService;
    private final SNCache snCache;
    private final GPSCCache gpscCache;

    private final Logger logger = LoggerFactory.getLogger(DatastoreController.class);

    @Autowired
    public DatastoreController(ServiceInterface datastoreService, SNCache snCache, GPSCCache gpscCache) {
        this.datastoreService = datastoreService;
        this.snCache = snCache;
        this.gpscCache = gpscCache;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/all-machine-ids")
    public ResponseEntity<Object> getAllMachineIds() {
        logger.info("Called getAllMachineIds");
        MachineIds machineIds = new MachineIds(snCache.getSnList());
        return new ResponseEntity<>(machineIds, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/{id}/coordinates")
    public ResponseEntity<Object> getMachinesGPSCoordinates(@PathVariable(value = "id") String id) {
        logger.info("Called getMachinesGPSCoordinates with id: {}", id);
        CoordinatesResponse coordinatesResponse = new CoordinatesResponse(gpscCache.getGpsCoordinates().get(id));
        return new ResponseEntity<>(coordinatesResponse, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/{kind}/{id}/data")
    public ResponseEntity<Object> getSingleMachineData(@PathVariable(value = "kind") String kind,
                                                       @PathVariable(value = "id") String id) {
        logger.info("getSingleMachineData called with kind: {}, id: {}", kind, id);
        //TODO: validation of params
        Key key = datastoreService.getTaskKey(kind, id);
        Entity entity = datastoreService.getEntityByKey(key);
        if (entity != null) {
            logger.info("getSingleMachineData successful!");
            DatastoreResponse datastoreResponse = ResponseTransformer.transformResponse(entity, id);
            logger.info("Data retrieved: {}", datastoreResponse);
            return new ResponseEntity<>(datastoreResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("{\"ErrorMessage\"=\"Bad data provided, '" + kind + "' or '" + id + "'\"}",
                    HttpStatus.BAD_REQUEST);
        }
    }
}
