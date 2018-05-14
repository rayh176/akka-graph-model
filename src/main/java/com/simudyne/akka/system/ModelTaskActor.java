package com.simudyne.akka.system;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.simudyne.beans.RequestModelTask;
import com.simudyne.model.ModelValuator;
import com.simudyne.repository.RiskMeasureCache;
import com.simudyne.rest.ResultCollector;
import com.simudyne.rest.RiskMeasure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ModelTaskActor extends UntypedActor {

    private final LoggingAdapter LOGGER = Logging
            .getLogger(getContext().system(), "ModelTaskActor");
    @Autowired
    private ModelValuator modelValuator;

    @Autowired
    RiskMeasureCache riskMeasureCache;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof RequestModelTask) {
            RiskMeasure riskMeasure = ((RequestModelTask)message).getRiskMeasure();

            ResultCollector resultCollector = ((RequestModelTask)message).getResultCollector();

            double affinity = modelValuator.processRiskCalculation((RequestModelTask) message);

            resultCollector.buildWithRiskResultsPerYear(riskMeasure.getAge(), affinity);

            riskMeasureCache.addTokenToCache(resultCollector.getCombinedKey(),resultCollector);

            //TODO This task is turned off as it will run the model for long time
            //getContext().parent().tell(new ResponseModelTask(((RequestModelTask)message), affinity), self());
        }
        else {
            unhandled(message);
        }
    }
}
