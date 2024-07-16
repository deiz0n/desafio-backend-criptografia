# Desafio 01 - Criptografia

## `❓` Problemática:
Implementar a criptografia em um serviço de forma transparente para a API e para as camadas de serviço de sua aplicação. O objetivo é garantir que os campos sensíveis dos objetos de entidade não sejam visíveis diretamente, realizando a criptografia em tempo de execução durante a conversão da entidade para a coluna correspondente no banco de dados, e vice-versa.

## `✔️` Requisitos:

- Implemente um CRUD simples considerando os campos mencionados acima como sensíveis.
- Utilize o algoritmo de criptografia de sua preferência. Sugestões: [SHA-512](https://en.wikipedia.org/wiki/SHA-2) ou
  [PBKDF2](https://en.wikipedia.org/wiki/PBKDF2).

## `💡` Solução:
Para tal problemática, desenvolvi um CRUD simples em Java e Spring. Além disso, optei pelo H2 como banco de dados. De forma geral, a aplicação aplica o conceito de programação orientada a eventos, na qual os eventos são responsáveis pela comunicação entre os microservices, implementando o padrão **Publisher-Subscriber**. Para tal feito, utilizei os eventos genéricos do próprio Spring. Os principais componentes da aplicação são o **EncodingDataService**, responsável pela implementação do algoritmo **PBKDF2**, realizando a criptografia e descriptografia dos dados, e o **UserService**, responsável pela implementação dos métodos de CRUD.

## `🧩` Diagrama de classes:
![Diagrama de classes](https://github.com/deiz0n/desafio-backend-criptografia/blob/main/class-diagram.png)
