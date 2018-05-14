package com.simudyne.service;

import com.simudyne.distruptor.RingFencing;
import com.simudyne.repository.CsvToRiskMeasureObject;
import com.simudyne.repository.RiskMeasureCache;
import com.simudyne.rest.ResultCollector;
import com.simudyne.rest.RiskMeasure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ModelCoordinator {

    public static final Logger LOGGER = LoggerFactory.getLogger(ModelCoordinator.class);

    public static final double MIN_BRAND_FACTOR= 0.1;
    public static final double MAX_BRAND_FACTOR = 2.9;

    @Autowired
    CsvToRiskMeasureObject csvToRiskMeasureObject;

    @Autowired
    RiskMeasureCache riskMeasureCache;

    @Autowired
    RingFencing ringFencing;

    public List<ResultCollector> processValuation(double brandFactor) throws InterruptedException {

        if ((brandFactor < MIN_BRAND_FACTOR) || (brandFactor > MAX_BRAND_FACTOR)){
            LOGGER.info("*** BRAND NOT  ALLOWED ***");
            return null;
        }

        List<RiskMeasure> fileBasedRiskMeasures = csvToRiskMeasureObject.constructRiskMeasureFromFile();

        Objects.nonNull(fileBasedRiskMeasures);

        List<ResultCollector> resultCollectors = new ArrayList<>(fileBasedRiskMeasures.size());
        List<String> keys = new ArrayList<>(fileBasedRiskMeasures.size());
        for (RiskMeasure riskMeasure : fileBasedRiskMeasures) {

            ResultCollector resultCollector;

            String key = riskMeasure.getPolicyId() + riskMeasure.getAgentBreed() + brandFactor;


            if (riskMeasureCache.getTokenFromCache(key) == null) {
                resultCollector = new ResultCollector(riskMeasure.getPolicyId(), riskMeasure.getAgentBreed(), brandFactor);
                riskMeasureCache.addTokenToCache(key, resultCollector);
            } else {
                resultCollector = riskMeasureCache.getTokenFromCache(key);
                LOGGER.info("RiskMeasure::KEY::GetResults:::" + resultCollector);
            }

            ringFencing.handleEvent(riskMeasure, brandFactor, resultCollector);

            keys.add(key);
        }
        //Let System finish calculation
        Thread.sleep(10000);
        for (String key : keys)
            resultCollectors.add(riskMeasureCache.getTokenFromCache(key));

        return resultCollectors;
    }

}
