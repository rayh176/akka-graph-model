package com.simudyne.model;

import com.simudyne.rest.RiskMeasure;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RiskCalculator {

    public double simulateModel(RiskMeasure riskMeasure){

        Objects.requireNonNull(riskMeasure);

        if (riskMeasure.getAutoRenew() != 1){
            double rand = Math.random() * 3;
            return (riskMeasure.getPaymentAtPurchase()/ riskMeasure.getAttributePrice())
                                + (rand * riskMeasure.getAttributePromotions() * riskMeasure.getInertiaForSwitch());

        }

        return 0;
    }
}
