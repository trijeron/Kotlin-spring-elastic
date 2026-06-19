package com.example.elastic.controller

import com.example.elastic.model.ElasticDocument
import com.example.elastic.repository.DocumentRepository
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.query.Criteria
import org.springframework.data.elasticsearch.core.query.CriteriaQuery
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/search", produces = [MediaType.APPLICATION_JSON_VALUE])
class SearchController(
    private val documentRepository: DocumentRepository,
    private val elasticsearchOperations: ElasticsearchOperations
) {

    @GetMapping
    fun findAll(): ResponseEntity<List<ElasticDocument>> {
        val documents = documentRepository.findAll().toList()
        return ResponseEntity.ok(documents)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<ElasticDocument> {
        val document = documentRepository.findById(id)
        return if (document.isPresent) {
            ResponseEntity.ok(document.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/title/{title}")
    fun findByTitle(@PathVariable title: String): ResponseEntity<List<ElasticDocument>> {
        val documents = documentRepository.findByTitle(title)
        return ResponseEntity.ok(documents)
    }

    @GetMapping("/category/{category}")
    fun findByCategory(@PathVariable category: String): ResponseEntity<List<ElasticDocument>> {
        val documents = documentRepository.findByCategory(category)
        return ResponseEntity.ok(documents)
    }

    @GetMapping("/search")
    fun search(@RequestParam keyword: String): ResponseEntity<List<ElasticDocument>> {
        val criteria = Criteria("title").contains(keyword)
            .or(Criteria("content").contains(keyword))
        val query = CriteriaQuery(criteria)
        val hits = elasticsearchOperations.search(query, ElasticDocument::class.java)
        val documents = hits.searchHits.map { it.content }
        return ResponseEntity.ok(documents)
    }
}
