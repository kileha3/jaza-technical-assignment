
# GitHub User Browser

An Android application that displays public GitHub users using the GitHub REST API.

## Features

- Browse GitHub users with pagination
- View user details, including bio, followers, and public repositories
- Offline caching
- Dark mode support
- Basic Pull-to-refresh
- Error and state change handling

## Architecture

The app follows Clean Architecture principles with MVVM pattern:

- **Data Layer**: Room database for offline caching, Retrofit for network calls, Repository pattern
- **Domain Layer**: Use cases and domain models
- **Presentation/UI Layer**: Jetpack Compose UI, ViewModels, State management

## Tech Stack

- **UI**: Jetpack Compose with Material 3
- **Architecture**: MVVM + Clean Architecture
- **DI**: Hilt
- **Pagination**: Paging 3
- **Networking**: Retrofit + OkHttp
- **Local Storage**: Room
- **Image Loading**: Coil
- **Async**: Kotlin Coroutines + Flow

## Key Design Decisions

1. **Jetpack Compose over XML**:
    - Faster UI development with less boilerplate
    - Better state management with recomposition
    - Native support for Material 3 and theming

2. **Paging 3 Library**:
    - Handles pagination logic out of the box
    - Built-in support for Compose
    - Automatic loading state management

3. **Clean Architecture**:
    - Clear separation of concerns
    - Easier testing with dependency inversion
    - Scalable for future features

4. **Offline First Approach**:
    - Room database as single source of truth
    - Improves user experience in poor connectivity

## Trade-offs

Skipped advanced bonuses like search to focus on core functionalities.

## Known limitations or improvements

1. **No Search Implementation**:
    -  GitHub's user search API requires authentication
    -  Would need to implement OAuth flow for full search functionality

2. **Enhanced Offline Support**
    - Implement more sophisticated caching strategies
    - Sync data when online (WorkManager)

3. **Enhanced Pull-To-Refresh Support**
    - Implement more sophisticated pull-to-refresh strategies by storing remote keys and refreshing on appropriate pages.

4. **Enhanced Security**
    - Avoid hardcoding the API base URL; instead, properties and the Gradle ecosystem would have been used to handle this.

5. **Database Migration**
    - Would have added database migration to accomodate entity changes and avoid schema conflicts.



## Building and Running the App
### Prerequisites
- Android Studio Hedgehog or newer
- JDK 17
- Android SDK with API level 34
### Steps
1. Clone the repository:
 ```bash
	 git clone https://github.com/kileha3/jaza-technical-assignment.git
```
2.  Open the project in Android Studio
3. Sync the project with Gradle files
4. Run the app on an emulator or physical device:
 ```bash
	 ./gradlew installDebug
```

### Running Unit Tests
```bash
	./gradlew test
```
