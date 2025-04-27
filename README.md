# 📖 WordDictionaryApp

This is a simple Android app built with Kotlin and Jetpack Compose. It uses the Random Word API and Free Dictionary API to fetch a random English word and display its meaning. The goal of this app is to show a card view with an English word and its definition, allowing users to swipe right for the next word and swipe left to view the previous word.

## 🎥 Demo
<img src="assets/demo/appdemo.gif" width="300" height="650">

## 🚀 Features
Fetch a random English word

Retrieve and display the meaning of the word

Swipe right ➡️ to fetch the next word and meaning

Swipe left ⬅️ to view the previous word and meaning

Handles loading, success, and error states gracefully

Built with clean MVVM architecture

Modular and scalable project structure

## 🛠️ Tech Stack
Language: Kotlin

UI: Jetpack Compose

Architecture: MVVM (Model-View-ViewModel)

Networking: Retrofit

Dependency Injection: Hilt

Asynchronous Handling: Kotlin Coroutines

State Management: StateFlow with Compose collectAsState

Swipe Handling: pointerInput + detectDragGestures from Compose

App State Handling: UiState sealed class (Loading, Success, Error)

## 📦 API Reference
Random Word API

GET /word – Fetch a random English word

Free Dictionary API

GET /api/v2/entries/en/{word} – Fetch the definition of a given English word

## 🧪 Testing
Manual tests cover:

Proper loading and displaying of words and their meanings

Swipe gestures for navigation between words

Error handling for missing words or API failures

Smooth user interaction and feedback during network operations


## ✨ Future Improvements
Add offline caching for previous words

Enhance UI with swipe animations

Implement dark mode

Improve error messages with retry options

Add unit and UI tests


## 🏗️ Project Structure
```plaintext
com.example.worddictionaryapp
│
├── data/
│   ├── api/         # Retrofit interfaces for API communication
│   ├── model/       # Data models for Word and Meaning
│   ├── repository/  # Repository to manage data operations
│   └── constants.kt # API endpoints and constants
│
├── di/
│   ├── AppModule.kt     # Hilt module for dependency injection
│   └── WordDictionaryApp.kt # Application class setup
│
├── ui/
│   ├── screens/     # Compose UI screens
│   ├── theme/       # Theme and styling resources
│   ├── vm/          # ViewModels for UI state management
│   └── widgets/     # Reusable UI components (cards, loaders, etc.)
│
├── utils/
│   └── UiState.kt   # Sealed class representing API call states (Loading, Success, Error)
│
└── MainActivity.kt  # Host activity

