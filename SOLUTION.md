# Markdown Previewer – System Design Solution

## 1. Visão Geral

O objetivo do sistema é permitir que usuários criem, compartilhem e visualizem documentos Markdown com alta performance, expiração automática em 24 horas e suporte a milhões de documentos simultâneos.

O sistema foi projetado priorizando:

- Baixa latência (P95 < 100ms)
- Escalabilidade horizontal
- Simplicidade arquitetural
- Segurança contra abuso
- Expiração automática sem processamento manual

---

## 2. Arquitetura Geral

### 🗺️ Diagrama de Arquitetura
**[🔗 Clique aqui para visualizar o diagrama interativo no Excalidraw](https://excalidraw.com/#json=ftIBZUaEzM_8BGO23fxyV,4MizWrsg8M1DQB_hStvX5w)**

### Componentes Principais

- Frontend (HTML + CSS + JavaScript)
- Backend (Spring Boot – REST API)
- Banco de Dados (MongoDB com TTL Index)
- Cache (Redis – estratégia Read-Aside)
- CDN (opcional – Cloudflare)
- Load Balancer

### Arquitetura de Alto Nível

Usuário → CDN → Load Balancer → Backend (Spring Boot) → Redis → MongoDB

A aplicação é **stateless**, permitindo escalabilidade horizontal.

---

## 3. Escolhas Tecnológicas

### Frontend

- HTML, CSS e JavaScript puro
- marked.js (via CDN) para conversão Markdown → HTML
- DOMPurify para sanitização contra XSS

**Justificativa:**
Interface simples não exige framework pesado. Reduz tempo de carregamento e complexidade.

---

### Backend

- Java + Spring Boot (Spring MVC)

**Justificativa:**
- Robustez e maturidade no mercado
- Fácil escalabilidade horizontal
- Ecossistema rico (Actuator, validações, segurança)

**Trade-off:**
Spring adiciona overhead comparado a frameworks minimalistas, porém oferece maior organização e extensibilidade.

---

### Banco de Dados

- MongoDB

**Justificativa:**
- Modelo orientado a documentos (ideal para Markdown)
- TTL Index nativo
- Escalabilidade horizontal via sharding

**Trade-off:**
Não oferece relacionamentos complexos como bancos SQL, porém o sistema não necessita deles.

---

### Cache

- Redis

**Justificativa:**
- Latência extremamente baixa (1–5ms)
- TTL nativo
- Redução de carga no banco principal

**Estratégia:** Read-Aside Cache

Fluxo:
1. Consulta Redis
2. Cache miss → consulta MongoDB
3. Reidrata Redis

**Trade-off:**
Aumenta complexidade e custo operacional.

---

### CDN

- Cloudflare (opcional)

**Justificativa:**
Como os documentos são imutáveis após criação, podem ser armazenados na borda para reduzir latência global.

---

## 4. Fluxo de Funcionamento

### 4.1 Criação de Documento

1. Usuário envia Markdown via POST
2. Backend:
   - Gera ID único (NanoID)
   - Define expiresAt = now + 24h
   - Salva no MongoDB
   - Salva no Redis com TTL de 24h
3. Retorna URL única

---

### 4.2 Visualização de Documento

1. Usuário acessa link
2. CDN tenta resolver
3. Caso não esteja em cache:
   - Backend consulta Redis
   - Caso cache miss → consulta MongoDB
   - Redis é reidratado
4. Retorna Markdown
5. Frontend converte e sanitiza antes de renderizar

---

## 5. Geração de IDs

Foi escolhido o algoritmo **NanoID**.

Características:
- Não sequencial
- URL-friendly
- Baixa probabilidade de colisão

**Justificativa:**
Evita enumeração de documentos e melhora segurança.

**Trade-off:**
IDs não possuem ordenação temporal natural.

---

## 6. Expiração de 24 Horas

A expiração foi delegada às camadas de dados.

### MongoDB

- TTL Index no campo `expiresAt`
- Thread interna do Mongo remove documentos automaticamente

### Redis

- TTL configurado na chave
- Expiração automática na memória

**Vantagem:**
Não é necessário Cron Job na aplicação.

**Resultado:**
Impacto zero na performance do backend.

---

## 7. Estratégia de Escalabilidade

### Aplicação

- Stateless
- Escalável horizontalmente
- Pode rodar múltiplas instâncias atrás de Load Balancer

### Banco de Dados

- MongoDB Replica Set
- Possível Sharding para milhões de documentos

### Cache

- Redis Cluster
- Política de eviction LRU

---

## 8. Performance

Meta: P95 < 100ms

Estimativa:

- CDN: 10–30ms
- Redis: 1–5ms
- MongoDB: 5–20ms

Como a maioria das leituras ocorre no cache, a meta é alcançável.

---

## 9. Segurança

- Rate Limiting por IP (API Gateway)
- Limite de tamanho do documento (ex: 100KB)
- Validação de input no backend
- Sanitização no frontend com DOMPurify
- IDs não sequenciais
- Headers de segurança HTTP

---

## 10. Disponibilidade

Meta: 99.9%

Estratégias:

- Múltiplas instâncias do backend
- MongoDB com replicação
- Redis clusterizado
- Monitoramento com Spring Actuator

---

## 11. Monitoramento e Observabilidade

- Spring Boot Actuator
- Métricas de latência (P95)
- Health checks
- Logs estruturados
- Alertas de uso excessivo

---

## 12. Trade-offs Considerados

| Decisão | Benefício | Custo |
|---------|----------|--------|
| MongoDB | TTL nativo | Sem joins |
| Redis | Latência baixa | Infra extra |
| CDN | Escala global | Configuração adicional |
| NanoID | Segurança | Sem ordenação |

---

## 13. Conclusão

A solução proposta atende aos requisitos funcionais e não-funcionais com foco em:

- Alta performance
- Escalabilidade horizontal
- Simplicidade
- Baixo acoplamento
- Segurança

A arquitetura evita overengineering, utilizando recursos nativos das tecnologias escolhidas para garantir eficiência e manutenibilidade.
