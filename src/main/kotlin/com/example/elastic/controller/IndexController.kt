package com.example.elastic.controller

import com.example.elastic.model.ElasticDocument
import com.example.elastic.repository.DocumentRepository
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/index", produces = [MediaType.APPLICATION_JSON_VALUE])
class IndexController(
    private val documentRepository: DocumentRepository
) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun index(@RequestBody document: ElasticDocument): ResponseEntity<ElasticDocument> {
        val saved = documentRepository.save(document)
        return ResponseEntity.status(HttpStatus.CREATED).body(saved)
    }

    @PostMapping("/bulk", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun bulkIndex(@RequestBody documents: List<ElasticDocument>): ResponseEntity<List<ElasticDocument>> {
        val saved = documentRepository.saveAll(documents).toList()
        return ResponseEntity.status(HttpStatus.CREATED).body(saved)
    }

    @PutMapping("/{id}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun update(
        @PathVariable id: String,
        @RequestBody document: ElasticDocument
    ): ResponseEntity<ElasticDocument> {
        val updated = documentRepository.save(document.copy(id = id))
        return ResponseEntity.ok(updated)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        if (!documentRepository.existsById(id)) {
            return ResponseEntity.notFound().build()
        }
        documentRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}
