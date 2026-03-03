# Desafio System Design 01

## 🎯 Objetivo

Este desafio tem como objetivo avaliar suas habilidades em **System Design**, capacidade de criar arquiteturas escaláveis, eficientes e bem fundamentadas.

## 📚 O que é System Design?

**System Design** é o processo de definir a arquitetura, componentes, módulos, interfaces e dados de um sistema para satisfazer requisitos específicos. Envolve decisões sobre:

- **Arquitetura de infraestrutura**: Como os componentes se comunicam
- **Escalabilidade**: Como o sistema cresce com a demanda
- **Confiabilidade**: Garantir disponibilidade e tolerância a falhas
- **Performance**: Otimizar latência e throughput
- **Segurança**: Proteger dados e acessos
- **Trade-offs**: Balancear custo, complexidade e benefícios

## 💡 Por que é importante?

System Design é uma habilidade essencial para engenheiros de software porque:

1. **Prepara para escala**: Sistemas bem projetados suportam crescimento sem reescrita completa
2. **Reduz custos**: Decisões arquiteturais corretas economizam recursos de infraestrutura
3. **Melhora a experiência do usuário**: Performance e confiabilidade impactam diretamente os usuários
4. **Facilita manutenção**: Arquiteturas bem documentadas são mais fáceis de evoluir
5. **Desenvolve pensamento crítico**: Ensina a avaliar trade-offs e tomar decisões fundamentadas

---

## 🚀 Desafio: Markdown Previewer

### Descrição do Problema

Desenvolver um sistema de **preview de markdown** que permita aos usuários criar, compartilhar e visualizar conteúdo markdown de forma rápida e eficiente.

### Requisitos Funcionais

- ✅ **Criação**: O usuário pode criar um texto no formato Markdown e gerar um link compartilhável
- ✅ **Compartilhamento**: Qualquer pessoa com o link pode visualizar o preview renderizado
- ✅ **Visualização instantânea**: O acesso deve ser instantâneo com latência mínima (< 100ms ideal)
- ✅ **Links únicos**: Os IDs gerados não podem conflitar (ex: `mydomain.com/preview?id=123` ou `mydomain.com/preview#1z2l3A`)
- ✅ **Expiração**: O markdown deve ficar disponível por **no máximo 24 horas** após a criação

### Requisitos Não-Funcionais

- **Performance**: Latência de leitura < 100ms (P95)
- **Escalabilidade**: Suportar milhões de documentos simultâneos
- **Disponibilidade**: 99.9% de uptime
- **Consistência**: Documentos devem ser imediatamente acessíveis após criação
- **Segurança**: Proteção contra abuso (rate limiting, validação de input)

### Requisitos Técnicos

- IDs devem ser únicos e não sequenciais (para evitar enumeração)
- Sistema deve limpar automaticamente documentos expirados
- Renderização de Markdown deve ser segura (sanitização de XSS)
- Considerar otimizações de cache para conteúdo frequentemente acessado

---

## 📦 O que você deve entregar?

### 1. Diagrama de Arquitetura (Obrigatório)

Crie um diagrama detalhado da sua arquitetura incluindo:

- **Componentes principais**: Frontend, Backend, Database, Cache, etc.
- **Fluxo de dados**: Como as informações trafegam pelo sistema
- **Tecnologias**: Especifique as ferramentas escolhidas (ex: Redis, PostgreSQL, S3)
- **Escalabilidade**: Como o sistema escala horizontalmente

**Ferramentas sugeridas:**
- [Excalidraw](https://excalidraw.com/) (recomendado)
- [draw.io](https://draw.io)
- [Lucidchart](https://www.lucidchart.com)

**Formato de entrega:** Link público ou imagem (PNG/JPG) no repositório

### 2. Documento de Decisões Arquiteturais (Obrigatório)

Crie um documento explicando suas escolhas:

- **Por que escolheu cada tecnologia?**
- **Quais trade-offs você considerou?**
- **Como o sistema lida com os requisitos de escala?**
- **Como implementou a expiração de 24 horas?**
- **Como garante IDs únicos sem conflitos?**
- **Estratégias de cache e otimização**

### 3. Implementação (Opcional, mas valorizado)

Se desejar implementar o sistema:

- **Backend**: API RESTful ou GraphQL
- **Frontend**: Interface simples para criar e visualizar markdown
- **Deploy**: Link da aplicação funcionando (Vercel, Heroku, Railway, etc.)

**Stack sugerida (use o que preferir):**
- Backend: Node.js, Python (FastAPI/Flask), Go, Java (Spring Boot)
- Frontend: React, Vue, Svelte, ou HTML puro
- Database: PostgreSQL, MongoDB, DynamoDB
- Cache: Redis, Memcached
- Storage: S3, CloudFlare R2

---

## 📋 Critérios de Avaliação

| Critério | Peso | Descrição |
|----------|------|-----------|
| **Arquitetura** | 40% | Qualidade do design, escalabilidade, resiliência |
| **Justificativa técnica** | 30% | Clareza nas decisões e análise de trade-offs |
| **Viabilidade** | 20% | A solução é realista e implementável? |
| **Implementação** | 10% | Bônus se houver código funcional |

---

## 🎓 Dicas e Considerações

### Perguntas que você deve responder:

1. **Armazenamento**: Onde guardar os documentos? (SQL, NoSQL, Object Storage)
2. **Geração de IDs**: UUID, Nanoid, Snowflake, Hash?
3. **Expiração**: Cron job, TTL nativo do banco, fila de mensagens?
4. **Cache**: Vale a pena? Onde aplicar?
5. **CDN**: Faz sentido para este caso?
6. **Rate Limiting**: Como evitar abuso?
7. **Monitoramento**: Como saber se o sistema está saudável?

### Trade-offs comuns:

- **Consistência vs Disponibilidade**: CAP Theorem
- **Custo vs Performance**: Infraestrutura cara vs otimização de código
- **Simplicidade vs Escalabilidade**: Over-engineering vs preparação para crescimento
- **SQL vs NoSQL**: Estrutura rígida vs flexibilidade

### Recursos úteis:

- [System Design Primer](https://github.com/donnemartin/system-design-primer)
- [ByteByteGo](https://bytebytego.com/)
- [High Scalability Blog](http://highscalability.com/)
- [AWS Architecture Center](https://aws.amazon.com/architecture/)
- [Design a URL Shortener Like Bit.ly](https://www.hellointerview.com/learn/system-design/problem-breakdowns/bitly)

---

## 📤 Como Submeter

1. Faça um fork deste repositório
2. Adicione seu diagrama de arquitetura (imagem ou link)
3. Crie um arquivo `SOLUTION.md` com suas decisões arquiteturais
4. (Opcional) Adicione o código da implementação
5. Abra um Pull Request com suas alterações

**Estrutura sugerida:**
```
/
├── README.md (este arquivo)
├── SOLUTION.md (suas respostas e decisões)
├── architecture-diagram.png (ou link no SOLUTION.md)
└── /src (opcional - código da implementação)
    ├── /backend
    └── /frontend
```

---

## ⏰ Prazo

Não há prazo rígido. Trabalhe no seu ritmo, mas considere que uma solução completa (diagrama + documentação) pode ser feita em **4-8 horas**.

---

## 🤝 Dúvidas?

Abra uma issue neste repositório ou entre em contato com o time de avaliação.

**Boa sorte! 🚀**

## Exemplo
[Markdown Preview](https://dbrno.vercel.app/md)
