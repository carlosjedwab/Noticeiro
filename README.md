# Noticeiro

Este é o Noticeiro, um super agregador de blogs e notícias.

Projeto "Super agregador de blogs e notícias" da matéria MAC0321 da USP.

## Dependências do projeto
Este projeto é implementado em Java Spring MVC, utilizando o banco de dados MongoDB

* Dependências do Java Spring
    * Spring Web
    * Spring Security
    * Thymeleaf
    * Spring Data MongoDB
    
* Banco de dados
    * MongoDB

## Como executar
Primeiramente, é necessário ter o MongoDB em execução, pois o web app irá interagir
com ele para armazenamento dos dados.

### Instalação e execução do MongoDB
* Instale o MongoDB -> [Instalação do MongoDB](https://docs.mongodb.com/manual/installation/)
* Com o MongoDB instalado, abra um terminal e digite o seguinte comando: ```mongod```
* Com isto, o MongoDB deverá estar rodando e conectado ao localhost na porta 27017
* __IMPORTANTE__: O MongoDB reserva uma pasta para armazenamento dos dados. No Linux, o padrão é a pasta "/data/db",
porém, ao executá-lo pela primeira vez, talvez esta pasta não existirá e você precisará criá-la. Após ser criada,
é possível que o MongoDB não consiga permissão para acessá-la, devido ser uma pasta criada na root do sistema, neste caso
será necessário alterar as permissões de acesso para a pasta.
* No Linux, os dois problemas acima podem ser resolvidos com os seguintes comandos:
```
sudo mkdir -p /data/db
sudo chown -R $USER /data/db
```

### Execução do projeto
Com o MongoDB em funcionamento, há duas maneiras de executar o projeto:
* Primeira maneira - Execução por meio do maven
    * Instale o maven. No Ubuntu: ```sudo apt install maven```
    * Abra o terminal na pasta do projeto
    * Execute o comando: ```mvn spring-boot:run -D maven.test.skip=true```
    * Com isto, o projeto estará sendo executado e o site poderá ser acessado por meio da url __localhost:8080__
* Segunda maneira - Execução por meio do Eclipse
    * Instale o Plugin [Spring Tools 4](https://marketplace.eclipse.org/content/spring-tools-4-aka-spring-tool-suite-4)
    * Importe o projeto no Eclipse
    * Clique com o botão direito na pasta principal do projeto e execute o comando __Run as -> Spring Boot Application__
    * Com isto, o projeto estará sendo executado e o site poderá ser acessado por meio da url __localhost:8080__

## Configuração da conexão com o banco de dados
As configurações da conexão do web app com o banco de dados podem ser encontradas no arquivo
__src/main/resources/application.properties__ e por padrão são as seguintes:
```
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=testdb
```
Isto é, por padrão, a conexão com o MongoDB é feita no __localhost__, na porta __27017__ e o nome do banco de dados é __testdb__

## Testes do JUnit
Na versão atual, o web app conta com uma bateria simples de testes do JUnit, testando os principais métodos do código.


Os testes estão localizados na pasta __src/test/java/noticeiro__.

__IMPORTANTE__:
É importante notar que os testes são feitos com uma conexão real ao MongoDB, e, durante os testes, o banco de dados é apagado, portanto,
se os testes forem executados no mesmo banco de dados onde existem usuários cadastrados, eles serão perdidos. Isto pode ser evitado alterando-se
o banco de dados utilizado na hora de rodar os testes, o que pode ser feito mudando a linha de configuração ```spring.data.mongodb.database=testdb```, trocando o __testdb__ para outro nome, assim, o MongoDB utilizará um banco separado na hora dos testes.

## Imagens do Noticeiro
### Home
![Home](https://i.ibb.co/gz7vwzn/Screenshot-from-2020-06-12-04-09-16.png)
### Cadastro
![Cadastro](https://i.ibb.co/XkMrszz/Screenshot-from-2020-05-21-16-22-21.png)
### Login
![Login](https://i.ibb.co/jkR5whj/Screenshot-from-2020-05-21-16-22-32.png)
### Feed
![Feed](https://i.ibb.co/JjN5DdL/Screenshot-from-2020-06-12-04-07-51.png)

__OBS__: Para remover URLs da lista do feed, posicione o mouse sobre a URL e o ícone de uma lixeira aparecerá. Clique no ícone.
