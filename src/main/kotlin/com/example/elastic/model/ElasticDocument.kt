package com.example.elastic.model

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = "documents")
data class ElasticDocument(
    @Id
    val id: String? = null,

    @Field(type = FieldType.Text)
    val title: String,

    @Field(type = FieldType.Text)
    val content: String,

    @Field(type = FieldType.Keyword)
    val category: String? = null,

    @Field(type = FieldType.Long)
    val timestamp: Long = System.currentTimeMillis()
)
