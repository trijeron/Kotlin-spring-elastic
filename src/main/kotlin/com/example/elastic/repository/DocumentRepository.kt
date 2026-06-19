package com.example.elastic.repository

import com.example.elastic.model.ElasticDocument
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository

@Repository
interface DocumentRepository : ElasticsearchRepository<ElasticDocument, String> {

    fun findByTitle(title: String): List<ElasticDocument>

    fun findByCategory(category: String): List<ElasticDocument>

    fun findByTitleContaining(keyword: String): List<ElasticDocument>
}
