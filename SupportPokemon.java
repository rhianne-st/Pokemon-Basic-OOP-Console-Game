
public class SupportPokemon extends PlayerPokemons {
	//ATTRIBUTE
	private Pokemon support;
	
	
	
	//CONSTRUCTOR
	public SupportPokemon() {
        this.support = getSupport();
    }
    
    public SupportPokemon(Pokemon pokemon) {
        this.support = pokemon;
    }
    
    
    
    //SETTER/GETTER
	public Pokemon getSupport() {
		return support;
	}

	public void setSupport(Pokemon support) {
		this.support = support;
	}
	
	
	
	//OTHER METHODS
	public int supportAttack(OpponentPokemons opponent, int o1hp, int o2hp) {
    	int score = 0;
		
		if (o1hp > 0) {		//deals damage to opponent pokemon #1
			int atkDamage = support.attack(opponent.getO1(), 20);
			o1hp -= atkDamage;
			score += (atkDamage)*100;
		}
		
		if (o2hp > 0) {		//deals damage to opponent pokemon #2
			int atkDamage = support.attack(opponent.getO2(), 20);
			o2hp -= atkDamage;
			score += (atkDamage)*100;
		}
				
		System.out.printf("%s made a final attack!\n\n", getSupport().getPOKEMONNAME());
		System.out.println("-- Wild Pokémons Remaining Health --");
		System.out.printf("%-12s: %3d HP\n", opponent.getO1().getPOKEMONNAME(), o1hp);
		System.out.printf("%-12s: %3d HP\n", opponent.getO2().getPOKEMONNAME(), o2hp);
		System.out.println();
		
		return score;
	}
    

}
