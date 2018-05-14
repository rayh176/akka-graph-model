package com.simudyne.model;

import com.simudyne.beans.RequestModelTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelValuator {

    @Autowired
    RiskCalculator calculator;

    public double processRiskCalculation(final RequestModelTask requestModelTask){
        return  calculator.simulateModel(requestModelTask.getRiskMeasure());
    }
}
