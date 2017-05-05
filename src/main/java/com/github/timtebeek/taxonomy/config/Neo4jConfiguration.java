package com.github.timtebeek.taxonomy.config;

import com.github.timtebeek.taxonomy.model.Taxon;
import org.neo4j.ogm.config.DriverConfiguration;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;

@Configuration
public class Neo4jConfiguration {
	@Bean
	public org.neo4j.ogm.config.Configuration getConfiguration() {
		org.neo4j.ogm.config.Configuration config = new org.neo4j.ogm.config.Configuration();
		DriverConfiguration driverConfiguration = config.driverConfiguration();
		driverConfiguration.setURI("bolt://localhost");
		return config;
	}

	@Bean
	public SessionFactory sessionFactory() {
		// with domain entity base package(s)
		return new SessionFactory(getConfiguration(), Taxon.class);
	}

	@Bean
	public Neo4jTransactionManager transactionManager() {
		return new Neo4jTransactionManager(sessionFactory());
	}
}
