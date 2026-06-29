# 👤 user-service

Microsserviço responsável pelo gerenciamento de usuários com controle de acesso baseado em roles (RBAC).

---

## 🛠️ Tecnologias

- Java 21
- Spring Boot 3.5.16
- Spring Security + JWT
- Spring Data JPA
- MySQL
- Swagger/OpenAPI
- Spring Boot Actuator
- Docker

---

## 🚀 Endpoints

| Método | Endpoint | Descrição | Role |
|---|---|---|---|
| GET | `/users` | Lista todos os usuários | ADMIN |
| GET | `/users/{id}` | Busca usuário por ID | ADMIN |
| PUT | `/users/{id}` | Atualiza usuário | ADMIN |
| DELETE | `/users/{id}` | Deleta usuário | ADMIN |

### Exemplo de requisição

```bash
GET http://localhost:8081/users
Authorization: Bearer {token}
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
http://localhost:8081/swagger-ui/index.html
```

---

## ▶️ Como executar

```bash
./mvnw clean package -DskipTests
docker build -t user-service .
docker run -p 8081:8081 user-service
```

---

## 🗄️ Banco de dados

- **MySQL** — porta `3306`
- **Database** — `userdb`