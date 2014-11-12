import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class Passwords {

    private static int[][] followersTable;
    private static HashMap<String, Integer> characterPlace;

    public static void main(String[] args) {
        try{

            BufferedReader referenceFile = new BufferedReader(new FileReader(args[0]));
            characterPlace = getCharacterPlacement();
            followersTable = generateFollowersTable(referenceFile);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static int[][] generateFollowersTable(BufferedReader inputFile){
        int[][] table = new int[26][26];
        return table;
    }


    //Want to create a hashmap that contains the table placement for each character in the alphabet. For example a => 0, b => 1
    private static HashMap<String, Integer> getCharacterPlacement(){
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        String[] strings = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        for(int i =0; i < strings.length; i++){
            map.put(strings[i], i);
        }
        return map;
    }
}
