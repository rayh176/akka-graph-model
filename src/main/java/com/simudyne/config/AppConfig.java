package com.simudyne.config;

import akka.actor.ActorSystem;
import akka.actor.TypedActor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simudyne.ext.SpringExtension;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.apache.shiro.cache.ehcache.EhCacheManager;

@Configuration
@EnableAutoConfiguration /** Read provided pom dependencies **/
@ComponentScan(basePackages = { "com.simudyne" })
@Lazy
public class AppConfig {

    @Autowired
    private SpringExtension springExtension;

    // The application context is needed to initialize the Akka Spring
    // Extension
    @Autowired
    private ApplicationContext applicationContext;


    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false));
        return converter;
    }

    @Bean(name="localCacheManager")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public EhCacheManager getEhCacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:ehcache.xml");
        return em;
    }

    /**
     * Actor system singleton for this application.
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ActorSystem actorSystem() {

        final ActorSystem system = ActorSystem.create("AkkaModelProcessing", akkaConfiguration());

        // Initialize the application context in the Akka Spring Extension
        springExtension.initialize(applicationContext);
        return system;
    }

    /**
     * Read configuration from application.conf file
     */
    @Bean
    public Config akkaConfiguration() {
        return ConfigFactory.load();
    }

}
