# Desafio Totvs - Backend

Bem-vindo ao repositório do Desafio de Backend para a vaga de emprego na TOTVS. Este projeto consiste em uma API de cliente desenvolvida em Spring Boot com JDK 21, permitindo a criação, edição e listagem de clientes. O banco de dados utilizado é o PostgreSQL.

## Requisitos
1. PostgreSQL 16.1
2. JDK 21
3. Maven 4.0.0

## Configuração do Banco de Dados

1. Crie um banco de dados no seu PostgreSQL.
2. Abra o arquivo 'application.properties' que se econtra no diretório 'src/main/resources'.
3. Modifique as configurações do banco de dados de acordo com suas credenciais:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco_de_dados
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

## Executando o Projeto

1. Clone o repositório para o seu ambiente local.
2. Instale todas as dependências do Maven.
3. Rode o projeto a partir da classe DesafioTotvsApplication.java.

## Uso da API
### Endpoints
**Criar Cliente**
- Endpoint: `POST /cliente`
- Body:
```
{
    "nome": "João Augusto Moreira Ananias",
    "endereco": "Teste",
    "bairro": "Teste",
    "telefones": [
        {
            "telefone": "62999999999"
        },
        {
            "telefone":"12345678901"
        }
    ]
}
```

**Editar Cliente**
- Endpoint: `PUT /cliente`
- Body
```
{
    "id": 1,
    "nome": "João Augusto Moreira",
    "endereco": "Rua 2",
    "bairro": "Morada Nova",
    "telefones": [
        {
            "telefone": "6291866794"
        }
    ]
}
```

**Listar Clientes**
- Endpoint: `GET /cliente`