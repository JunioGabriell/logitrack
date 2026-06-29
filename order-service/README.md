# 📦 order-service

Microsserviço responsável pela criação e gerenciamento de pedidos. Ao criar um pedido, publica um evento no Apache Kafka para que o delivery-service processe a entrega.

---

## 🛠️ Tecnologias

- Java 21
- Spring Boot 3.5.16
- Spring Security + JWT
- Spring Data JPA
- MySQL
- Apache Kafka (Producer)
- Swagger/OpenAPI
- Spring Boot Actuator
- Docker

---

## 🚀 Endpoints

| Método | Endpoint | Descrição | Role |
|---|---|---|---|
| POST | `/orders` | Cria novo pedido | CLIENT, ADMIN |
| GET | `/orders` | Lista pedidos | ADMIN vê todos, CLIENT vê os seus |
| GET | `/orders/{id}` | Busca pedido por ID | ADMIN, CLIENT dono |

### Exemplo de criação de pedido

```json
POST /orders
Authorization: Bearer {token}

{
  "descricao": "Notebook Dell",
  "endereco": "Rua das Flores, 123"
}
```

### Resposta

```json
{
  "id": 1,
  "clienteEmail": "joao@email.com",
  "descricao": "Notebook Dell",
  "endereco": "Rua das Flores, 123",
  "status": "AGUARDANDO",
  "criadoEm": "2024-01-15T10:30:00"
}
```

---

## 📨 Kafka

- **Tópico publicado:** `pedidos`
- **Evento publicado:** objeto `PedidoEvent` em JSON

---

## 📊 Monitoramento

| Endpoint | Descrição |
|---|---|
| `/actuator/health` | Status da aplicação |
| `/actuator/metrics` | Métricas da aplicação |

---

## 📚 Documentação

```
http://localhost:8082/swagger-ui/index.html
```

---

## ▶️ Como executar

```bash
./mvnw clean package -DskipTests
docker build -t order-service .
docker run -p 8082:8082 order-service
```

---

## 🗄️ Banco de dados

- **MySQL** — porta `3306`
- **Database** — `orderdb`