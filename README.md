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
</p>

## :page_with_curl: Sobre
Este repositório contém um sistema baseado em arquitetura Rest Api desenvolvida com Java e Spring Boot, o H2 para a persistência de dados com Spring Data, o spring-validation para fazer as validações necessárias da Api.

## :books: **Dependencias**
**spring-boot-starter-web**: Utilizado para tornar a aplicação web.

**spring-boot-starter-data-jpa**: Utilizado para fazer a integração com o banco de dados e gerenciar a persistência de dados do sistema.

**spring-boot-starter-validation**: Utilizado para as validações das Entidades, DTOs, etc.

**spring-boot-devtools**: Utilizado para o LiveReload do servidor.

**junit-vintage-engine**: Utilizado para fazer os testes unitários da aplicação.


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
