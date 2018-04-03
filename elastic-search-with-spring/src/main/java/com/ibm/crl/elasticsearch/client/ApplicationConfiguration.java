/*
 * Copyright 2014-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ibm.crl.elasticsearch.client;

import java.util.Arrays;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.NodeClientFactoryBean;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author Artur Konczak
 * @author Oliver Gierke
 * @author Christoph Strobl
 */
@Configuration
@EnableElasticsearchRepositories
public
class ApplicationConfiguration {

	@Autowired ElasticsearchOperations operations;
	@Autowired ConferenceRepository repository;

	@Bean
	public NodeClientFactoryBean client() {

		NodeClientFactoryBean bean = new NodeClientFactoryBean(true);
		bean.setClusterName(UUID.randomUUID().toString());
		bean.setEnableHttp(false);
		bean.setPathData("target/elasticsearchTestData");
		bean.setPathHome("src/test/resources/test-home-dir");

		return bean;
	}

	@Bean
	public ElasticsearchTemplate elasticsearchTemplate(Client client) throws Exception {
		return new ElasticsearchTemplate(client);
	}

	@PreDestroy
	public void deleteIndex() {
		operations.deleteIndex(Conference.class);
	}

	@PostConstruct
	public void insertDataSample() {

		// Remove all documents
		repository.deleteAll();
		operations.refresh(Conference.class);
		
		// Save data sample
		repository.save(new Conference("1","Spring eXchange 2014 - London","2014-11-06",new GeoPoint(51.500152D, -0.126236D),Arrays.asList("java", "spring")));
		
		repository.save(new Conference("1","Scala eXchange 2014 - London","2014-12-07",new GeoPoint(51.500152D, -0.126236D),Arrays.asList("scala", "play", "java")));
		repository.save(new Conference("1","Elasticsearch 2014 - Berlin","2014-11-20",new GeoPoint(52.5234051D, 13.4113999),Arrays.asList("java", "elasticsearch", "kibana")));
		repository.save(new Conference("1","AWS London 2014","2014-11-12",new GeoPoint(51.500152D, -0.126236D),Arrays.asList("cloud", "aws")));
		repository.save(new Conference("1","JDD14 - Cracow","2014-10-04",new GeoPoint(50.0646501D, 19.9449799),Arrays.asList("java", "spring")));
	}
}
