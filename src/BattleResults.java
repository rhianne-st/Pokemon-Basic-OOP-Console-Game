import java.io.*;
import java.util.*;

public class BattleResults extends Battle {
	
	//THIRD (LAST) PART OF GAME: DISPLAYING BATTLE SCORE & LEADERBOARD
	
	
	
	//CONSTRUCTOR
	public BattleResults() {
		displayResults();
		updateLeaderboard();
		displayLeaderboard();
	}
	
	
	
	//METHODS
	public void displayResults() {
		System.out.println();
		System.out.println();
		System.out.println("===============================================\n"
	   					 + "                    RESULTS                    \n"
	   					 + "===============================================");
		System.out.println("The battle score for this time has come out!");
		System.out.println("Battle Score: " + totalScore);
	}
	
	
	//UPDATES THE LEADERBOARD IN EXTERNAL FILE
	public void updateLeaderboard() {
        List<String> leaderboard = new ArrayList<>();

        //reads current leaderboard from the file
        try (BufferedReader reader = new BufferedReader(new FileReader("data/gameLeaderboard.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                leaderboard.add(line);
            }
        }
        catch (IOException e) {
            System.out.println("Error reading LEADERBOARD file: " + e.getMessage());
        }

        //adding player name & totalscore into the new leaderboard list (for further processing & updating)
        leaderboard.add(Player.getPlayerName() + " —— " + totalScore);

        //sorting the leaderboard by score in descending order
        leaderboard.sort((a, b) -> {
            int scoreA = Integer.parseInt(a.split(" —— ")[1]);
            int scoreB = Integer.parseInt(b.split(" —— ")[1]);
            return Integer.compare(scoreB, scoreA);
        });
        
        //keep only top 5
        if (leaderboard.size() > 5) {
            leaderboard = leaderboard.subList(0, 5);
        }

        //write the updated leaderboard back to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/gameLeaderboard.txt"))) {
            for (String entry : leaderboard) {
                writer.write(entry);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            System.out.println("Error writing to the leaderboard file: " + e.getMessage());
        }
        
        System.out.println();
        System.out.println("Leaderboard updated successfully!");
    }
	
	
	//DISPLAYING FINAL LEADERBOARD IN CONSOLE
	public void displayLeaderboard() {
		System.out.println("===================================\n"
	   					 + "            LEADERBOARD            \n"
	   					 + "===================================");

		List<String> leaderboard = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader("data/gameLeaderboard.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                leaderboard.add(line);
            }
        }
        catch (IOException e) {
            System.out.println("Error reading LEADERBOARD file: " + e.getMessage());
        }
		
		for (int i = 0; i < leaderboard.size(); i++) {
			System.out.printf("%d) %s\n", i+1, leaderboard.get(i));	//printing in '#) xxx -- 12345' format
		}
	}
}
