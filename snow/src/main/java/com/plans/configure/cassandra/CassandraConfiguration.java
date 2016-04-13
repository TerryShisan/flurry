package com.plans.configure.cassandra;

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

    @Bean
    @Override
    public CassandraClusterFactoryBean cluster() {
        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints("10.160.5.56");
        cluster.setPort(9042);
        return cluster;
    }

    @Override
    protected String getKeyspaceName() {
        return "mykeyspace";
    }

    @Bean
    @Override
    public CassandraMappingContext cassandraMapping() throws  ClassNotFoundException {
        return new BasicCassandraMappingContext();
    }

}
