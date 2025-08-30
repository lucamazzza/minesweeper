# Minesweeper (JavaFX)

University project for the course “Software Engineering I”.  
Two‑month, team-based project implementing the classic Minesweeper game with a JavaFX user interface and an MVC-inspired architecture.

## Overview
This repository contains a desktop implementation of Minesweeper. The application is built in Java with a JavaFX UI and follows a clear separation of concerns between models, views, and controllers. It includes user preferences and basic internationalization support.

## Features
- Classic Minesweeper gameplay
  - Reveal cells, flag suspected mines, number hints indicate adjacent mines
  - Mine counter and in-game feedback
- Configurable board (size/mines) and game options
- JavaFX user interface with FXML-based views and CSS styling
- Internationalization via resource bundles (language can be selected in preferences)
- Persistent user preferences (e.g., language)
- Desktop integration
  - Application icon
  - On macOS, menu bar integration with the system menu

## Tech stack
- Java (recommended: 17 or later)
- JavaFX (controls, FXML)
- CSS (JavaFX styling)

## Project structure
The project is organized with a frontend module hosting the JavaFX application.

- frontend/
  - src/main/java/ch/supsi/minesweeper/
    - MainFx.java — JavaFX application entry point
    - controller/ — UI and game controllers (e.g., GameController, UserPreferencesController)
    - model/ — game and board state (e.g., GameModel, BoardModel)
    - view/ — FXML-backed views (e.g., MenuBarViewFxml, GameBoardViewFxml, UserFeedbackViewFxml)
  - src/main/resources/
    - assets/icon.png — application icon
    - messages*.properties — resource bundles for localization

Note: Exact layout may evolve; see the source tree for the latest structure.

## Getting started

### Prerequisites
- JDK 17+ installed and configured (JAVA_HOME set)
- One of:
  - Gradle (with the JavaFX plugin) or the provided Gradle Wrapper, or
  - Maven (with javafx-maven-plugin), or
  - A JavaFX SDK on your machine if running directly from the JDK without a build tool

### Clone
```
git clone https://github.com/lucamazzza/minesweeper.git
cd minesweeper
```

### Run from an IDE (recommended)
1. Open the project in your IDE (e.g., IntelliJ IDEA).
2. Ensure the JDK is set to 17 or later.
3. Mark the `frontend` module as an application module if needed.
4. Run the main class:
   - Main class: `ch.supsi.minesweeper.MainFx`

If your IDE does not automatically manage JavaFX modules, add VM options (see “Run with plain Java” below).

### Run with Maven
If the project includes Maven with JavaFX plugin:
```
mvn -f frontend javafx:run
```
or from the root if configured:
```
mvn javafx:run
```

### Run with plain Java (without build tool)
When running outside Gradle/Maven, you must provide JavaFX modules on the module path. Example:
```
java \
  --module-path /path/to/javafx-sdk-<version>/lib \
  --add-modules javafx.controls,javafx.fxml \
  -cp frontend/target/classes \
  ch.supsi.minesweeper.MainFx
```
Adjust the classpath to your compiled classes output directory (e.g., `build/classes/java/main` for Gradle).

## How to play
- Left click: reveal a cell.
- Right click: place/remove a flag to mark a suspected mine.
- Numbers indicate how many mines are adjacent to that cell.
- Clear all non-mine cells to win. Revealing a mine ends the game.

Game options (board size/mines) and language can be adjusted via the menu and/or preferences.

## Architecture
The application follows an MVC-inspired design:

- Models
  - `GameModel`, `BoardModel`: hold game and board state, expose observable changes.
- Controllers
  - `GameController`: centralizes game logic and event handling (menu actions, player actions).
  - `UserPreferencesController`: manages user preferences and notifications.
- Views (JavaFX, FXML-backed)
  - `MenuBarViewFxml`: application menu (integrates with system menu on macOS).
  - `GameBoardViewFxml`: grid of cells and interaction area.
  - `UserFeedbackViewFxml`: game status, counters, messages.

Internationalization is implemented via Java `ResourceBundle` (e.g., `messages.properties` and localized variants). Preferences are handled through a `UserPreferences` service.

## Development notes
- Minimum Java version: 17 (recommended).
- Ensure JavaFX modules are available at runtime (handled automatically by Gradle/Maven setups configured with the JavaFX plugin).
- Styling is done via JavaFX CSS in resources.
- On macOS, the menu bar is integrated into the system menu bar (uses `MenuToolkit`), so the in-window menu bar may not be shown.
