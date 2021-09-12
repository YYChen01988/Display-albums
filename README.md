# Display-albums
Default by displaying all the albums alphabetically and user can click the button to change album order by album title text length. User can also view the list without internet.


# Modules
## 1. Core
This can be accessed by all modules. Includes utils and abstractions.

## 2. app
With main application and injections and access to all modules. 

## 3. albums
Provides linear view of album titles. Tests including instrumental ui test using Espresso, viewModel unit tests using Mockito and database tests(SQLite).

# Gradle
## 1. config-android.gradle
    - Contains android configuration. Enabled viewbinding and buildvariants. Flavors needs to be added here and this will be accessed by all libraries build.gradles

## 2. config-properties.gradle
    - Contains properties that needs to be secured. BASE_URL in this code.

## 3. dependencies-all.gradle
    - Dependencies.


# Libraries
1. Koin(DI)
2. Android Architecture (AndroidX,ViewModel,navigation, Lifecycle, LiveData, Coroutines)
3. Retrofit2, OkHttp, Gson
4. Mockito
5. Junit
6. Espresso
7. SOLite
8. Ktlint


# Improvements
1. Database extend functions (CURD)
2. Albums can be removed or added and updated with UI interfaces
3. More unit tests
