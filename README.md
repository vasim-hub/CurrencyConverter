# Currency Converter App

This is a Android application which helpful to show currency conversion based on your selected
currency

## UI

 Splash Screen |                                                                                            
 --- |

 <img src="https://github.com/vasim-hub/CurrencyConverter/assets/10848154/a7174ac1-576b-4f08-991a-c2519905116c" width="250" /> | 

Exchange rate listing | Default screen   
  --- | --- |

 <img src="https://github.com/vasim-hub/CurrencyConverter/assets/10848154/f748ef56-65ef-4c78-b22b-2310ebd6141e" width="250" /> | 
 <img src="https://github.com/vasim-hub/CurrencyConverter/assets/10848154/bd425774-9179-4d9a-b60a-b575ee33ea42" width="250" /> |


- It will show splash screen
- It calculates currency conversion from selected currency to all other supported currencies with its
  country flag respectively.
- It support offline mode and to limit bandwidth, the app won't refetch remote data within 30
  minutes.

## Evaluation of the Project Structure (Multi Module)

### App
The presentation layer contains UIs for the app.Also can be create feature module for this layer

### Domain
This is the core layer of the application. The domain layer is independent of any other layers thus domain models and business logic can be independent from other layers.This means that changes in other layers will have no effect on domain layer eg. screen UI (presentation layer) or changing database (data layer) will not result in any code change withing domain layer.

Components of domain layer include:
- **Models**: Defines the core structure of the data that will be used within the application.
- **Use cases/Interacts**: They enclose a single action, like getting data from a database or posting to a service. They use the repositories to resolve the action they are supposed to do.

### Data
The data layer is responsible for selecting the proper data source for the domain layer.

- **Repositories**: Responsible for exposing data to the domain layer through the use case.
- **Mappers**: They perform data transformation between domain, dto and entity models.

### Remote

- **Network**: This is responsible for performing network operations eg. defining API endpoints using[ ](https://square.github.io/retrofit/)[Retrofit](https://square.github.io/retrofit/).

### Database

- **Cache**: This is responsible for performing caching operations using[ ](https://developer.android.com/training/data-storage/room)[Room](https://developer.android.com/training/data-storage/room).

### Shared
Define or Implement similar Class, interface, Util Methods which may be required to use across the Multiple modules

## Stacks

- [Coroutines](https://developer.android.com/kotlin/coroutines) - Performing asynchronous code with
  sequential manner.
- [Kotlin Flow](https://developer.android.com/kotlin/flow) - Reactive streams based on coroutines
  that can emit multiple values sequentially.
- [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - The DI
  framework which reduces the boilerplate.
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Presenter with
  its semi data persistence behavior.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Android modern toolkit for
  building native UI.
- [Compose Material 3](https://developer.android.com/jetpack/compose/designsystems/material3) -
  Helping me present Material Design.
- [Compose Navigation Component](https://developer.android.com/jetpack/compose/navigation) - For
  single-activity architecture with Compose.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android.
- [Room](https://developer.android.com/training/data-storage/room) - Save data in a local database
  and support to Kotlin Extensions and Coroutines.

For testing

- [Mockk](https://mockk.io/)- It is a dummy implementation for an interface or class. It also allows
  to define the output of certain method calls. It verify that if you want to check if a certain
  method of a mock object has been called or not.
- [Truth](https://truth.dev/) - Used for assertion effectively in tests

**Architecture and Pattern**

- Clean Architecture
- MVVM Architecture
- Single Activity
- Multi Module Implementation

## TODO

- [ ] Error state handling for all possible cases
- [ ] CI/CD Implementaiton
- [ ] Spotless Implementaiton
- [ ] Sonar Implementaiton

- ### **What is Clean Architecture?**

A well planned architecture is extremely important for an app to scale and all architectures have
one common goal- to manage the complexity of your app. This isn't something to be worried about in
smaller apps however it may prove very useful when working on apps with longer development lifecycle
and a bigger team.

Clean architecture was proposed
by[ ](https://en.wikipedia.org/wiki/Robert_C._Martin)[Robert C. Martin](https://en.wikipedia.org/wiki/Robert_C._Martin)
in 2012 in
the[ ](http://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)[Clean Code Blog](http://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
and it follows the SOLID principle.

- ### **Questions & Answers**

|**Q1** |Why clean architecture used in this project|
| :- | :- |
|**Ans** .|<p>Clean architecture is used for better code maintainability and testability of the project .</p><p></p><p>But we have to agree sometime managing that architecture is kind of burden </p>|

### Disclaimer

- Complex architectures like the pure clean architecture can also increase code complexity since
  decoupling your code also means creating lots of data transformations(mappers) and models,that may
  end up increasing the learning curve of your code to a point where it would be better to use a
  simpler architecture like MVVM.