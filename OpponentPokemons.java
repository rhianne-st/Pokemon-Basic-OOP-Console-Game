import java.util.*;

public class OpponentPokemons {
	//ATTRIBUTES
	private Pokemon o1;
    private Pokemon o2;

	
	
	//CONSTRUCTORS
    public OpponentPokemons() {
        this.o1 = getO1();
        this.o2 = getO2();
    }
    
    public OpponentPokemons(Pokemon pokemon1, Pokemon pokemon2) {
        this.o1 = pokemon1;
        this.o2 = pokemon2;
    }
    
    

	//SETTER/GETTER
    public Pokemon getO1() {
        return o1;
    }
    
    public void setO1(Pokemon o1) {
		this.o1 = o1;
	}

    public Pokemon getO2() {
        return o2;
    }

	public void setO2(Pokemon o2) {
		this.o2 = o2;
	}



	public int opponentAttack(PlayerPokemons player) {    	
		System.out.println("\nOPPONENT'S ATTACK!");
		System.out.println("-- Your Pokémons Remaining Health --");
		System.out.printf("%-12s: %3d HP\n", player.getP1().getPOKEMONNAME(), player.getP1().getBattleHp());
		System.out.printf("%-12s: %3d HP\n", player.getP2().getPOKEMONNAME(), player.getP2().getBattleHp());
		
		int defRouletteVal = defenceRoulette();
		
		List<Pokemon> opponentPokemons = new ArrayList<>();
		opponentPokemons.add(getO1());
		opponentPokemons.add(getO2());
		
		List<Pokemon> playerPokemons = new ArrayList<>();
		playerPokemons.add(player.getP1());
		playerPokemons.add(player.getP2());
		
		for (Pokemon opponent : opponentPokemons) {		//loops for each of opponent's pokemons
			for (Pokemon target : playerPokemons) {		//loops for each of player's pokemons
				if (target.getBattleHp() > 0) {			//if player pokemon not yet defeated
					int atkDamage = 0;
					if (opponent.getBattleHp() > 0) {	//if opponent pokemon still alive, only can attack
						atkDamage = opponent.attack(target, defRouletteVal);
					}
					
					int newPlayerHP = target.getBattleHp() - atkDamage;
								
					if (newPlayerHP < 0) {
						target.setBattleHp(0);
					}
					else {
						target.setBattleHp(newPlayerHP);
					}
					
				}
			}
		}
		
		System.out.printf("Wild Pokémons %s and %s attacked!\n\n", o1.getPOKEMONNAME(), o2.getPOKEMONNAME());
		System.out.println("-- Your Pokémons Remaining Health --");
		System.out.printf("%-12s: %3d HP\n", player.getP1().getPOKEMONNAME(), player.getP1().getBattleHp());
		System.out.printf("%-12s: %3d HP\n", player.getP2().getPOKEMONNAME(), player.getP2().getBattleHp());
		System.out.println();
		
		return (int) (defRouletteVal*20);
		
	}
    

    public int defenceRoulette() {
    	Scanner input = new Scanner(System.in);
		
		ArrayList<String> defRltOptionList = new ArrayList<String>(Arrays.asList("Null", "Null", "Defence Arise", "Evasion Arise"));
		Collections.shuffle(defRltOptionList);
		
		System.out.println("\n<<< Defence Roulette >>>");
		int defChoice = -1;
	    while (defChoice < 1 || defChoice > 4) {
	    	System.out.print("Pick a number from 1 to 4 >> ");
	        try {
	            defChoice = input.nextInt();
	            if (defChoice < 1 || defChoice > 4) {
	                System.out.println("Invalid choice. Please enter a number between 1 and 4.");
	            }
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid input. Please enter a number.");
	            input.nextLine(); // clear the invalid input
	        }
	    }
		
		
		String defObtained = defRltOptionList.get(defChoice-1);
		
		int defendValue = 0;
		
		if (defObtained.equals("Null")) {
			defendValue = 15;
		}
		else if (defObtained.equals("Defence Arise")) {
			defendValue = 5;
		}
		else if (defObtained.equals("Evasion Arise")) {
			defendValue = 0;
		}
		
		System.out.println();
		System.out.printf("YOU'VE OBTAINED '%s'!\n", defObtained);
		
		return defendValue;
	}
		

    
	
	//TOSTRING
    @Override
	public String toString() {
		return String.format(
				"%-10s: Grade %d, %3d HP\n%-10s: Grade %d, %3d HP\n",
				o1.getPOKEMONNAME(), o1.getGRADE(), o1.getBattleHp(),
				o2.getPOKEMONNAME(), o2.getGRADE(), o2.getBattleHp());
	}

}