
import java.util.*;
import java.io.*;

public class BattleSetup extends Battle {
	
	//FIRST PART OF GAME: WILD POKEMONS & PLAYER CHOOSE POKEMONS TO PLAY WITH
	
	
	
	//CONSTRUCTOR
	public BattleSetup() {
		System.out.println();
		System.out.println("=============================================\n"
					 	 + "           “Battle and Catch” Mode           \n"
					 	 + "=============================================");
		captureWildPokemon();
		opponentWildPokemons();
		playerSelectPokemons();
	}
	
	
	
	//METHODS
	
	//(AT THE START OF GAME) GENERATES 3 WILD POKEMONS, THEN PLAYER CATCH ONE OF THEM
	public void captureWildPokemon() {
		Scanner input = new Scanner(System.in);
		System.out.println("CATCH TIME!");
		
		List<Pokemon> initialCatch = Pokemon.generateRandomPokemon(3);	//generates 3 random pokemons
		Pokemon.printGeneratedPokemon(initialCatch, 1);
		
		int capturePokemonIndex = -1;
	    boolean validInput = false;

	    while (!validInput) {
	        System.out.print("Select Pokémon to capture >> ");
	        try {
	            capturePokemonIndex = input.nextInt();
	            if (capturePokemonIndex < 1 || capturePokemonIndex > initialCatch.size()) {
	                throw new InputMismatchException();
	            }
	            validInput = true;
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid input. Please enter a number between 1 and 3.");
	            input.nextLine(); // clear the invalid input
	        }
	    }
		
		Pokemon capturedPokemon = initialCatch.get(capturePokemonIndex-1);	//creates a Pokemon instance of the player's chosen pokemon
		System.out.println();
		System.out.printf("You have captured %s.\n", capturedPokemon.getPOKEMONNAME());

        Player.playerPokemons.add(capturedPokemon);		//adding the pokemon data to the main List in Player class
        Player.savePokemons(true);		//save the newly captured pokemon's data into playerPokemonList txt file by APPEND
        System.out.printf("\n%s has been saved to your bag!\n", capturedPokemon.getPOKEMONNAME());
        
        totalScore += 300;
        
        System.out.println();
        System.out.println("Enter any key to continue.");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	
	//GENERATES NEW SET OF 2 WILD POKEMONS FOR BATTLE
	public void opponentWildPokemons() {
		Scanner input = new Scanner(System.in);
		
		System.out.println("\n\nTHERE ARE TWO WILD POKÉMONS! BATTLE TO CATCH IT!");
		
		List<Pokemon> wildPokemonList = Pokemon.generateRandomPokemon(2);
		for (int i = 0; i < 2; i++) {
			Pokemon pokemon = wildPokemonList.get(i);
            String pokemonType = "";
            if (pokemon instanceof FirePokemon) {
                pokemonType = "Fire";
            } else if (pokemon instanceof WaterPokemon) {
                pokemonType = "Water";
            } else if (pokemon instanceof GrassPokemon) {
                pokemonType = "Grass";
            }
            
			System.out.printf(
					"%-10s: %-5s type, Grade %d, %3d HP\n",
					wildPokemonList.get(i).getPOKEMONNAME(), pokemonType, wildPokemonList.get(i).getGRADE(), wildPokemonList.get(i).getHP());
		}
		Pokemon wildPokemon1 = wildPokemonList.get(0);
		Pokemon wildPokemon2 = wildPokemonList.get(1);
		OpponentPokemons opponentWildPokemons = new OpponentPokemons(wildPokemon1, wildPokemon2);
		setOpponent(opponentWildPokemons);		//creates instance as OpponentPokemons in the Battle class
		
	}
	
	
	//SHOWS PLAYER THEIR AVAILABLE POKEMON, THEN LET THEM SELECT WHICH 2 THEY WANT TO USE IN BATTLE
	public void playerSelectPokemons() {
		Scanner input = new Scanner(System.in);
		
		System.out.println("\n\nSEND OUT YOUR POKÉMON NOW!");
		
		List<Pokemon> rentalPokemon = Pokemon.generateRandomPokemon(2);
		List<Pokemon> playerAvailablePokemonList = new ArrayList<>();
		playerAvailablePokemonList.addAll(rentalPokemon);
		playerAvailablePokemonList.addAll(Player.loadPlayerPokemons());
		
		System.out.println("--- Rental Pokémons --");
		Pokemon.printGeneratedPokemon(playerAvailablePokemonList.subList(0, 2), 1);
		System.out.println("--- Pokémons In Your Bag ---");
		Pokemon.printGeneratedPokemon(playerAvailablePokemonList.subList(2, playerAvailablePokemonList.size()-1), 3);
		
		int playerOpt1 = -1, playerOpt2 = -1;
	    boolean validInput1 = false, validInput2 = false;
		
	    System.out.println();
	    while (!validInput1) {
	        System.out.print("Choose your 1st Pokémon >> ");
	        try {
	            playerOpt1 = input.nextInt();
	            if (playerOpt1 < 1 || playerOpt1 > playerAvailablePokemonList.size()) {
	                throw new InputMismatchException();
	            }
	            validInput1 = true;
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid input. Please enter a valid Pokémon number.");
	            input.nextLine(); // clear the invalid input
	        }
	    }

	    while (!validInput2) {
	        System.out.print("Choose your 2nd Pokémon >> ");
	        try {
	            playerOpt2 = input.nextInt();
	            if (playerOpt2 < 1 || playerOpt2 > playerAvailablePokemonList.size() || playerOpt2 == playerOpt1) {
	                throw new InputMismatchException();
	            }
	            validInput2 = true;
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid input. Please enter a valid Pokémon number and make sure it is different from the 1st Pokémon.");
	            input.nextLine(); // clear the invalid input
	        }
	    }
		
		Pokemon playerPokemon1 = playerAvailablePokemonList.get(playerOpt1-1);
		Pokemon playerPokemon2 = playerAvailablePokemonList.get(playerOpt2-1);
		PlayerPokemons playerChoicePokemons = new PlayerPokemons(playerPokemon1, playerPokemon2);
		setPlayer(playerChoicePokemons);		//creates instance as PlayerPokemons in the Battle class
				
		System.out.println();
		System.out.printf("You have sent out %s and %s for battle!\n", getPlayer().getP1().getPOKEMONNAME(), getPlayer().getP2().getPOKEMONNAME());
		
		System.out.println();
        System.out.println("Enter any key to continue.");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
				
	}
	
	
}
