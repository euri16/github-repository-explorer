# GitHub Repository Explorer - Android Project

The GitHub Repository Explorer is an Android application that displays a list of GitHub repositories. Upon selecting a repository from the list, detailed information about the repository is displayed, including the name, description, the number of stars, the number of forks, and the primary language used in the repository.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Architecture](#architecture)
- [Testing](#testing)
- [Getting Started](#getting-started)

## Features

- List view of GitHub repositories.
- Detailed view of individual repositories including: name, description, stars, forks, and primary language.
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

## Testing

The GitHub Repository Explorer has been thoroughly tested with the following libraries:

- **Mockk**: Provides mocking for Kotlin.
- **Turbine**: Helps test Kotlin Coroutines and Flow.

## Getting Started

To clone and run this application, you'll need [Git](https://git-scm.com) and [Android Studio](https://developer.android.com/studio) installed on your computer.

From your command line:

# Clone this repository
$ git clone https://github.com/yourusername/github-repository-explorer.git

# Go into the repository
$ cd github-repository-explorer

# Open in Android Studio
# You can open the project directory in Android Studio to begin development.

## Building and Running the App

First, Add your personal github token in the build.gradle of the :core:network module.

Once you have cloned the repository and opened it in Android Studio, you can build and run the application.

From Android Studio:

1. Click on `Build -> Make Project` from the top menu to build the project.
2. To run the application on an Android device connected to your machine, click on `Run -> Run 'app'`.
3. To run the application on an emulator, you need to first set up an Android Virtual Device (AVD) by clicking on `Tools -> AVD Manager -> + Create Virtual Device`. Follow the wizard to create an AVD, and then run the application as described in step 2.

## Testing the App

Unit tests are located in the `src/test` directory under each module. To run the tests:

1. Click on `Run -> Run 'All Tests'` from the top menu.

Instrumented tests are located in the `src/androidTest` directory under each module. To run these tests, you will need an emulator or real device:

1. Click on `Run -> Run 'All Instrumented Tests'` from the top menu.

## Contributing

We welcome contributions from the community. If you wish to contribute:

1. Fork the project.
2. Create your feature branch (`git checkout -b feature/AmazingFeature`).
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`).
4. Push to the branch (`git push origin feature/AmazingFeature`).
5. Open a Pull Request.

Please make sure to update tests as appropriate.

## License

This project is licensed under the MIT License. See `LICENSE` file for more information.

## Contact

Your Name - [@_euryperez](https://twitter.com/_euryperez) - YourEmail

Project Link: [https://github.com/euri16/github-repository-explorer](https://github.com/euri16/github-repository-explorer)
