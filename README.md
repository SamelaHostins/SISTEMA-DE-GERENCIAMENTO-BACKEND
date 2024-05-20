# salao.online

Este projeto usa Quarkus, o Supersonic Subatomic Java Framework.

Se você quiser saber mais sobre o Quarkus, visite seu site: https://quarkus.io/.

## Índice
- <a href="#objetivos">Objetivos</a>
- <a href="#como-utilizar">Como Utilizar</a> 
- <a href="#tecnologias">Tecnologias</a>

## Executando o aplicativo em modo dev

Você pode executar sua aplicação no modo de desenvolvimento que permite codificação ao vivo usando:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTA:_**  O Quarkus agora vem com uma Dev UI, que está disponível no modo dev apenas em http://localhost:8080/q/dev/.

## Empacotando e executando o aplicativo

A aplicação pode ser empacotada usando:
```shell script
./mvnw package
```
Ele produz o `quarkus-run.jar` arquivo no `target/quarkus-app/` diretório.
Esteja ciente de que não é um _über-jar_ conforme as dependências são copiadas para o `target/quarkus-app/lib/` diretório.

A aplicação agora pode ser executado usando `java -jar target/quarkus-app/quarkus-run.jar`.

Se você deseja construir um _über-jar_, execute o seguinte comando:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

A aplicação, empacotado como um _über-jar_, agora pode ser executado usando `java -jar target/*-runner.jar`.


## Objetivos
 
- Disponibilizar interface intuitiva, seguindo princípios de usabilidade e experiência do usuário para os clientes fazerem agendamentos com o profissional; 
- Facilitar a organização dos compromissos dos profissionais por meio de calendários e da gestão de seus compromissos; 
- Contribuir para o controle financeiro do profissional; 
- Analisar o processo atual do Studio e de suas atividades e propor possíveis melhorias para aprimorar;
- Possibilitar a criação de estoques pro profissional poder ter controle dos seus produtos;
- Simplificar o cotidiano dos profissionais autônomos com MEI na área de estética.
- Oferecer diversas funcionalidades que irão auxiliar em uma melhor eficiência e autonomia


## Como Utilizar

Para rodar o trabalho, o processo envolve uma série de comandos Maven específicos para compilar e executar nosso projeto Java com Quarkus de forma eficiente. Primeiramente, é necessário utilizar o comando:  

```shell script
./mvn clean install
```
Aqui, o mvn indica que estamos utilizando o Maven, uma ferramenta fundamental para automação de compilação e gerenciamento de dependências em projetos Java. O parâmetro clean tem a função de limpar artefatos de compilações anteriores, enquanto install executa a compilação do projeto, realiza os testes necessários e gera um pacote do projeto. Este arquivo é então disponibilizado no repositório local do Maven, permitindo seu uso em outros projetos. Depois de concluir essa etapa, prossegue com o comando: 

```shell script
./mvn quarkus:dev ou mvn quarkus dev
```
Novamente o mvn indica o uso do Maven, enquanto quarkus:dev é específico para projetos Quarkus. Este comando inicia um servidor de desenvolvimento que monitora automaticamente o código. Ele detecta alterações em tempo real e recarrega o servidor dinamicamente, o que permite visualizar instantaneamente as mudanças enquanto é desenvolvido. Essa abordagem acelera significativamente o ciclo de desenvolvimento, eliminando a necessidade de reinicializações manuais do servidor após cada modificação.

## Tecnologias

* Maven v3.9.6
* Java v14
* Quarkus v2.15.3
* Mastruct v1.5.5
* Hibernate v1.18.32

## Guias Relacionados

- Hibernate ORM ([guide](https://quarkus.io/guides/hibernate-orm)): Defina seu modelo persistente com Hibernate ORM e Jakarta Persistence
- RESTEasy Reactive's REST Client ([guide](https://quarkus.io/guides/rest-client-reactive)): Chame serviços REST de forma reativa
- OpenID Connect Client ([guide](https://quarkus.io/guides/security-openid-connect-client)): Obtenha e atualize tokens de acesso de provedores OpenID Connect
- RESTEasy Classic's REST Client ([guide](https://quarkus.io/guides/rest-client)): Chame serviços REST
- RESTEasy Reactive ([guide](https://quarkus.io/guides/resteasy-reactive)): Uma implementação REST de Jacarta utilizando processamento de tempo de construção e Vert.x. Esta extensão não é compatível com a extensão quarkus-resteasy ou com qualquer uma das extensões que dela dependem.
- Amazon Lambda ([guide](https://docs.quarkiverse.io/quarkus-amazon-services/dev/amazon-lambda.html)): Conecte-se ao Amazon Lambda
- RESTEasy Classic Multipart ([guide](https://quarkus.io/guides/rest-json#multipart-support)): Suporte multipartes para RESTEasy Classic
- Amazon DynamoDB ([guide](https://docs.quarkiverse.io/quarkus-amazon-services/dev/amazon-dynamodb.html)): Conecte-se ao armazenamento de dados do Amazon DynamoDB
- RESTEasy Classic ([guide](https://quarkus.io/guides/resteasy)): Estrutura de endpoint REST implementando Jakarta REST e muito mais
- REST resources for Hibernate Reactive com Panache ([guide](https://quarkus.io/guides/rest-data-panache)): Gere recursos REST de Jacarta para suas entidades e repositórios do Hibernate Reactive Panache
- Amazon S3 ([guide](https://docs.quarkiverse.io/quarkus-amazon-services/dev/amazon-s3.html)): Conecte-se ao armazenamento em nuvem Amazon S3
- JDBC Driver - PostgreSQL ([guide](https://quarkus.io/guides/datasource)): Conecte-se ao banco de dados PostgreSQL via JDBC
- REST resources for Hibernate ORM com Panache ([guide](https://quarkus.io/guides/rest-data-panache)): Gere recursos REST de Jacarta para suas entidades e repositórios do Hibernate Panache
- Hibernate Validator ([guide](https://quarkus.io/guides/validation)): Valide propriedades de objetos (campo, getter) e parâmetros de método para seus beans (REST, CDI, Jakarta Persistence)
- SmallRye OpenAPI ([guide](https://quarkus.io/guides/openapi-swaggerui)): Documente suas APIs REST com OpenAPI - vem com Swagger UI
- Hibernate ORM com Panache ([guide](https://quarkus.io/guides/hibernate-orm-panache)): Simplifique seu código de persistência para Hibernate ORM por meio do registro ativo ou do padrão de repositório

## Código fornecido

### Hibernate ORM

Crie sua primeira entidade JPA

[Related guide section...](https://quarkus.io/guides/hibernate-orm)

[Seção Hibernate com Panache relacionada...](https://quarkus.io/guides/hibernate-orm-panache)


### REST Client

Invoque diferentes serviços através de REST com JSON

[Seção do guia relacionado...](https://quarkus.io/guides/rest-client)

### RESTEasy JAX-RS

Inicie facilmente seus serviços Web RESTful

[Seção do guia relacionado...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)

### RESTEasy Reactive

Inicie facilmente seus serviços Web RESTful reativos

[Seção do guia relacionado...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

### RESTEasy Reactive Qute

Crie sua página web usando Quarkus RESTEasy Reactive & Qute

[Seção do guia relacionado...](https://quarkus.io/guides/qute#type-safe-templates)
