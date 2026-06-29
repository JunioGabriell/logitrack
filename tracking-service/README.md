# 📍 tracking-service

Microsserviço responsável pelo rastreamento de pedidos utilizando serviços AWS simulados localmente com LocalStack. Persiste eventos de rastreamento no DynamoDB e publica notificações no SQS.

---

## 🛠️ Tecnologias

- Java 21
- Spring Boot 3.5.16
- Spring Security + JWT
- AWS SDK v2 (SQS + DynamoDB)
- LocalStack — simulação local da AWS
- Swagger/OpenAPI
- Spring Boot Actuator
- Docker

---

## ☁️ Serviços AWS (LocalStack)

| Serviço | Uso |
|---|---|
| **DynamoDB** | Persiste o histórico de rastreamento |
| **SQS** | Publica notificações de atualização |

---

## 🚀 Endpoints

| Método | Endpoint | Descrição | Role |
|---|---|---|---|
| GET | `/tracking/{pedidoId}` | Busca histórico de rastreamento | ADMIN, CLIENT, DRIVER |
| POST | `/tracking/{pedidoId}/update` | Atualiza rastreamento | ADMIN, DRIVER |

### Exemplo de atualização de rastreamento

```json
POST /tracking/1/update
Authorization: Bearer {token}

{
  "pedidoId": "1",
  "status": "EM_TRANSITO",
  "localizacao": "Av. Paulista, São Paulo"
}
```

### Exemplo de resposta

```json
{
  "id": "uuid-gerado",
  "pedidoId": "1",
  "status": "EM_TRANSITO",
  "localizacao": "Av. Paulista, São Paulo",
  "atualizadoPor": "driver@email.com",
  "atualizadoEm": "2024-01-15T14:30:00"
}
```

---

## 📊 Monitoramento

| Endpoint | Descrição |
|---|---|
| `/actuator/health` | Status da aplicação |
| `/actuator/metrics` | Métricas da aplicação |

---

## 📚 Documentação

```
http://localhost:8084/swagger-ui/index.html
```

---

## ▶️ Como executar

Requer o LocalStack rodando (via docker-compose):

```bash
docker-compose up localstack
```

Depois:

```bash
./mvnw clean package -DskipTests
docker build -t tracking-service .
docker run -p 8084:8084 tracking-service
```

---

## 🗄️ Banco de dados

- **DynamoDB (LocalStack)** — porta `4566`
- **Tabela** — `tracking`
- **SQS Queue** — `tracking-queue`