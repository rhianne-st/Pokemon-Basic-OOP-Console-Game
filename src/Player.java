import java.io.*;
import java.util.*;

public class Player {
	Scanner input = new Scanner(System.in);
	
	//ATTRIBUTES
	private static String playerName;
	static List<Pokemon> playerPokemons = new ArrayList<>();

	
	
	//CONSTRUCTOR
	public Player() {
		playerName = requestPlayerName();
	}
	
	
	
	//SETTER/GETTER
	public static String getPlayerName() {
		return playerName;
	}
	
	public static void setPlayerName(String playerName) {
		Player.playerName = playerName;
	}

	public static List<Pokemon> getPlayerPokemons() {
		return playerPokemons;
	}

	public static void setPlayerPokemons(List<Pokemon> playerPokemons) {
		Player.playerPokemons = playerPokemons;
	}

	
	
	//OTHER METHODS
	public String requestPlayerName() {
       Scanner input = new Scanner(System.in);
       System.out.println("=======================================\n"
    		   			+ "       Welcome to Pokémon Ga Olé!       \n"
    		   			+ "=======================================");
       
       String name = "";
       while (true) {
       System.out.print("Enter player name (3 letters) >> ");
       name = input.next().toUpperCase();
       if (name.length() == 3 && name.matches("[A-Z]+")) {
           break;
       } else {
           System.out.println("Invalid input. Please enter exactly 3 letters.");
       	}
       }
	   return name;
	}
	

	//READS PLAYER'S POKEMONS FROM EXTERNAL FILE & ADDS TO A LIST -- 'static' cuz is also used/called by other classes
	public static List<Pokemon> loadPlayerPokemons() {
		try (BufferedReader reader = new BufferedReader(new FileReader("data/playerPokemonList.txt"))) {
            String playerPokemonLine;
            while ((playerPokemonLine = reader.readLine()) != null) {
                String[] playerPokemonIndex = playerPokemonLine.split("; ");	//splits the line by '; ' from txt file, then appends the individual elements into an array
                
                if (playerPokemonIndex.length == 6) {		//checks if the line in the array has all 6 attributes (appended elements)
                    String name = playerPokemonIndex[0];
                    String type = playerPokemonIndex[1];
                    int grade = Integer.parseInt(playerPokemonIndex[2]);
                    int hp = Integer.parseInt(playerPokemonIndex[3]);
                    int atkpower = Integer.parseInt(playerPokemonIndex[4]);
                    int defpower = Integer.parseInt(playerPokemonIndex[5]);
                    
                    Pokemon loadPokemon;
                    switch (type.toLowerCase()) {
                        case "fire":
                        	loadPokemon = new FirePokemon(name, type, grade, hp, atkpower, defpower);
                            break;
                        case "water":
                        	loadPokemon = new WaterPokemon(name, type, grade, hp, atkpower, defpower);
                            break;
                        case "grass":
                        	loadPokemon = new GrassPokemon(name, type, grade, hp, atkpower, defpower);
                            break;
                        default:
                            throw new IllegalArgumentException("Unknown Pokémon type: " + type);
                    }
                    
                    playerPokemons.add(loadPokemon);
                    
                }
            }
        }
        
        catch (IOException e) {
        	System.out.println("Error reading from file: " + e.getMessage());
        }
		
		return playerPokemons;
		
	}
	
	
	//SAVES THE GENERAL playerPokemons LIST -- 'static' cuz is also used/called by other classes
	public static void savePokemons(boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/playerPokemonList.txt", append))) {
            for (int i = 0; i < playerPokemons.size(); i++) {
                Pokemon pokemon = playerPokemons.get(i);
                writer.write(pokemon.getPOKEMONNAME() + "; ");
                if (pokemon instanceof FirePokemon) {
                    writer.write("Fire; ");
                } else if (pokemon instanceof WaterPokemon) {
                    writer.write("Water; ");
                } else if (pokemon instanceof GrassPokemon) {
                    writer.write("Grass; ");
                }
                writer.write(pokemon.getGRADE() + "; ");
                writer.write(pokemon.getHP() + "; ");
                writer.write(pokemon.getATTACKPOWER() + "; ");
                writer.write(pokemon.getDEFENCEPOWER() + "\n"); 
            }
            writer.flush();
        }
        
        catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
        
    }
	
	
	
	//TOSTRING	
	@Override
	public String toString() {
		return String.format("\nWelcome %s!\n", playerName);
	}
	
	
}
