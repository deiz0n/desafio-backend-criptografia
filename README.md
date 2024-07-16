﻿# Desafio 01 - Criptografia

## ` ` Problemática:
Implementar a criptografia em um serviço de forma transparente para a API e para as camadas de serviço de sua aplicação. O objetivo é garantir que os campos sensíveis dos objetos de entidade não sejam visíveis diretamente, realizando a criptografia em tempo de execução durante a conversão da entidade para a coluna correspondente no banco de dados, e vice-versa.

## Requisitos

- Implemente um CRUD simples considerando os campos mencionados acima como sensíveis.
- Utilize o algoritmo de criptografia de sua preferência. Sugestões: [SHA-512](https://en.wikipedia.org/wiki/SHA-2) ou
  [PBKDF2](https://en.wikipedia.org/wiki/PBKDF2).

## ` ` Solução:
Para a problemática apresentada desenvolvi um CRUD simples em Java com Spring, para o
banco de dados, optei pelo H2
