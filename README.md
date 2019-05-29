# Posts Api

[![Kotlin](https://img.shields.io/badge/kotlin-1.3.30-blue.svg)](http://kotlinlang.org) [![Gradle](https://img.shields.io/badge/gradle-3.3.2-%2366DCB8.svg)](https://developer.android.com/studio/releases/gradle-plugin) [![Mockito](https://img.shields.io/badge/mockito-2.27.0-orange.svg)](https://site.mockito.org/)

Repository focus on consume API from [Json Placeholder](https://jsonplaceholder.typicode.com/) about post, comments and user implementing Kotlin coroutines and MVVM Architecture. Throughout this project, other librarys also has been using; a section below describe better every library and his use on this.

## Architecture
MVVM Architecture has been implemented on this project. My thought is that this design pattern is a better approach in comparison with MVP(Model View Presenter), MVC(Model View Controller) and MVI(Model View Intent) due to this has some advantages that other architecure doesn't.

<img src="assets/mvvm-architecture.png" height="540">

## Advantages
The obvious purpose of MVVM pattern is abstraction of the View which reduces the amount of business logic in code-behind. However, following are some other solid advantages:

- The ViewModel is easier to unit test than code-behind or event-driven code.
- You can test it without awkward UI automation and interaction.
- The presentation layer and the logic is loosely coupled.


## Maintainability
- A clean separation of different kinds of code should make it easier to go into one or several of those more granular and focused parts and make changes without worrying. That means you can remain agile and keep moving out to new releases quickly.

## Testability
- With MVVM each piece of code is more granular and if it is implemented right your external and internal dependences are in separate pieces of code from the parts with the core logic that you would like to test. That makes it a lot easier to write unit tests against a core logic.

- Make sure it works right when written and keeps working even when things change in maintenance.

## Extensibility
- It sometimes overlaps with maintainability, because of the clean separation boundaries and more granular pieces of code.

- You have a better chance of making any of those parts more reusable.

- It has also the ability to replace or add new pieces of code that do similar things into the right places in the architecture.

## Disadvantages
- Some people think that for simple UIs, MVVM can be overkill.
- Similarly in bigger cases, it can be hard to design the ViewModel.
- Debugging would be bit difficult when we have complex data bindings.

## Dependencies

- Retrofit 2 [version: '2.4.0'](https://square.github.io/retrofit/)
- OkHttp 3 [version: '3.4.1'](https://square.github.io/okhttp/)
- AndroidX [version: '1.0.0'](https://mvnrepository.com/artifact/androidx)
- Arch Lifecycle [version: '2.0.0'](https://developer.android.com/jetpack/androidx/releases/lifecycle)
- Mockito [version: '2.27.0'](https://site.mockito.org/)
- Arch Testing [version: '1.1.1'](https://mvnrepository.com/artifact/android.arch.core/core-testing?repo=google)
- Kotlin Extension: [version: '1.0.0'](https://android.github.io/android-ktx/core-ktx/)
- Kotlin Coroutines: [version:'1.0.0-RC1'](https://github.com/Kotlin/kotlinx.coroutines)
- Gson: [version:'2.8.5'](https://github.com/google/gson)
- Material Design: [version:'1.0.0'](https://github.com/material-components)
- KodeIn: [version:'5.2.0'](https://github.com/Kodein-Framework/Kodein-DI)
- Room: [version:'2.1.0-alpha01'](https://github.com/rom-rb/rom)
- Three Ten Abp: [version:'1.1.0'](https://github.com/JakeWharton/ThreeTenABP)

## References

- ViewModel https://developer.android.com/topic/libraries/architecture/viewmodel
- LiveData https://developer.android.com/topic/libraries/architecture/livedata
- Android Architecture Blueprints https://github.com/googlesamples/android-architecture
- Android Jetpack: ViewModel https://www.youtube.com/watch?v=5qlIPTDE274
- Mockito : https://site.mockito.org/
- Unit Testing + Mockito + Kotlin + Architecture components https://medium.com/@marco_cattaneo/unit-testing-with-mockito-on-kotlin-android-project-with-architecture-components-2059eb637912
- Junit + LiveData https://jeroenmols.com/blog/2019/01/17/livedatajunit5/
- Eduardo Medina Github Account - MVVM project : https://github.com/emedinaa/kotlin-mvvm
- MVVM Resources: https://www.tutorialspoint.com/mvvm/mvvm_advantages.htm 

## License
[MIT](https://choosealicense.com/licenses/mit/)
