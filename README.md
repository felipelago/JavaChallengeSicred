<h1 align="center">
Desafio Processo Seletivo da Sicred
</h1>

<p align="center">
  Criar um pequeno sistema de pagamentos
</p>

<p align="center">
  <a href="#page_with_curl-sobre">Sobre</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#books-dependencias">Dependencias</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#star-requisitos">Requisitos</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;  
  <a href="#rocket-começando">Começando</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#computer-iniciando-o-projeto">Iniciando o Projeto</a>&nbsp;&nbsp;&nbsp;
  <a href="#computer-testando-os-endpoints">Testando os Endpoints</a>&nbsp;&nbsp;&nbsp;
</p>

## :page_with_curl: Sobre
Este repositório contém um sistema baseado em arquitetura Rest Api desenvolvida com Java (1.8 corretto) e Spring (2.5.7), banco de dados H2 para a persistência de dados com Spring Data, o spring-validation para fazer as validações necessárias da Api. O fluxo foi um pouco alterado em relação ao exigido, implementei uma nova entidade chamada cliente, onde você cadastra alguns dados, incluindo o limite de crédito, a data e hora também são gerados no backend, não sendo necessário o usuário informar a hora, os códigos nsu e código de autorização eu gero números aleatórios já que não tenho como me conectar a API do ministério da fazenda. A entidade Transacao possui 2 valueObjects que são 'Descricao' e 'FormaPagamento', dessa forma além de ficar mais módular, é possível reutilizar esses valueObjects em outras futuras entidades caso necessário. Foi feito um tratamento de erros central (GlobalHandlerException) para tratar as exceções, pode ser melhorado, mas é funcional. Os testes unitários foram utilizados Junit(5.7.2) e Mockito.

## :books: **Dependencias**
**spring-boot-starter-web**: Utilizado para tornar a aplicação web.

**spring-boot-starter-data-jpa**: Utilizado para fazer a integração com o banco de dados e gerenciar a persistência de dados do sistema.

**spring-boot-starter-validation**: Utilizado para as validações das Entidades, DTOs, etc.

**spring-boot-devtools**: Utilizado para o LiveReload do servidor.

**junit-jupiter-api - 5.7.2**: Utilizado para fazer os testes unitários da aplicação.

**lombok**: Utilizado para gerar Getters, Setters, construtores, reduzir Boilerplate e injeção de dependências.


## :star: Requisitos
- Ter [**Git**](https://git-scm.com/) para clonar o projeto.
- Ter [**Java 8**]() instalado.
- Ter [**Maven**]([https://gradle.org/install/](https://maven.apache.org/download.cgi)) instalado. (Opcional)


## :rocket: Começando
``` bash
  # Clonar o projeto:
  $ git clone https://github.com/felipelago/JavaChallengeSicred

```

## :computer: Iniciando o Projeto
```bash
  # Instalar as dependências:
  $ mvn clean install 

  # Rodar a aplicação:
  $ mvn spring-boot:run
```
## :computer: Testando os Endpoints
Para testar os endpoints, por favor siga nestá ordem (lembrando que H2 é um banco em memória, então quando reiniciar a aplicação ele vai zerar os dados):
- **Step 1: Criar um cliente**

Endpoint: /api/v1/cliente<br>
Método: POST<br>
Exemplo de Request Body:
```JSON
{
    "nome": "João da Silva",
    "cpf": "12345678901",
    "limiteCredito": 1500.00,
    "numeroCartao": "1234567812345678"
}
```
- **Step 2: Crie uma transação (ou um pagamento)**

Endpoint: /api/v1/pagamento<br>
Método: POST<br>
Exemplo de Request Body:
```JSON
{
  "cartao": "1234567812345678",
  "descricao": {
    "valor": 200.00,
    "estabelecimento": "PetShop Mundo cão"
  },
  "formaPagamento": {
    "tipo": "AVISTA",
    "parcelas": 1
  }
}

```

- **Step 3: Estornar um pagamento/transação**
  ```
  Descrição: Estornar um pagamento
  Endpoint: api/v1/pagamento/estornar/{id}
  Método: PATCH
  ```
- **Outros Endpoints na aplicação:**
  ```
  Descrição: Listar todos os pagamentos/transações
  Endpoint: api/v1/pagamento/transacoes
  Método: GET
  ```
  ```
  Descrição: Listar um pagamento/transação por ID
  Endpoint: api/v1/pagamento/transacao/{id}
  Método: GET
  ```
  ```
  Descrição: Listar todos os estornos (todos os pagamentos/transações que estão com o status CANCELADO)
  Endpoint: api/v1/pagamento/estornos
  Método: GET
  ```
  ```
  Descrição: Listar a transação estornada pelo ID
  Endpoint: api/v1/pagamento/estorno/{id}
  Método: GET
  ```
