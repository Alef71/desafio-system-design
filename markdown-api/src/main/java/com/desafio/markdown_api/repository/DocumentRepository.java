package com.desafio.markdown_api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.desafio.markdown_api.model.MarkdownDocument;

@Repository
public interface DocumentRepository extends MongoRepository<MarkdownDocument, String> {
}