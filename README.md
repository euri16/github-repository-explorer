# GitHub Repository Explorer - Android Project

The GitHub Repository Explorer is an Android application that displays a list of GitHub repositories. Upon selecting a repository from the list, detailed information about the repository is displayed, including the name, description, the number of stars, the number of forks, and the primary language used in the repository.

<div>
    <img src="https://github.com/euri16/github-repository-explorer/blob/main/screenshots/sololearn_screenshot1.png" alt="Screenshot 1" width="400" />
    <img src="https://github.com/euri16/github-repository-explorer/blob/main/screenshots/sololearn_screenshot2.png" alt="Screenshot 2" width="400" />
</div>

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Architecture](#architecture)
- [Testing](#testing)
- [Getting Started](#getting-started)

## Features

- List of GitHub repositories.
- Detailed view of individual repositories.
- In-app navigation from list view to detailed view.
- Offline data persistence.
- Efficient pagination and data loading.

## Technologies

The GitHub Repository Explorer utilizes a number of modern Android technologies and libraries:

- **Kotlin**: The project is fully written in Kotlin.
- **MVVM and MVI**: Both Model-View-ViewModel (MVVM) and Model-View-Intent (MVI) architectural patterns are used.
- **Navigation Component**: Handles in-app navigation.
- **Room**: Provides a local database to store data for offline access.
- **Paging3**: Efficiently loads and displays data in pages.
- **Hilt**: Provides dependency injection.
- **Kotlin Coroutines and Flow**: Handles asynchronous tasks and real-time data.
- **Retrofit**: Used for network operations.
- **Glide**: Handles image loading.
- **RecyclerView**: Displays list of repositories.
- **ConstraintLayout**: Used for flexible and responsive UI design.
- **Version Catalog**: Provides centralized dependencies management.
- **New Splash Screen API**: Utilizes the new splash screen API for an enhanced user experience.

## Architecture

The app is modularized and follows the clean architecture principles:

- **Presentation Layer**: Responsible for showing data to the user and interpreting the user's actions.
- **Domain Layer**: Contains all the use cases of the application.
- **Data Layer**: Manages the application's data and abstracts the origin of the data from the rest of the application.

This modularization benefits the team in several ways:

- The build time is highly reduced because of concurrent module compiling
- The app is ready to be scaled
- Any changes to the project are encapsulated to its own module, reducing the risk of regressions
- The relationship between layers and modules is clear

### Overview (Diagram)

![Architecture Diagram](https://github.com/euri16/github-repository-explorer/blob/main/arch_diagram.png)

## Testing

The GitHub Repository Explorer has been thoroughly tested with the following libraries:

- **Mockk**: Provides mocking for Kotlin.
- **Turbine**: Helps test Kotlin Coroutines and Flow.

## Getting Started

To clone and run this application, you'll need [Git](https://git-scm.com) and [Android Studio](https://developer.android.com/studio) installed on your computer.

From your command line:

- $ git clone https://github.com/euri16/github-repository-explorer.git
- $ cd github-repository-explorer

## Building and Running the App

First, Add your personal github token in the build.gradle of the :core:network module. 
After that and once you have cloned the repository and opened it in Android Studio, you can build and run the application.

## Testing the App

Unit tests are located in the `src/test` directory under each module. To run the tests:

1. Click on `Run -> Run 'All Tests'` from the top menu.

## License

This project is licensed under the MIT License. See `LICENSE` file for more information.

## Contact

Eury Pérez - [@_euryperez](https://twitter.com/_euryperez)

Project Link: [https://github.com/euri16/github-repository-explorer](https://github.com/euri16/github-repository-explorer)
