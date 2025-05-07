import java.io.IOException;
import java.util.*;

public class Battle {
	
	//THIS IS THE SUPERCLASS FOR THE OTHER BATTLE-RELATED SUBCLASSES
	
	
	
	//ATTRIBUTES
    protected static int totalScore = 0;
    private static PlayerPokemons player;
    private static OpponentPokemons opponent;
    private SupportPokemon supportPokemon;

    
    
	//SETTER/GETTER
    public static int getTotalScore() {
		return totalScore;
	}

	public static void setTotalScore(int totalScore) {
		Battle.totalScore = totalScore;
	}
	
	public static PlayerPokemons getPlayer() {
		return player;
	}

	public void setPlayer(PlayerPokemons p) {
		player = p;
	}

	public static OpponentPokemons getOpponent() {
		return opponent;
	}

	public void setOpponent(OpponentPokemons o) {
		opponent = o;
	}
	
	public SupportPokemon getSupportPokemon() {
		return supportPokemon;
	}

	public void setSupportPokemon(SupportPokemon supportPokemon) {
		this.supportPokemon = supportPokemon;
	}
	
	

	//METHOD -- RUNS THE GAME
	public void runBattle() {
    	new BattleSetup();
        new MainBattle();
        new BattleResults();
    }
	
	
	
	//MAIN-ISH METHOD THAT RUNS THE CAPTURE & SAVE
	public void defeatCapture() {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Wild Pokémons have fainted!");
		System.out.println();

		String pokeball = pokeBallRoulette();
		
		System.out.printf("[1] %s\n", getOpponent().getO1().getPOKEMONNAME());
		System.out.printf("[2] %s\n", getOpponent().getO2().getPOKEMONNAME());
		System.out.printf("Throw %s at which pokemon >> ", pokeball);
		
		int capturePokemonChoice = -1;
		while (capturePokemonChoice != 1 && capturePokemonChoice != 2) {
            try {
                capturePokemonChoice = input.nextInt();
                if (capturePokemonChoice != 1 && capturePokemonChoice != 2) {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter 1 or 2.");
                input.nextLine(); // clear the invalid input
            }
        }
		
		if (capturePokemonChoice == 1) {
			Pokemon pokemonToCapture1 = getOpponent().getO1();
			
			boolean catchRate = catchChance(pokeball, pokemonToCapture1);
			
			captureAndSave(catchRate, pokemonToCapture1);
			
		}
		else if (capturePokemonChoice == 2) {
			Pokemon pokemonToCapture2 = getOpponent().getO2();
			
			boolean catchRate = catchChance(pokeball, pokemonToCapture2);
			
			captureAndSave(catchRate, pokemonToCapture2);
			
		}
		
	}
	
	
	//PLAYER PICKS POKEBALL TO USE (RANDOMISED)
	public String pokeBallRoulette() {
		Scanner input = new Scanner(System.in);
		
		ArrayList<String> pokeballList = new ArrayList<String>(Arrays.asList(
				"Poké Ball", "Poké Ball", "Poké Ball", "Poké Ball", "Poké Ball", "Poké Ball", "Poké Ball", "Poké Ball",
				"Poké Ball", "Poké Ball", "Great Ball", "Great Ball", "Great Ball", "Ultra Ball", "Ultra Ball", "Master Ball"));
		Collections.shuffle(pokeballList);
				
		System.out.println("CATCH TIME!");
		System.out.println("<<< Pokéball Roulette >>>");
		
		int ballChoice = -1;
		while (ballChoice < 1 || ballChoice > 16) {
	            System.out.print("Pick a number from 1 to 16 >> ");
	            try {
	                ballChoice = input.nextInt();
	                if (ballChoice < 1 || ballChoice > 16) {
	                    throw new InputMismatchException();
	                }
	            } catch (InputMismatchException e) {
	                System.out.println("Invalid input. Please enter a number between 1 and 16.");
	                input.nextLine(); // clear the invalid input
	            }
	        }
		
		System.out.printf("\nYou have picked the %s!\n\n", pokeballList.get(ballChoice-1));
		
		return pokeballList.get(ballChoice-1);
		
	}
	
	
	//CATCH SUCCESS RATE DEPENDS ON POKEBALL & POKEMON'S GRADE
	public boolean catchChance(String pokeball, Pokemon pokemon) {
		boolean catchSuccess = false;
		if (pokeball.equals("Master Ball")) {
			catchSuccess = true;
			totalScore += 500;
		}
		
		else if (pokeball.equals("Ultra Ball")) {
			if (pokemon.getGRADE() <= 4) {
				catchSuccess = true;
				totalScore += 400;
			}
		}
		
		else if (pokeball.equals("Great Ball")) {
			if (pokemon.getGRADE() <= 3) {
				catchSuccess = true;
				totalScore += 300;
			}
		}
		
		else if (pokeball.equals("Poké Ball")) {
			if (pokemon.getGRADE() <= 2) {
				catchSuccess = true;
				totalScore += 200;
			}
		}
		
		return catchSuccess;
	}
	
	
	//CAPTURES POKEMON, THEN SAVE TO EXTERNAL FILE
	public void captureAndSave(boolean catchRate, Pokemon pokemon) {
		Scanner input = new Scanner(System.in);
		if (!catchRate) {
			System.out.printf("\nOh no! %s ran away!\n\n", pokemon.getPOKEMONNAME());
			System.out.println("Better luck next time :)");
			System.out.println();
	        System.out.println("Enter any key to display results.");
	        try {
	            System.in.read();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		
		else {
			System.out.println();
			System.out.println("HOORAY! YOU CAUGHT THE POKÉMON!");
			System.out.println();
			totalScore += (pokemon.getGRADE()*100);
			
			
			String saveOption = null;
			while (true) { // Loop until valid input is provided
				System.out.print("Save Pokémon to bag? [Y/N] >> ");
			    saveOption = input.next().toUpperCase();
			    if (saveOption.equals("Y") || saveOption.equals("N")) {
			        break; // Exit the loop if input is valid
			    } else {
			        System.out.println("Invalid input. Please enter 'Y' or 'N'.");
			        input.nextLine(); // clear the invalid input
			    }
			}

			if (saveOption.equals("Y")) {
				Player.playerPokemons.add(pokemon);
		        Player.savePokemons(false);
		        System.out.println();
		        System.out.printf("%s has been saved to your bag!\n", pokemon.getPOKEMONNAME());
			}
			
			System.out.println();
	        System.out.println("Enter any key to display results.");
	        try {
	            System.in.read();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		
	}

}
