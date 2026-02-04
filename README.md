# Bounce Game

A platformer puzzle game inspired by the classic Nokia Bounce game, implemented in Java using the city.cs.engine library.

## About

This game recreates the nostalgic experience of Bounce from Nokia keypad phones. Players control Bounce through three levels, rescuing ball friends who each unlock unique abilities. The game combines puzzle-platform mechanics with different ball physics, culminating in a boss battle against the Hypnotiser.

## Gameplay

The objective is to guide Bounce through levels to rescue trapped ball friends. Each rescued friend provides access to a new ball mode with distinct physics properties.

### Ball Modes

Press M to switch between available ball modes:

- **Bounce** - Default mode with balanced movement and jump capabilities
- **Rock Ball** - Heavy ball that can break glass obstacles but moves slower and jumps lower due to increased gravity
- **Beach Ball** - Lightweight ball with reduced gravity, allowing higher jumps and longer air time

The first two levels use static puzzle-platform layouts. The final level features a scrolling screen and includes the boss fight.

## Setup

Requirements:
- JDK 8 or higher
- city.cs.engine library

To run the game:
1. Open the project in your Java IDE
2. Ensure city.cs.engine is in the build path
3. Run the main class

## Documentation

All classes include JavaDoc documentation. To generate the full documentation:

**IntelliJ IDEA:**
- Tools → Generate JavaDoc
- Select desired scope and output directory

**Command line:**
```bash
javadoc -d docs -sourcepath src -subpackages <package.name>
```

## Project Structure

The game uses object-oriented design with separate classes for game logic, physics handling, level management, and character states. Levels 1-2 implement static puzzle platforms while Level 3 uses a scrolling platform system.
