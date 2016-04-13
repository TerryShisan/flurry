package com.plans.configure.jersey;

import com.plans.rest.Journey;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * Created by river on 2016/4/2.
 */
@Component
public class JerseyConfig extends ResourceConfig{
    public JerseyConfig(){
        register(Journey.class);
    }
}
