package com.example.elastic.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration

@Configuration
class ElasticsearchConfig : ElasticsearchConfiguration() {

    @Value("\${spring.elasticsearch.uris:localhost:9200}")
    private lateinit var elasticsearchUri: String

    @Value("\${spring.elasticsearch.username:}")
    private lateinit var username: String

    @Value("\${spring.elasticsearch.password:}")
    private lateinit var password: String

    override fun clientConfiguration(): ClientConfiguration {
        val builder = ClientConfiguration.builder()
            .connectedTo(elasticsearchUri.removePrefix("http://").removePrefix("https://"))

        if (username.isNotBlank() && password.isNotBlank()) {
            builder.withBasicAuth(username, password)
        }

        return builder.build()
    }
}
