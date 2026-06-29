# 🔐 auth-service

Microsserviço responsável pelo registro e autenticação de usuários, geração de tokens JWT e controle de acesso por roles (RBAC).

---

## 🛠️ Tecnologias

- Java 21
- Spring Boot 3.5.16
- Spring Security
- Spring Data JPA
- MySQL
- JWT (jjwt 0.12.6)
- Swagger/OpenAPI
- Spring Boot Actuator
- Docker

---

## 🔐 Roles disponíveis

| Role | Descrição |
|---|---|
| `ADMIN` | Acesso total ao sistema |
| `CLIENT` | Cliente que faz pedidos |
| `DRIVER` | Entregador |

---

## 🚀 Endpoints

| Método | Endpoint | Descrição | Autenticação |
|---|---|---|---|
| POST | `/auth/register` | Registra novo usuário | Aberto |
| POST | `/auth/login` | Realiza login e retorna JWT | Aberto |

### Exemplo de registro

```json
POST /auth/register
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "senha": "123456",
  "role": "CLIENT"
}
```

### Exemplo de login

```json
POST /auth/login
{
  "email": "joao@email.com",
  "senha": "123456"
}
```

### Resposta do login

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
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
http://localhost:8080/swagger-ui/index.html
```

---

## ▶️ Como executar

```bash
./mvnw clean package -DskipTests
docker build -t auth-service .
docker run -p 8080:8080 auth-service
```

Ou via docker-compose na raiz do projeto:

```bash
docker-compose up --build
```

---

## 🗄️ Banco de dados

- **MySQL** — porta `3306`
- **Database** — `authdb`