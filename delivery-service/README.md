# 🚚 delivery-service

Microsserviço responsável pelo gerenciamento de entregas. Consome eventos de novos pedidos do Apache Kafka e persiste as entregas no MongoDB.

---

## 🛠️ Tecnologias

- Java 21
- Spring Boot 3.5.16
- Spring Security + JWT
- Spring Data MongoDB
- Apache Kafka (Consumer)
- Swagger/OpenAPI
- Spring Boot Actuator
- Docker

---

## 🚀 Endpoints

| Método | Endpoint | Descrição | Role |
|---|---|---|---|
| GET | `/deliveries` | Lista entregas | ADMIN vê todas, DRIVER vê as suas |
| GET | `/deliveries/{id}` | Busca entrega por ID | ADMIN, DRIVER atribuído |
| PUT | `/deliveries/{id}/status` | Atualiza status da entrega | ADMIN, DRIVER |

### Status disponíveis

```
AGUARDANDO → ATRIBUIDO → EM_TRANSITO → ENTREGUE
                                      → CANCELADO
```

### Exemplo de atualização de status

```json
PUT /deliveries/{id}/status
Authorization: Bearer {token}

{
  "status": "EM_TRANSITO"
}
```

---

## 📨 Kafka

- **Tópico consumido:** `pedidos`
- **Group ID:** `delivery-grupo`

---

## 📊 Monitoramento

| Endpoint | Descrição |
|---|---|
| `/actuator/health` | Status da aplicação |
| `/actuator/metrics` | Métricas da aplicação |

---

## 📚 Documentação

```
http://localhost:8083/swagger-ui/index.html
```

---

## ▶️ Como executar

```bash
./mvnw clean package -DskipTests
docker build -t delivery-service .
docker run -p 8083:8083 delivery-service
```

---

## 🗄️ Banco de dados

- **MongoDB** — porta `27017`
- **Database** — `deliverydb`
- **Collection** — `entregas`