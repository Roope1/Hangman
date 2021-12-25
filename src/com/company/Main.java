package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
        Scanner guess = new Scanner(System.in);

        // import all words from word.txt into an array
        BufferedReader reader = new BufferedReader(new FileReader("src/com/company/word.txt"));
        List<String> lines = new ArrayList<String>();
        String line;
        while((line = reader.readLine()) != null){
            lines.add(line);
        }
        reader.close();
        String[] words = lines.toArray(new String[]{});

        // pick random word from array
        String wordToGuess = words[(int)(Math.random() * words.length)];
        // clear the lines array
        lines.clear();

        wordToGuess = wordToGuess.toLowerCase(Locale.ROOT);
        // Creating a char array the length of the word to guess
        char[] correct = new char[wordToGuess.length()];
        char[] guessed = new char[wordToGuess.length()];
        List<Character> usedLetters = new ArrayList<Character>();

        // Initializing both arrays
        for (int i = 0; i < wordToGuess.length(); i++){
            // wordToGuess into a char array
            correct[i] = wordToGuess.charAt(i);
            // guessed to "_"
            guessed[i] = '_';

            if(wordToGuess.charAt(i) == ' '){
                guessed[i] = ' ';
            }
        }

        int strikes = 0;
        final int MAXSTRIKES = 10;
        boolean addStrike = true;
        boolean gameWon = true;

        
        // game loop
        while(true){
            //  Game over condition, breaks the game loop
            if (strikes >= MAXSTRIKES){
                System.out.printf("%nGAME OVER! %nThe word was '%s'%n", wordToGuess);
                break;
            }

            for(int i = 0; i < wordToGuess.length(); i++){
                if(correct[i] != guessed[i]){
                    gameWon = false;
                    break;
                }
            }

            if(gameWon){
                System.out.printf("The word was: %s%n", wordToGuess);
                System.out.println("Congratulations, you won!");
                break;
            }

            gameWon = true;
            System.out.printf("%nUsed letters: %s%n", usedLetters);
            for (char ch : guessed){
                System.out.print(ch);
            }

            System.out.printf(" (word length %s)%n", wordToGuess.length());
            System.out.printf("You have %s strikes (10 ends the game).%n", strikes);
            System.out.print("Your guess: ");
            String guessedString = guess.nextLine();
            guessedString = guessedString.toLowerCase(Locale.ROOT);
            // change player guess from string to character (takes only the first character)

            if(guessedString.length() < 1){
                System.out.println("Please enter a character");
                continue;
            }

            char playerGuess = guessedString.charAt(0);
            char[] preCheckGuessed = guessed.clone();

            for(int i = 0; i < wordToGuess.length(); i++){
                if (checkGuess(playerGuess,correct[i])){
                   guessed = revealCharacter(guessed, playerGuess, i);
                }
            }

            for(int i = 0; i < wordToGuess.length();i++){
                if(preCheckGuessed[i] != guessed[i]){
                    addStrike = false;
                    break;
                }
            }

            if(addStrike){
                strikes++;
            }
            addStrike = true;
            usedLetters.add(playerGuess);
        }
        System.out.println("Thank you for playing.");
    }

    // returns true if character is in that spot, else returns false
    public static boolean checkGuess(char playerGuess, char correct){
        return playerGuess == correct;
    }

    public static char[] revealCharacter(char[] guessed,char guess, int index){
        guessed[index] = guess;
        return guessed;
    }
}
