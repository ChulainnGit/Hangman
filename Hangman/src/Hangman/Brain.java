package Hangman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Brain {

	public String[] dictionary;
    public String hiddenWord="_____";
    
    private List<String> potentials = new ArrayList<String>();
    private StringBuilder guessed = new StringBuilder();

    public Brain(String[] wordlist, String target) {
    	potentials.addAll(Arrays.asList(wordlist));
        dictionary = wordlist;
        hiddenWord = target;
    }

    public char guessLetter() {
    	Integer[] counts = new Integer[26];
    	String regex = "^" + hiddenWord.replaceAll("_", ".") + "$";
    	String correct = "[" + hiddenWord.replaceAll("_", "") + "]+";
    	String incorrect = correct.length() < 4 ? guessed.toString() : guessed.toString().replaceAll(correct, "");
    	Arrays.fill(counts, 0);
    	potentials.removeIf((word) -> { 
    		boolean matches = incorrect.length() == 0 || word.matches("[^" + incorrect + "]+");
    		matches = matches && word.matches(regex);
    		if (matches) {
    			Set<String> processed = new HashSet<String>();
    			Arrays.asList(word.split("")).forEach((character) -> {
    				if (guessed.indexOf(character) == -1 && processed.add(character)) {
    					counts[character.charAt(0) - 'a']++;
    				}
    			});
    		}
    		return !matches; 
    	});
    	if (potentials.size() == 1) {
    		String remaining = potentials.get(0).replaceAll("[" + guessed + "]", "");
	        guessed.append(remaining.charAt(0));
    		return remaining.charAt(0);
    	} else {
    		int half = potentials.size() / 2;
    		List<Integer> letters = new ArrayList<Integer>();
    		letters.addAll(Arrays.asList(counts));
    		letters.removeIf((count)->count==0);
    		letters.sort((a,b)->Integer.valueOf(Math.abs(a - half)).compareTo(Integer.valueOf(Math.abs(b - half))));
    		Integer median = letters.get(0);
    		char guess = (char)(Arrays.asList(counts).indexOf(median) + 'a');
	        guessed.append(guess);
	        return guess;
    	}
    }        

}



