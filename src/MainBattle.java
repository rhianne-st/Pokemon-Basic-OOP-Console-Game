import java.io.*;
import java.util.*;

public class MainBattle extends Battle {
	
	//SECOND PART OF GAME: BATTLING
	//MOST OF THE PLAY USES METHODS FROM THE RESPECTIVE Pokemon & Player/OpponentPokemons CLASSES
	//THIS CLASS BASICALLY MANAGES THE GENERAL GAMEPLAY & PROCESSES IF EITHER SIDE HAS BEEN DEFEATED
	
	
	Scanner input = new Scanner(System.in);
	
	//ATTRIBUTES
	private int p1hp;
	private int p2hp;
	private int o1hp;
	private int o2hp;
	
	
	
	//CONSTRUCTOR
	public MainBattle() {
        this.p1hp = getPlayer().getP1().getBattleHp();
        this.p2hp = getPlayer().getP2().getBattleHp();
        this.o1hp = getOpponent().getO1().getBattleHp();
        this.o2hp = getOpponent().getO2().getBattleHp();
        startBattle();
    }
	

	
	//SETTER/GETTER
	public int getP1hp() {
		return p1hp;
	}

	public void setP1hp(int p1hp) {
		this.p1hp = p1hp;
	}

	public int getP2hp() {
		return p2hp;
	}

	public void setP2hp(int p2hp) {
		this.p2hp = p2hp;
	}

	public int getO1hp() {
		return o1hp;
	}

	public void setO1hp(int o1hp) {
		this.o1hp = o1hp;
	}

	public int getO2hp() {
		return o2hp;
	}

	public void setO2hp(int o2hp) {
		this.o2hp = o2hp;
	}
	
	
	
	//OTHER METHODS
	
	//THIS IS THE MAIN CODE RUN FOR THIS SPECIFIC SUBCLASS -- TAKES IN POKEMONS' REMAINING HP FOR FURTHER BATTLING
	public void startBattle() {
		Scanner input = new Scanner(System.in);
				
		System.out.println();
		System.out.println("LET'S BATTLE!");
		
		
		while ((getP1hp() > 0 || getP2hp() > 0) && (getO1hp() > 0 || getO2hp() > 0)) {	//ensures that at least 1 pokemon from each side is still alive
			String selectRound = roundRoulette();
			
			if (selectRound == "player") {
				int playerTurn = getPlayer().playerAttack(getOpponent());
				totalScore += playerTurn;
			}
			
			else if (selectRound == "opponent") {
				int opponentTurn = getOpponent().opponentAttack(getPlayer());
				totalScore += opponentTurn;
			}
			
			//updates the p/o/1/2 HP after each round
			this.p1hp = getPlayer().getP1().getBattleHp();
	        this.p2hp = getPlayer().getP2().getBattleHp();
	        this.o1hp = getOpponent().getO1().getBattleHp();
	        this.o2hp = getOpponent().getO2().getBattleHp();
	        
		}
		
		if (getO1hp() <= 0 && getO2hp() <= 0) {		//in the case where both wild pokemons have been defeated
			System.out.println("Wild Pokémons have fainted!");
			System.out.println();
			System.out.println();
	        System.out.println("Enter any key to catch a Pokémon.");
	        try {
	            System.in.read();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        defeatCapture();

		}
		
		else if (getP1hp() <= 0 && getP2hp() <= 0) {	//in the case where both player pokemons have been defeated
			System.out.println("Your Pokémons have fainted!");

			//ADD-ON FEATURE: SUPPORT POKEMON EXTRA ATTACK
			String supportInput = null;
			while (true) { // Loop until valid input is provided
			    System.out.print("\nCall Support Pokémon for help? [Y/N]: ");
			    supportInput = input.next().toUpperCase();
			    if (supportInput.equals("Y") || supportInput.equals("N")) {
			        break; // Exit the loop if input is valid
			    } else {
			        System.out.println("Invalid input. Please enter 'Y' or 'N'.");
			        input.nextLine(); // clear the invalid input
			    }
			}

			if (supportInput.equals("Y")) {
			    chooseSupportPokemon();
			    supportPokemonAttack();
			}
			
			
			if (getO1hp() <= 0 && getO2hp() <= 0) {		//if wild pokemons defeated after final attack
				System.out.println("Wild Pokémons have fainted!");
				System.out.println();
				System.out.println();
		        System.out.println("Enter any key to catch a Pokémon.");
		        try {
		            System.in.read();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        
		        defeatCapture();

			}
			
			else {		//if wild pokemons remain undefeated after final attack
				System.out.println("\n\nOh no! Wild Pokémons have ran away!\n\nGAME OVER!");
				
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
	
	
	//PLAYER CHOOSES 1/2 TO DETERMINE IF IT'S THEIR TURN OR OPPONENT TURN (RANDOMISED -- 50/50 CHANCE)
	public String roundRoulette() {
		Scanner input = new Scanner(System.in);
		
		ArrayList<String> startRouletteList = new ArrayList<String>(Arrays.asList("player", "opponent"));
		Collections.shuffle(startRouletteList);
		
		System.out.println("--------------------------------------------------");
		System.out.print("Pick 1 or 2 to determine who attacks >> ");
		
		 int startOption = -1;
	        while (startOption != 1 && startOption != 2) {
	            try {
	                startOption = input.nextInt();
	                if (startOption != 1 && startOption != 2) {
	                    throw new InputMismatchException();
	                }
	            } catch (InputMismatchException e) {
	                System.out.println("Invalid input. Please enter 1 or 2.");
	                input.nextLine(); // clear the invalid input
	            }
	        }
		
		return startRouletteList.get(startOption-1);
		
	}
	
	
	//METHODS FOR ADD-ON FEATURE 'SUPPORT POKEMON EXTRA ATTACK'
	public void chooseSupportPokemon() {
		List<Pokemon> playerPokemons = Player.getPlayerPokemons();
	    
	    for (int i = 0; i < playerPokemons.size(); i++) {
	        System.out.printf("[%d] %s\n", i + 1, playerPokemons.get(i).getPOKEMONNAME());
	    }
	    
	    int supportPokemonChoice = -1;
	    while (supportPokemonChoice < 1 || supportPokemonChoice > playerPokemons.size()) {
            System.out.print("Pick your Support Pokémon >> ");
            try {
                supportPokemonChoice = input.nextInt();
                if (supportPokemonChoice < 1 || supportPokemonChoice > playerPokemons.size()) {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please enter a valid number.");
                input.nextLine(); // clear the invalid input
            }
        }
	    
	    	Pokemon supportPokemon = playerPokemons.get(supportPokemonChoice-1);
	    	SupportPokemon playerChoiceSupport = new SupportPokemon(supportPokemon);
	    	setSupportPokemon(playerChoiceSupport);
	        
	        System.out.printf("\nYou picked %s as your Support Pokémon!\n\n", getSupportPokemon().getSupport().getPOKEMONNAME());
	    }
	    
	
	public void supportPokemonAttack() {
		int supportScore = getSupportPokemon().supportAttack(getOpponent(), o1hp, o2hp);
		totalScore += supportScore;
	}
	
	
	
	

}
