# Nudge - Task Management Android App

A production-ready Android task management application built with Kotlin, Jetpack Compose, and Material Design 3. The UI/UX is heavily inspired by **Microsoft To Do** with its clean, minimalist design and intuitive user experience.

## 📱 Screenshots

*Note: Screenshots will be added after the app is built and run*

## ✨ Features

### Core Functionality
- ✅ User authentication with secure BCrypt password hashing
- ✅ Create, read, update, and delete tasks
- ✅ Task filtering (All, Active, Completed, Pinned)
- ✅ Real-time search functionality
- ✅ Task statistics dashboard
- ✅ Priority levels (High, Medium, Low) with color indicators
- ✅ Multiple task color options (8 vibrant colors)
- ✅ Task categories for organization
- ✅ Pin important tasks
- ✅ Due date and reminder support

### UI/UX
- ✅ Clean, minimalist Microsoft To Do inspired design
- ✅ Material Design 3 with dynamic colors
- ✅ Light and Dark theme support
- ✅ Smooth animations and transitions
- ✅ Edge-to-edge display
- ✅ Responsive layouts
- ✅ Empty states with helpful messages

### Architecture
- ✅ MVVM (Model-View-ViewModel) architecture
- ✅ Clean architecture with separation of concerns
- ✅ Repository pattern for data access
- ✅ Dependency injection with Hilt
- ✅ Reactive programming with Kotlin Flow
- ✅ Type-safe navigation

## 🛠 Technology Stack

| Component | Technology |
|-----------|------------|
| Language | Kotlin 1.9.22 |
| UI Framework | Jetpack Compose |
| Design System | Material 3 |
| Architecture | MVVM |
| Database | Room 2.6.1 |
| Dependency Injection | Hilt/Dagger 2.50 |
| Async | Kotlin Coroutines + Flow 1.9.0 |
| Navigation | Jetpack Navigation Compose 2.8.4 |
| Password Hashing | BCrypt 0.10.2 |
| Preferences | DataStore 1.1.1 |
| Min SDK | 26 (Android 8.0) |
| Target SDK | 35 (Android 15) |
| JDK | Java 17 |

## 📂 Project Structure

```
app/src/main/java/com/phoenixdev/nudge/
├── data/
│   ├── local/
│   │   ├── entity/          # Room entities (User, Task)
│   │   ├── dao/             # Database access objects
│   │   ├── NudgeDatabase.kt # Room database
│   │   └── Converters.kt    # Type converters for enums
│   └── repository/          # Repository implementations
├── domain/
│   ├── model/               # Domain models (Priority, TaskColor, etc.)
│   └── repository/          # Repository interfaces
├── di/                      # Hilt modules for dependency injection
├── ui/
│   ├── theme/               # Material 3 theme (colors, typography, shapes)
│   ├── viewmodel/           # ViewModels (Auth, Task, Profile)
│   ├── screens/             # Composable screens
│   │   ├── auth/            # Login and Register screens
│   │   ├── home/            # Home screen with task list
│   │   └── profile/         # Profile screen
│   ├── components/          # Reusable UI components
│   └── navigation/          # Navigation setup
├── util/                    # Utility classes
└── MainActivity.kt          # Main entry point
```

## 🎨 Design

### Color Palette

Inspired by Microsoft To Do's vibrant and clean color scheme:

**Light Theme:**
- Primary: #6C63FF (Purple-Blue)
- Secondary: #FF6B9D (Pink)
- Background: #F7F7F7 (Light Gray)
- Surface: #FFFFFF (White)

**Dark Theme:**
- Primary: #8B82FF (Lighter Purple)
- Secondary: #FF8FA0 (Lighter Pink)
- Background: #0F0F1E (Very Dark Blue)
- Surface: #1A1A2E (Dark Blue-Gray)

**Task Colors:**
- Default (#6C63FF), Red (#E74C3C), Blue (#4A90E2), Green (#2ECC71)
- Purple (#9B59B6), Orange (#F39C12), Pink (#FF6B9D), Cyan (#1ABC9C)

### Typography

Uses the default system font (Roboto on Android) with Material 3 typography scale:
- Display, Headline, Title, Body, and Label styles
- Font weights: Regular (400), Medium (500), SemiBold (600)

### Shapes

Rounded corners matching Microsoft To Do:
- Small: 8dp
- Medium: 12dp
- Large: 16dp
- Extra Large: 24dp

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- JDK 17 or later
- Android SDK 26+
- Gradle 8.9+

### Building the Project

1. Clone the repository:
```bash
git clone https://github.com/phoenixdev-512/micro_habitracker_NUDGE.git
cd micro_habitracker_NUDGE
```

2. Open the project in Android Studio

3. Sync Gradle files

4. Build and run:
```bash
./gradlew assembleDebug
```

Or use Android Studio's build button.

### Running on Device/Emulator

1. Connect an Android device or start an emulator
2. Click "Run" in Android Studio or use:
```bash
./gradlew installDebug
```

## 📱 App Flow

### Authentication
1. Launch app → Splash screen
2. Not logged in → Login screen
3. Can navigate to Register screen
4. After login/register → Home screen

### Home Screen
1. View tasks with filters (All, Active, Completed, Pinned)
2. Search tasks by title/description
3. See statistics (Total, Completed, Active, High Priority)
4. Tap FAB to add new task
5. Tap task to view details
6. Swipe or use checkbox to complete tasks
7. Use pin icon to pin important tasks

### Profile
1. View user information
2. Update profile picture
3. Toggle theme (Light/Dark)
4. Manage notifications
5. Logout

## 🔒 Security

- Passwords are hashed using BCrypt with cost factor 12
- User sessions managed via DataStore
- No sensitive data stored in plain text
- Foreign key constraints for data integrity

## 📦 Dependencies

### Core
- AndroidX Core KTX 1.15.0
- Lifecycle Runtime KTX 2.8.7
- Activity Compose 1.9.3

### Compose
- Compose BOM 2024.11.00
- Material 3 1.4.0
- Material Icons Extended

### Database
- Room Runtime 2.6.1
- Room KTX 2.6.1
- Room Compiler (KSP)

### Dependency Injection
- Hilt Android 2.50
- Hilt Navigation Compose 1.2.0

### Navigation
- Navigation Compose 2.8.4

### Async
- Coroutines Android 1.9.0
- Coroutines Core 1.9.0

### Storage
- DataStore Preferences 1.1.1

### Security
- BCrypt 0.10.2

### UI Utilities
- Accompanist Permissions 0.36.0
- Accompanist System UI Controller 0.36.0
- Core Splash Screen 1.0.1

### Testing
- JUnit 4.13.2
- MockK 1.13.13
- Turbine 1.1.0 (Flow testing)
- Compose UI Test

## 🧪 Testing

Run unit tests:
```bash
./gradlew test
```

Run instrumented tests:
```bash
./gradlew connectedAndroidTest
```

## 📝 Code Quality

- Follows SOLID principles
- Clean architecture with clear separation of concerns
- Repository pattern for data access
- MVVM for presentation layer
- Proper error handling throughout
- Type-safe navigation
- Reactive data flow with Kotlin Flow

## 🔮 Future Enhancements

Potential features to add:
- [ ] Biometric authentication
- [ ] Task reminders with notifications
- [ ] Recurring tasks
- [ ] Task attachments (images, files)
- [ ] Task sharing and collaboration
- [ ] Cloud sync
- [ ] Widgets
- [ ] Wear OS support
- [ ] Data export/backup
- [ ] Subtasks
- [ ] Tags and custom filters

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👏 Acknowledgments

- Design inspiration from Microsoft To Do
- Material Design 3 guidelines
- Android Jetpack libraries
- Open source community

## 📧 Contact

For questions or feedback, please open an issue on GitHub.

---

**Built with ❤️ using Kotlin and Jetpack Compose**
