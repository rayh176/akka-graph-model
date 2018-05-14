package com.simudyne.controller;

import com.simudyne.rest.ResultCollector;
import com.simudyne.service.ModelCoordinator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class RiskMeasuresController<T> {

    @Autowired
    ModelCoordinator modelCoordinator;

    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_JSON)
    @GetMapping("/risk-measures/{brandFactor}")
    public ResponseEntity<List<ResultCollector>> registerRiskMeasure(@PathVariable (value="brandFactor") double brandFactor,
                                                           @HeaderParam("user") String user,
                                                           UriComponentsBuilder builder) throws InterruptedException {
        List<ResultCollector> resultCollectors = modelCoordinator.processValuation(brandFactor);
        HttpHeaders headers = new HttpHeaders();
        if (resultCollectors == null){
            return new ResponseEntity<>(new ArrayList<>(), headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(resultCollectors, headers, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value="/RiskMeasuresView/{brandFactor}",
            produces ={MediaType.TEXT_HTML},
            method = RequestMethod.GET)
    public String viewRiskMeasureResults(@PathVariable (value="brandFactor") double brandFactor){
        System.out.println("RiskMeasureResuts Invoked:::"+brandFactor);
        return "RiskMeasureResults";
    }

}
