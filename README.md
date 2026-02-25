# Fintrack API

REST API para controle financeiro pessoal. Permite gerenciar usuários, contas bancárias e transações financeiras, com atualização automática de saldo a cada movimentação registrada.

---

## Tecnologias

- **Java 17**
- **Spring Boot**
- **PostgreSQL**

---

## Pré-requisitos

- Java 17+
- PostgreSQL rodando localmente na porta `5432`

---

## Como executar

**1. Clone o repositório**
```bash
git clone https://github.com/guimarobo/fintrack-api.git
cd fintrack-api
```

**2. Crie o banco de dados**
```sql
CREATE DATABASE fintrack;
```

**3. Configure as credenciais**

Edite o arquivo `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/fintrack
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

**4. Execute a aplicação**
```bash
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

> As tabelas são criadas automaticamente pelo Hibernate na primeira execução.

---

## Endpoints

### Usuários `/users`
| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/users` | Lista todos os usuários |
| GET | `/users/{id}` | Busca usuário por ID |
| POST | `/users` | Cria um usuário |
| PUT | `/users/{id}` | Atualiza um usuário |
| PATCH | `/users/{id}` | Atualiza campos específicos |
| DELETE | `/users/{id}` | Remove um usuário |

### Contas `/accounts`
| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/accounts` | Lista todas as contas |
| GET | `/accounts/{id}` | Busca conta por ID |
| POST | `/accounts` | Cria uma conta |
| PUT | `/accounts/{id}` | Atualiza uma conta |
| PATCH | `/accounts/{id}` | Atualiza campos específicos |
| DELETE | `/accounts/{id}` | Remove uma conta |

### Transações `/transactions`
| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/transactions` | Lista todas as transações |
| GET | `/transactions/{id}` | Busca transação por ID |
| POST | `/transactions` | Registra uma transação |
| PUT | `/transactions/{id}` | Atualiza uma transação |
| PATCH | `/transactions/{id}` | Atualiza campos específicos |
| DELETE | `/transactions/{id}` | Remove uma transação |

---

