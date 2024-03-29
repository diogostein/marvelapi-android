*Languages: [pt-BR](README.md)*

# Marvel API

<div align="center">
  <img width="33%" src="https://diogostein.dev/assets/codelabs/marvel_1-5905335043ad5c482036c271f5ab3c2851bd8f76699925d7a87e149a5f3f06e0.png" />
  <img width="33%" src="https://diogostein.dev/assets/codelabs/marvel_2-a3c0272f4d8d4ca98fc15ff5a50a924f9b264b437e36323b4f9af34e02e5c01e.png" />
</div>

<hr/>

Marvel API is an application developed in Kotlin language for Android that consumes a Marvel API (https://developer.marvel.com/) for specific study purposes.

The app basically has a character listing screen with search option and a screen to display details, comics, events and series of the selected character.

The defined architecture follows the recommendations and principles of the official Android documentation and is based on the structure as shown in the image below.

![image](https://user-images.githubusercontent.com/2924219/132413978-d6026326-ed73-4956-9e47-0515938a8f96.png)

## Code features and libraries

* MVVM architecture with ViewModel for repository and UI interaction
* Data layer using repository + data sources
* Coroutines for asynchronous programming and long task management
* HTTP client and API connection setup with Retrofit
* Dependency Injection with Dagger Hilt
* Image loading and caching with Glide
* Storage of API results in local database with Room
* Manually implemented results paging engine
* Creation of auxiliary generic classes in order to avoid code repetitions (DRY code principles)
* Basic Unit Tests (only for study)

## How to setup

To run the project in Android Studio, you first need to create an API access key via the Marvel developer portal (https://developer.marvel.com/). After registration, a public key and a private key will be generated, which should be added to the `local.properties` file, as in the example:

`marvelApi.publicKey="your_public_key"`<br/>
`marvelApi.privateKey="your_private_key"`

**The project is ready to run! :)**
