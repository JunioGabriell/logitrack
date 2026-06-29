# 🚚 LogiTrack — Sistema de Logística com Microsserviços

Arquitetura de microsserviços simulando um sistema de logística e rastreamento de pedidos, com comunicação assíncrona via Apache Kafka, persistência em bancos SQL e NoSQL, simulação de serviços AWS com LocalStack e autenticação JWT com controle de acesso por roles (RBAC).

---

## 📐 Arquitetura

```
Cliente
   │
   ▼
POST /auth/login  →  auth-service (8080)  →  MySQL (authdb)
                          │
                       JWT Token
                          │
         ┌────────────────┼────────────────┐
         ▼                ▼                ▼
  user-service      order-service    tracking-service
    (8081)            (8082)            (8084)
    MySQL             MySQL            LocalStack
   (userdb)          (orderdb)       SQS + DynamoDB
                         │
                    Kafka Topic
                    "pedidos"
                         │
                         ▼
                 delivery-service
                    (8083)
                    MongoDB
                  (deliverydb)
```

---

## 🧩 Microsserviços

| Serviço | Porta | Banco | Responsabilidade |
|---|---|---|---|
| `auth-service` | 8080 | MySQL | Login, registro e geração de token JWT |
| `user-service` | 8081 | MySQL | Gerenciamento de usuários |
| `order-service` | 8082 | MySQL | Criação de pedidos e publicação no Kafka |
| `delivery-service` | 8083 | MongoDB | Consumo de pedidos do Kafka e gerenciamento de entregas |
| `tracking-service` | 8084 | DynamoDB | Rastreamento de pedidos via SQS e DynamoDB (LocalStack) |

---

## 🔐 Roles e Permissões (RBAC)

| Role | Quem é | Permissões |
|---|---|---|
| `ADMIN` | Funcionário da empresa | Acesso total a todos os endpoints |
| `CLIENT` | Cliente | Criar pedidos e ver os próprios pedidos |
| `DRIVER` | Entregador | Ver e atualizar entregas atribuídas, atualizar rastreamento |

---

## 🛠️ Tecnologias

- **Java 21**
- **Spring Boot 3.5.16**
- **Spring Security + JWT** — autenticação e autorização por roles
- **Apache Kafka** — comunicação assíncrona entre order-service e delivery-service
- **MySQL** — persistência relacional (auth, user e order)
- **MongoDB** — persistência NoSQL (delivery)
- **AWS LocalStack** — simulação local de SQS e DynamoDB (tracking)
- **Spring Boot Actuator** — monitoramento e health check
- **Swagger/OpenAPI** — documentação das APIs
- **Docker + Docker Compose** — containerização e orquestração

---

## 🔄 Fluxo completo

```
1. Cliente se registra via POST /auth/register
2. Cliente faz login via POST /auth/login e recebe o JWT
3. Cliente cria pedido via POST /orders com o JWT no header
4. order-service salva o pedido no MySQL e publica no Kafka
5. delivery-service consome o evento do Kafka e cria uma entrega no MongoDB
6. DRIVER atualiza o status da entrega via PUT /deliveries/{id}/status
7. DRIVER registra rastreamento via POST /tracking/{pedidoId}/update
8. tracking-service salva no DynamoDB e publica no SQS (LocalStack)
9. CLIENT consulta rastreamento via GET /tracking/{pedidoId}
```

---

## 📊 Monitoramento

Todos os serviços expõem endpoints do Spring Boot Actuator:

| Endpoint | Descrição |
|---|---|
| `/actuator/health` | Status da aplicação |
| `/actuator/metrics` | Métricas da aplicação |

---

## 📚 Documentação das APIs

Cada serviço expõe o Swagger na rota:

```
http://localhost:{porta}/swagger-ui/index.html
```

| Serviço | Swagger |
|---|---|
| auth-service | http://localhost:8080/swagger-ui/index.html |
| user-service | http://localhost:8081/swagger-ui/index.html |
| order-service | http://localhost:8082/swagger-ui/index.html |
| delivery-service | http://localhost:8083/swagger-ui/index.html |
| tracking-service | http://localhost:8084/swagger-ui/index.html |

---

## ▶️ Como executar

### Pré-requisitos
- Java 21
- Maven
- Docker Desktop

### 1. Gerar os JARs de cada serviço

```bash
cd auth-service && ./mvnw clean package -DskipTests && cd ..
cd user-service && ./mvnw clean package -DskipTests && cd ..
cd order-service && ./mvnw clean package -DskipTests && cd ..
cd delivery-service && ./mvnw clean package -DskipTests && cd ..
cd tracking-service && ./mvnw clean package -DskipTests && cd ..
```

### 2. Subir toda a infraestrutura

```bash
docker-compose up --build
```

### 3. Testar o fluxo completo

```bash
# 1. registrar usuário
POST http://localhost:8080/auth/register
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "senha": "123456",
  "role": "CLIENT"
}

# 2. fazer login
POST http://localhost:8080/auth/login
{
  "email": "joao@email.com",
  "senha": "123456"
}

# 3. usar o token retornado no header das próximas requisições
Authorization: Bearer {token}

# 4. criar pedido
POST http://localhost:8082/orders
{
  "descricao": "Notebook Dell",
  "endereco": "Rua das Flores, 123"
}
```

---

## 📁 Estrutura do repositório

```
logitrack/
├── docker-compose.yml
├── init-aws.sh
├── README.md
├── auth-service/
│   ├── Dockerfile
│   ├── README.md
│   └── src/
├── user-service/
│   ├── Dockerfile
│   ├── README.md
│   └── src/
├── order-service/
│   ├── Dockerfile
│   ├── README.md
│   └── src/
├── delivery-service/
│   ├── Dockerfile
│   ├── README.md
│   └── src/
└── tracking-service/
    ├── Dockerfile
    ├── README.md
    └── src/
```

---

## 👨‍💻 Autor

Desenvolvido por **Junio Gabriel** como projeto final do curso **Java Backend — EBAC**.