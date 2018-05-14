package com.simudyne;

import akka.actor.ActorSystem;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PreDestroy;

@SpringBootApplication
@ComponentScan("com.simudyne.config")
public class ApplicationRunner {

    private static ActorSystem system;

    public static final void main(String[] args) throws InterruptedException {

        ApplicationContext context = SpringApplication.run(ApplicationRunner.class);
        EhCacheManager localCacheManager = context.getBean(EhCacheManager.class);
        localCacheManager.getCache("AGENT_BASED_MODEL");
        system = context.getBean(ActorSystem.class);
        final LoggingAdapter log = Logging.getLogger(system, "Application");
        log.info("*** System Starting up ****");
    }

    @PreDestroy
    public void onDestroy() throws Exception {
        System.out.println("Akka Shutting down");
        system.terminate();
        System.out.println("Spring Container is destroyed!");
    }

}
