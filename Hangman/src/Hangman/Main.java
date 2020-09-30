package Hangman;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		try {
			String line;
			List<String> words = new ArrayList<String>();
			InputStream in = Main.class.getResourceAsStream("words.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			while ((line = reader.readLine()) != null) words.add(line);
			int correct = 0, plays = 100;
			while (plays-- > 0) correct += play(words);
			System.out.println(correct + " out of " + 100);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int play(List<String> words) {
		List<Character> guesses = new ArrayList<Character>();
		String word = words.get(new Random().nextInt(words.size() - 1));
		String template = word.replaceAll(".", "_");
		Brain brain = new Brain(words.toArray(new String[0]), template);
		int lives = 8;
		StringBuilder guessed = new StringBuilder();
		while (lives > 0 && !template.equals(word)) {
			Character guess = brain.guessLetter();
			if (guesses.contains(guess)) {
				lives -= 1;
			} else {
				guesses.add(guess);
				guessed.append(guess);
				if (word.indexOf(guess) > -1) {
					template = word.replaceAll("[^" + guessed + "]", "_");
					brain.hiddenWord = template;
				} else {
					lives -= 1;
				}
			}
		}
		System.out.println(word + " > " + template);
		return template.equals(word) ? 1 : 0;
	}
}

