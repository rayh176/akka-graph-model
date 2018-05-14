package com.simudyne.beans;

import com.simudyne.rest.RiskMeasure;

import java.util.ArrayList;
import java.util.List;

public class RequestModelTaskRouterHelper {
    public static final String BREED_C = "Breed_C";
    public static final String BREED_NC = "Breed_NC";

    /**
     * Main method to route flows
     * @param riskMeasure
     * @param affinity
     * @param brandFactor
     * @return
     */
    public static List<RiskMeasure> buildRoutedRequest( RiskMeasure riskMeasure,
                                                        double affinity,
                                                        double brandFactor
                                                        ){
        List<RiskMeasure> riskMeasures = new ArrayList<>();

        if (affinity <= 0)
            return riskMeasures;

        //reroute to supervisor
        switch (riskMeasure.getAgentBreed()) {
            case BREED_C: {
                if (affinity < (riskMeasure.getSocialGrade() * riskMeasure.getAttributeBrand())) {
                    //Switch Breed to Breed_NC

                    riskMeasure.setAgentBreed(BREED_NC);
                    riskMeasure.setAge(riskMeasure.getAge());
                    riskMeasures.add(riskMeasure);
                    return riskMeasures;

                } else {
                    // Gained
                    // + Switch Breed to Breed_NC
                    // + Switch back to BREED_C
                    riskMeasure.setAgentBreed(BREED_NC);
                    riskMeasure.setAge(riskMeasure.getAge());
                    riskMeasures.add(riskMeasure);

                    riskMeasure.setAgentBreed(BREED_C);
                    riskMeasure.setAge(riskMeasure.getAge());
                    riskMeasures.add(riskMeasure);
                    return riskMeasures;
                }

            }

            case BREED_NC: {
                if (affinity < (riskMeasure.getSocialGrade() * riskMeasure.getAttributeBrand() * brandFactor)) {
                    //Switch Breed to Breed_C
                    riskMeasure.setAgentBreed(BREED_C);
                    riskMeasure.setAge(riskMeasure.getAge());
                    riskMeasures.add(riskMeasure);
                    return riskMeasures;
                } else {
                    // Gained
                    // + Switch Breed to Breed_C
                    // + Switch back to BREED_NC
                    riskMeasure.setAgentBreed(BREED_C);
                    riskMeasure.setAge(riskMeasure.getAge());
                    riskMeasures.add(riskMeasure);

                    riskMeasure.setAgentBreed(BREED_NC);
                    riskMeasure.setAge(riskMeasure.getAge());
                    riskMeasures.add(riskMeasure);
                    return riskMeasures;
                }

            }
        }
        return riskMeasures;
    }

}
