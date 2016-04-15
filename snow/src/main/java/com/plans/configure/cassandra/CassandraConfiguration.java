package com.plans.configure.cassandra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;

/**
 * Created by river on 2016/3/29.
 */

@Configuration
public class CassandraConfiguration extends AbstractCassandraConfiguration {
    public static final String KEY_SPACE = "mykeyspace";
    @Value("${cassandra.address}")
    private String cassandraAddress;

    @Value("${cassandra.port}")
    private Integer cassandraPort;

    @Bean
    @Override
    public CassandraClusterFactoryBean cluster() {
        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints(cassandraAddress);
        cluster.setPort(cassandraPort);
        return cluster;
    }

    @Override
    protected String getKeyspaceName() {
        return KEY_SPACE;
    }

    @Bean
    @Override
    public CassandraMappingContext cassandraMapping() throws ClassNotFoundException {
        return new BasicCassandraMappingContext();
    }
}
