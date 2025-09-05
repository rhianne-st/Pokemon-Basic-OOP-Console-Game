# 🔴 Pokémon Ga Olé-inspired OOP Console Game ⚪
This is a simple Java console-based game inspired by the Pokémon Ga Olé arcade game. The program focuses on the classic Battle and Catch mode, featuring a streamlined gameplay loop with three Pokémon types: Grass, Water, and Fire. All interactions take place directly in the IDE console; no GUI is implemented.

## Key Features
1. **Random Pokémon Generation:** Generate a random set of Pokémon to battle against
2. **Pokémon Info Display:** View stats and details of available Pokémon
3. **Catching Mechanism:** Attempt to catch defeated Pokémon and add them to your collection
4. **Battle System:** Choose your Pokémon to battle opponents using simple type-advantage rules
5. **Data Persistence:** Save caught Pokémon to an external `.txt` file for later reference
6. **Leaderboard:** Calculate and display battle scores to track player progress

## Concepts Used
- **Object-Oriented Programming:** Inheritance, polymorphism, encapsulation, and modularity
- **File I/O:** Read and write operations with `.txt` files for saving and loading Pokémon data
- **Game Loop & State Management:** Structured gameplay flow with turn-based logic
- **Basic Arithmetic Logic:** Used for score calculations and battle outcomes

## Folder Structure
```
pokemon-basic-oop-console-game/
<br>├── src/    # Java source files
<br>├── data/   # Text files for input/output
<br>├── .gitignore
<br>├── LICENSE
<br>└── README.md
```

## How to Run?
1. **Prerequisites:**
   - Java JDK 8 or higher
   - Any Java IDE (e.g., IntelliJ IDEA, Eclipse, NetBeans)
2. **Clone the repository:**
   ```
   git clone <repo-url>
   cd pokemon-basic-oop-console-game
   ```
3. **Run the program:**
   Open the project in your IDE
   Ensure the `data/` folder is in the correct directory relative to `src/`
   Run `gameConsole.java` to start playing

## Limitations & Future Enhancements
### Limitations
- Only three Pokémon types (Grass, Water, Fire) are supported
- Battles use simple type-advantage rules (no advanced mechanics)
- Text-based interface only — no GUI or animations
- Pokémon stats and moves are kept basic for simplicity

### Future Enhancements
- Expand the roster to include more Pokémon types
- Add more complex battle mechanics (HP, movesets, status effects)
- Implement a GUI for a more interactive experience
- Introduce multiplayer or AI difficulty scaling
- Enhance leaderboard with player profiles and session history

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
