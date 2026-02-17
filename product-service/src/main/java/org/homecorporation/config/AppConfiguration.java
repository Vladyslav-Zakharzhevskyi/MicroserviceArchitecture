package org.homecorporation.config;

import feign.micrometer.MicrometerObservationCapability;
import io.micrometer.observation.ObservationRegistry;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public MicrometerObservationCapability micrometerObservationCapability(ObservationRegistry registry) {
        return new MicrometerObservationCapability(registry);
    }

//    @Bean
//    public Connector connector() {
//        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
//    }

}
