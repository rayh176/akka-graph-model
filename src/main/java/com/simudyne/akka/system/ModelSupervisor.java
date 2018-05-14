package com.simudyne.akka.system;

import akka.actor.ActorRef;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.ActorRefRoutee;
import akka.routing.Routee;
import akka.routing.Router;
import akka.routing.SmallestMailboxRoutingLogic;
import com.simudyne.beans.RequestModelTask;
import com.simudyne.beans.ResponseModelTask;
import com.simudyne.ext.SpringExtension;
import com.simudyne.rest.ResultCollector;
import com.simudyne.rest.RiskMeasure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.simudyne.beans.RequestModelTaskRouterHelper.buildRoutedRequest;

/**
 * A sample supervisor to handle requests
 * for the actual {@link  RequestModelTask}
 * <p/>
 * A router is configured at startup time, managing a pool of task actors.
 */
@Component
@Scope("prototype")
public class ModelSupervisor extends UntypedActor {
    public static final int NO_OF_SIMULATED_YEARS = 15;
    private static final int NO_OF_ACTORS = 15;
    private final LoggingAdapter LOGGER = Logging
            .getLogger(getContext().system(), "Supervisor");

    @Autowired
    private SpringExtension springExtension;

    private Router router;

    @Override
    public void preStart() throws Exception {

        LOGGER.info("*** Supervisor Starting up ***");

        List<Routee> routees = new ArrayList<Routee>();
        for (int i = 0; i < NO_OF_ACTORS; i++) {
            ActorRef actor = getContext().actorOf(springExtension.props(
                    "modelTaskActor"));
            getContext().watch(actor);
            routees.add(new ActorRefRoutee(actor));
        }
        router = new Router(new SmallestMailboxRoutingLogic(), routees);
        super.preStart();
    }

    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof RequestModelTask) {
            router.route(message, getSender());
        } else if ( message instanceof ResponseModelTask){
            ResponseModelTask responseModelTask = (ResponseModelTask) message;
            RequestModelTask requestModelTask = responseModelTask.getRequestModelTask();
            RiskMeasure tmpRiskMeasure = requestModelTask.getRiskMeasure().deepClone(requestModelTask.getRiskMeasure().getAge());
            ResultCollector resultCollector = requestModelTask.getResultCollector();
            double affinity = responseModelTask.getAffinity();
            double brandFactor = requestModelTask.getBrandFactor();
            List<RiskMeasure>  riskMeasures = buildRoutedRequest(tmpRiskMeasure,
                                                                  affinity,
                                                                  brandFactor);
            for (RiskMeasure riskMeasure : riskMeasures){
                RequestModelTask modifiedRequestModelTask = new RequestModelTask(riskMeasure, brandFactor, resultCollector);
                router.route(modifiedRequestModelTask, getSender());
            }
        }
        else if (message instanceof Terminated) {
            // Readd task actors if one failed
            router = router.removeRoutee(((Terminated) message).actor());
            ActorRef actor = getContext().actorOf(springExtension.props
                    ("modelTaskActor"));
            getContext().watch(actor);
            router = router.addRoutee(new ActorRefRoutee(actor));
        } else {
            LOGGER.error("Unable to handle message {}", message);
        }
    }

    @Override
    public void postStop() throws Exception {
        LOGGER.warning("Shutting down");
        super.postStop();
    }
}