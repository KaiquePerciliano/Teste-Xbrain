# Teste Xbrain

Este projeto é uma API para gerenciamento de vendedores e vendas.


## Tecnologias Utilizadas

* Java

* Spring Boot

* JUnit (para testes unitários)

* Mockito (para criação de mocks em testes)


## Rotas da API

### Vendedor

<ins> Cadastrar Vendedor </ins> `POST /vendedor/`

Envia um objeto JSON com os dados do vendedor para cadastrá-lo.

<ins> Listar Vendedores </ins> `GET /vendedor/`

Retorna uma lista de todos os vendedores cadastrados.

<ins> Listar Vendedor por ID </ins> `GET /vendedor/{id}`

Retorna os detalhes de um vendedor específico.

<ins> Listar Vendedores com Vendas Filtradas por Data </ins> `GET /vendedor/periodo-vendas?dataInicio=YYYY-MM-DD&dataFim=YYYY-MM-DD`

Retorna vendedores e suas vendas dentro de um período especificado.

---

### Vendas

<ins> Cadastrar Venda </ins> `POST /vendas/`

Envia um objeto JSON com os detalhes da venda para cadastrá-la.

<ins> Listar Vendas </ins> `GET /vendas/`

Retorna todas as vendas registradas no sistema.

<ins> Listar Vendas por ID </ins> `GET /vendas/{id}`

Retorna os detalhes de uma venda específica.

<ins> Listar Vendas por ID do Vendedor </ins> `GET /vendas/vendedor/{idVendedor}`

Retorna todas as vendas de um determinado vendedor.
