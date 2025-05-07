import java.util.*;
import java.io.*;

public abstract class Pokemon {
	//ATTRIBUTES
	private final String POKEMONNAME;
	private final int GRADE;
	private final int HP;	//original HP of pokemon
	private final int ATTACKPOWER;
	private final int DEFENCEPOWER;
	private int battleHp;	//this one is to be deducted during game battle
	private static List<Pokemon> pokemonsData = new ArrayList<>();
	
	
	
	//CONSTRUCTORS
	public Pokemon() {
		this.POKEMONNAME = getPOKEMONNAME();
		this.GRADE = getGRADE();
		this.HP = getHP();
		this.ATTACKPOWER = getATTACKPOWER();
		this.DEFENCEPOWER = getDEFENCEPOWER();
		this.battleHp = getHP();
	}
	
	public Pokemon(String pOKEMONNAME, int gRADE, int hP, int aTTACKPOWER, int dEFENCEPOWER) {
		this.POKEMONNAME = pOKEMONNAME;
		this.GRADE = gRADE;
		this.HP = hP;
		this.ATTACKPOWER = aTTACKPOWER;
		this.DEFENCEPOWER = dEFENCEPOWER;
		this.battleHp = hP;
	}



	//SETTER/GETTER
	public String getPOKEMONNAME() {
		return POKEMONNAME;
	}

	public int getGRADE() {
		return GRADE;
	}

	public int getHP() {
		return HP;
	}

	public int getATTACKPOWER() {
		return ATTACKPOWER;
	}

	public int getDEFENCEPOWER() {
		return DEFENCEPOWER;
	}

	public int getBattleHp() {
		return battleHp;
	}

	public void setBattleHp(int hp) {
		if (hp <= 0) {
			this.battleHp = 0;
		}
		else {
			this.battleHp = hp;
		}
	}
	
	public static List<Pokemon> getPokemonsData() {
		return pokemonsData;
	}

	public static void setPokemonsData(List<Pokemon> pokemonsData) {
		Pokemon.pokemonsData = pokemonsData;
	}
	
	

	//OTHER METHODS
	
	//NOTE: attack & defend method will eventually be override by subclasses to incl. type damage into the calculations
	abstract public int attack(Pokemon target, int atkRoulette);
	
	abstract public int defend(Pokemon target, int defRoulette);
	
	
	//READS FROM EXTERNAL pokemonsData FILE (THE LIST OF POKEMONS THAT WE'RE USING IN THIS JAVA PROGRAM)
	public static List<Pokemon> loadPokemonsFromFile() {
		try (BufferedReader reader = new BufferedReader(new FileReader("data/pokemonData.txt"))) {
            String pokemonDataLine;
            while ((pokemonDataLine = reader.readLine()) != null) {
                String[] pokemonDataIndex = pokemonDataLine.split("; ");	//splits the line by '; ' from txt file, then appends the individual elements into an array
                
                if (pokemonDataIndex.length == 6) {		//checks if the line in the array has all 6 attributes (appended elements)
                    String name = pokemonDataIndex[0];
                    String type = pokemonDataIndex[1];
                    int grade = Integer.parseInt(pokemonDataIndex[2]);
                    int hp = Integer.parseInt(pokemonDataIndex[3]);
                    int atkpower = Integer.parseInt(pokemonDataIndex[4]);
                    int defpower = Integer.parseInt(pokemonDataIndex[5]);
                    
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
                    
                    pokemonsData.add(loadPokemon);
                    
                }  
            } 
        }
        
        catch (IOException e) {
        	System.out.println("Error reading from file: " + e.getMessage());
        }
		
		return pokemonsData;

	}
    
    
	//GENERATING RANDOM POKEMON(S)
    public static List<Pokemon> generateRandomPokemon(int num) {	//'num' refers to how many pokemons that should be generated        
        List<Pokemon> loadedPokemonsList = loadPokemonsFromFile();
  
            Random random = new Random();	//creates a random number generator -- to be used to generate random index number
            
            List<Pokemon> generatedPokemons = new ArrayList<>();
            
            for (int i = 0; i < num; i++) {
                int randomIdx = random.nextInt(loadedPokemonsList.size());	//generates a random index within pokemon list size
                Pokemon randomPokemon = loadedPokemonsList.get(randomIdx);
                generatedPokemons.add(randomPokemon);
            }
            
			return generatedPokemons;
        
    }

    
    //BASICALLY JUST PRINTING THE POKEMON WITH AN [#] INDEX -- USED FOR DISPLAY PURPOSES
    public static void printGeneratedPokemon(List<Pokemon> list, int shift) {
    	for (int i = 0; i < list.size(); i++) {		//print the 3 pokemons' data
    		Pokemon pokemon = list.get(i);
            String pokemonType = "";
            if (pokemon instanceof FirePokemon) {
                pokemonType = "Fire";
            } else if (pokemon instanceof WaterPokemon) {
                pokemonType = "Water";
            } else if (pokemon instanceof GrassPokemon) {
                pokemonType = "Grass";
            }
    		
            System.out.printf(
            		"[%d] %-10s: %-5s type, Grade %d, %3d HP\n",
            		i+shift, list.get(i).getPOKEMONNAME(), pokemonType, list.get(i).getGRADE(), list.get(i).getBattleHp());
        }
    }
	

	
	//TOSTRING
	public String toString() {
		return String.format(
				"Pokemon\t\t: %s\nGrade\t\t: %d\nHP\t\t: %d\nAttack Power\t: %d\nDefence Power\t: %d",
				getPOKEMONNAME(), getGRADE(), getBattleHp(), getATTACKPOWER(), getDEFENCEPOWER());
	}


}
