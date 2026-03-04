package com.desafio.markdown_api.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "documents")
public class MarkdownDocument {
    @Id
    private String id;
    private String content;
    
    @Indexed(expireAfter = "0s")
    private Instant expiresAt;

    public MarkdownDocument() {}
    public MarkdownDocument(String id, String content, Instant expiresAt) {
        this.id = id;
        this.content = content;
        this.expiresAt = expiresAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }
}