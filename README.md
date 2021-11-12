*Languages: [en](README.en.md)*

# Marvel API

Marvel API é um aplicativo desenvolvido na linguagem Kotlin para Android que consome a API da Marvel (https://developer.marvel.com/) com propósito específico de estudos.

O app possui basicamente uma tela de listagem de personagens com opção de pesquisa e uma tela para exibir detalhes, quadrinhos, eventos e séries do personagem selecionado. 

A arquitetura definida segue recomendações e princípios da documentação oficial do Android e baseia-se na estrutura conforme imagem abaixo. 

![image](https://user-images.githubusercontent.com/2924219/132413978-d6026326-ed73-4956-9e47-0515938a8f96.png)

## Características do código e bibliotecas utilizadas

* Arquitetura MVVM com ViewModel para interação com repositório e UI
* Camada de dados utilizando repositório + data sources
* Coroutines para programação assíncrona e gerenciamento de longas tarefas
* Cliente HTTP e configuração de conexão da API com Retrofit
* Injeção de dependência com Dagger Hilt
* Carregamento e caching de imagens com Glide
* Armazenamento de resultados da API no banco de dados local com Room
* Mecanismo de paginação de resultados implementado manualmente
* Criação de classes genéricas auxiliares a fim de evitar repetições de código (princípios de DRY code)
* Testes unitários básicos (somente estudos)

## Como configurar

Para executar o projeto no Android Studio, antes é necessário criar uma chave de acesso à API pelo portal de desenvolvedor da Marvel (https://developer.marvel.com/). Após cadastro, serão gerados uma chave pública e uma chave privada que deverão ser adicionadas ao arquivo `local.properties` conforme exemplo:

`marvelApi.publicKey="your_public_key"`<br/>
`marvelApi.privateKey="your_private_key"`

**O projeto está pronto para ser executado! :)**
