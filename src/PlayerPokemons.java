
import java.util.*;

public class PlayerPokemons {
	//ATTRIBUTES
	private Pokemon p1;
    private Pokemon p2;

	
	
	//CONSTRUCTORS
    public PlayerPokemons() {
        this.p1 = getP1();
        this.p2 = getP2();
    }
    
    public PlayerPokemons(Pokemon pokemon1, Pokemon pokemon2) {
        this.p1 = pokemon1;
        this.p2 = pokemon2;
    }
    

	
	//SETTER/GETTER
    public Pokemon getP1() {
        return p1;
    }
    
    public void setP1(Pokemon p1) {
		this.p1 = p1;
	}

    public Pokemon getP2() {
        return p2;
    }
    
	public void setP2(Pokemon p2) {
		this.p2 = p2;
	}


	
	//PLAYER'S POKEMONS ATTACKING THE OPPONENT'S POKEMONS
	public int playerAttack(OpponentPokemons opponent) {
    	int score = 0;
    	
		System.out.println("\nYOUR ALLY'S ATTACK!");
		System.out.println("-- Wild Pokémons Remaining Health --");
		System.out.printf("%-12s: %3d HP\n", opponent.getO1().getPOKEMONNAME(), opponent.getO1().getBattleHp());
		System.out.printf("%-12s: %3d HP\n", opponent.getO2().getPOKEMONNAME(), opponent.getO2().getBattleHp());
		
		int atkRouletteVal = attackRoulette();
		
		List<Pokemon> playerPokemons = new ArrayList<>();
		playerPokemons.add(getP1());
		playerPokemons.add(getP2());
		
		List<Pokemon> opponentPokemons = new ArrayList<>();
		opponentPokemons.add(opponent.getO1());
		opponentPokemons.add(opponent.getO2());
		
		for (Pokemon player : playerPokemons) {			//loops for each of player's pokemons
			for (Pokemon target : opponentPokemons) {	//loops for each of opponent's pokemons
				if (target.getBattleHp() > 0) {			//if opponent pokemon not yet defeated
					int atkDamage = 0;
					if (player.getBattleHp() > 0) {		//if player pokemon still alive, only can attack
						atkDamage = player.attack(target, atkRouletteVal);
					}
					
					int newOpponentHP = target.getBattleHp() - atkDamage;
								
					if (newOpponentHP < 0) {
						target.setBattleHp(0);
					}
					else {
						target.setBattleHp(newOpponentHP);
					}
					
					score += (atkDamage)*100;
				}
			}
		}
				
		System.out.printf("Your Pokémons %s and %s attacked!\n\n", p1.getPOKEMONNAME(), p2.getPOKEMONNAME());
		System.out.println("-- Wild Pokémons Remaining Health --");
		System.out.printf("%-12s: %3d HP\n", opponent.getO1().getPOKEMONNAME(), opponent.getO1().getBattleHp());
		System.out.printf("%-12s: %3d HP\n", opponent.getO2().getPOKEMONNAME(), opponent.getO2().getBattleHp());
		System.out.println();
		
		return score;
	}
    
    
    public int attackRoulette() {
    	Scanner input = new Scanner(System.in);

		ArrayList<Integer> atkRltOptionList = new ArrayList<Integer>(Arrays.asList(5, 5, 5, 10, 10, 20, 20, 35));
		Collections.shuffle(atkRltOptionList);
		
		System.out.println("\n<<< Attack Roulette >>>");
		int atkChoice = -1;
		while (atkChoice < 1 || atkChoice > 8) {
			System.out.print("Pick a number from 1 to 8 >> ");
			try {
				atkChoice = input.nextInt();
				if (atkChoice < 1 || atkChoice > 8) {
					System.out.println("Invalid choice. Please enter a number between 1 and 8.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a number.");
				input.nextLine(); // clear the invalid input
	    	}
		}
		
		System.out.println();
		int attackValue = atkRltOptionList.get(atkChoice - 1);
		System.out.printf("ATTACK ROSE BY %d!\n", atkRltOptionList.get(atkChoice-1));

		return atkRltOptionList.get(atkChoice-1);
	}
	

    
	//TOSTRING
    @Override
    public String toString() {
		return String.format(
				"%-10s: Grade %d, %3d HP\n%-10s: Grade %d, %3d HP\n",
				p1.getPOKEMONNAME(), p1.getGRADE(), p1.getBattleHp(),
				p2.getPOKEMONNAME(), p2.getGRADE(), p2.getBattleHp());
	}

}
