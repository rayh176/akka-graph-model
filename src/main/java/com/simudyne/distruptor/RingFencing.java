package com.simudyne.distruptor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.simudyne.beans.RequestModelTask;
import com.simudyne.ext.SpringExtension;
import com.simudyne.repository.RiskMeasureCache;
import com.simudyne.rest.ResultCollector;
import com.simudyne.rest.RiskMeasure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class RingFencing {

    public static final int NO_OF_SIMULATED_YEARS = 15;

    @Autowired
    ActorSystem actorSystem;

    @Autowired
    SpringExtension ext;

    private ActorRef modelSupervisor;


    public void handleEvent(RiskMeasure riskMeasure,
                            double brandFactor,
                            ResultCollector resultCollector) {
        int age = riskMeasure.getAge();
        for (int i=0; i< NO_OF_SIMULATED_YEARS; i++){
            RequestModelTask requestModelTask = new RequestModelTask(riskMeasure.deepClone(age+ i), brandFactor, resultCollector);
            System.out.println("Sequence: " + requestModelTask);
            modelSupervisor.tell(requestModelTask, null);
        }
    }

    @PreDestroy
    public void onDestroy() throws Exception{
        // Poison pill will be queued with a priority of 100 as the last
        // message
        System.out.println("PostConstruct ModelSupervisor:::PoisonPill:::"+modelSupervisor);
        modelSupervisor.tell(PoisonPill.getInstance(), null);
    }

    @PostConstruct
    public void post(){

        // Use the Spring Extension to create props for a named actor bean
        modelSupervisor = actorSystem.actorOf(
                ext.props("modelSupervisor").withMailbox("akka.priority-mailbox"));

        System.out.println("PostConstruct ModelSupervisor:::"+modelSupervisor);
    }

}
