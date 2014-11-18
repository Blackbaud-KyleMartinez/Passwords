import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.*;
import java.lang.*;

public class Passwords {

    private static int[][] followersTable;
    private static BufferedReader referenceFile;
    private static final char[] validCharList = new char[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
                                                            'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
                                                            'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                                                            'y', 'z'};
    private static int[] counts;
    private static int[] starters;
    private static int startersCount=0;


    public static void main(String[] args) {
        try{
            referenceFile = new BufferedReader(new FileReader(args[0]));
            int numOfPasswords = Integer.parseInt(args[1]);
            int sizeOfPasswords = Integer.parseInt(args[2]);
            counts = new int[26];
            starters = new int[26];
            followersTable = generateFollowersTable(referenceFile);
            printFollowersTable();
            printCounts();
            printsStarters();
            getStarterCount();
            getPasswords(numOfPasswords, sizeOfPasswords);
            referenceFile.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static int[][] generateFollowersTable(BufferedReader inputFile) throws IOException{
        int[][] table = new int[26][26];
        String line;
        String[] words;
        int counter = 0;
        while((line = inputFile.readLine()) != null){
            line = line.toLowerCase();
            words = line.split("\\s+");
            for(int i =0; i < words.length; i++){
                System.out.println(words[i]);
                updateTable(table, words[i]);
            }
        }

        System.out.println("There are " + counter + " Z's!!!!");

        return table;
    }

    private static void updateTable(int[][] table, String word){
        char[] chars = word.toCharArray();
        int row;
        int column;
        if(chars.length > 1){
            for(int i =1; i < chars.length; i++){
                if(validChar(chars[i]) && validChar(chars[i-1])){
                    if((i-1) == 0)
                        starters[getAlphabetIndex(chars[i-1]) - 1] += 1;
                    row = getAlphabetIndex(chars[i-1]);
                    column = getAlphabetIndex(chars[i]);
                    table[row-1][column-1] += 1;
                    counts[getAlphabetIndex(chars[i-1]) - 1] += 1;
                }
            }
        }
    }

    private static boolean validChar(char c){
        for(int i =0; i < validCharList.length; i++){
            if(validCharList[i] == c)
                return true;
        }
        return false;
    }

    private static void getPasswords(int amount, int size){
        for(int i = 0; i < amount; i++){
            char s = getStarterLetter();
            String p = getPassword(s, size);
            System.out.println(p);
        }
    }
    private static String getPassword(char c, int size){
        String password = "";
        password += c;
        char starter = c; 
        char letter;
        for(int i = 0; i < size-1; i++){
            letter = getNextLetter(starter);
            password += letter;
            starter = letter;
        }
        return password;
    }

    private static char getNextLetter(char c){
        int index = getAlphabetIndex(c);
        int seed = 20;
        Random rand = new Random();
        int prob = rand.nextInt(counts[index-1]) + 1;
        int count=0;
        int i =0;
        while(count <= prob){
            count += followersTable[index-1][i];
            i++;
        }
        return getIndexAlphabet(i);
    }

    private static char getStarterLetter(){
        Random rand = new Random();
        int prob = rand.nextInt(startersCount) + 1;
        int i= 0;
        int count = 0;
        while(count <= prob){
            count += starters[i];
            i++;
        }
        char nextLetter = getIndexAlphabet(i);
        return nextLetter;
    }

    private static void printFollowersTable(){
        for(int row = 0; row < 26; row++){
            for(int column = 0; column < 26; column++){
                System.out.print(followersTable[row][column] + " ");
            }
            System.out.println();
        }
    }

    private static int getAlphabetIndex(char c){
        return (int)c - 96;
    }

    private static char getIndexAlphabet(int i){
        i += 96;
        return (char)i;
    }

    private static void getStarterCount(){
        for(int i =0; i < 26; i++){
            startersCount += starters[i];
        }
    }

    private static void printCounts(){
        System.out.println("COUNTS TABLE");
        for(int i = 0; i < 26; i++){
            System.out.print(counts[i] + " ");
        }
        System.out.println();
    }

    private static void printsStarters(){
        System.out.println("STARTERS TABLE");
        for(int i = 0; i < 26; i++){
            System.out.print(starters[i] + " ");
        }
        System.out.println();
    }
}
