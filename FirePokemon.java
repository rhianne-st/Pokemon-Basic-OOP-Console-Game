
public class FirePokemon extends Pokemon {
	//ATTRIBUTE
	private final String POKEMONTYPE;
	
	
	
	//CONSTRUCTORS
	public FirePokemon() {
		super();
		this.POKEMONTYPE = getPOKEMONTYPE();
	}
	
	public FirePokemon(String n, String t, int g, int h, int a, int d) {
		super(n, g, h, a, d);
		this.POKEMONTYPE = t;
	}
	
	
	
	//SETTER/GETTER
	public String getPOKEMONTYPE() {
		return POKEMONTYPE;
	}


	
	//ATTACK & DEFEND METHODS OVERRIDES THE METHODS PARENT CLASS (POLYMORPHISM)
	@Override
	public int attack(Pokemon target, int atkRoulette) {
		int atkPower = getATTACKPOWER();
		int defPower = target.getDEFENCEPOWER();
				
		double playerDmg = (atkPower - defPower)/3 + typeDamage(target) + atkRoulette;
		if (playerDmg < 10) {
			playerDmg = 10;
		}
		
		return (int) Math.round(playerDmg);
	}
	
	
	@Override
	public int defend(Pokemon target, int defRoulette) {
		int atkPower = target.getATTACKPOWER();
		int defPower = getDEFENCEPOWER();
		
		double opponentDmg = (atkPower - defPower)/3 + typeDamage(target) + defRoulette;
		if (opponentDmg < 10) {
			opponentDmg = 10;
		}
				
		return (int) Math.round(opponentDmg);
	}
	
	
	//POKEMON TYPE DETERMINES THE DAMAGE DEALT
	public int typeDamage(Pokemon target) {
		int typeDamage = 0;
		if (target instanceof WaterPokemon) {
			typeDamage = 5;
		}
		else if (target instanceof GrassPokemon) {
			typeDamage = 1;
		}
		else {
			typeDamage = 3;
		}
				
		return typeDamage;
	}
	
	
	
	//TOSTRING
	@Override
	public String toString() {
		return String.format(
				"Pokemon\t\t: %s\nType\t\t: %s\nGrade\t\t: %d\nHP\t\t: %d\nAttack Power\t: %d\nDefence Power\t: %d",
				getPOKEMONNAME(), getPOKEMONTYPE(), getGRADE(), getBattleHp(), getATTACKPOWER(), getDEFENCEPOWER());
	}

}
