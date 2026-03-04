package com.desafio.markdown_api.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.desafio.markdown_api.model.MarkdownDocument;
import com.desafio.markdown_api.repository.DocumentRepository;

@Service
public class DocumentService {
    private final DocumentRepository repository;
    private final StringRedisTemplate redisTemplate;

    public DocumentService(DocumentRepository repository, StringRedisTemplate redisTemplate) {
        this.repository = repository;
        this.redisTemplate = redisTemplate;
    }

    public String createDocument(String content) {
        String id = NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, NanoIdUtils.DEFAULT_ALPHABET, 10);
        Instant expiresAt = Instant.now().plus(24, ChronoUnit.HOURS);
        
        MarkdownDocument doc = new MarkdownDocument(id, content, expiresAt);
        repository.save(doc);
        
        redisTemplate.opsForValue().set("doc:" + id, content, 24, TimeUnit.HOURS);
        return id;
    }

    public Optional<String> getDocument(String id) {
        String cachedContent = redisTemplate.opsForValue().get("doc:" + id);
        if (cachedContent != null) return Optional.of(cachedContent);

        return repository.findById(id).map(doc -> {
            redisTemplate.opsForValue().set("doc:" + id, doc.getContent(), 24, TimeUnit.HOURS);
            return doc.getContent();
        });
    }
}