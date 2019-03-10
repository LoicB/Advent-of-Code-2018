import java.util.LinkedList;
import java.util.Stack;

public class AlchemicalReduction {
    public static void main(String[] args) {
        System.out.println(annalysePolymer("dabAcCaCBAcCcaDA"));
        System.out.println(annalysePolymer("aA"));
        System.out.println(annalysePolymer(FileToArray.readFile("./resources/AlchemicalReduction")[0]));
    
        
        System.out.println(lengthOfShortestPolymere("dabAcCaCBAcCcaDA"));
        System.out.println(lengthOfShortestPolymere(FileToArray.readFile("./resources/AlchemicalReduction")[0]));
    
    }

    // part 1
    public static int annalysePolymer(String polymere) {
        return reducePolymere(polymere).size();
    }

    // part 2
    private static int lengthOfShortestPolymere(String polymere) {
        int result = reducePolymere(removeChar(polymere, 'a')).size();
        for (char c = 'a'; c <= 'z'; c++) {
            String newPolymere = removeChar(polymere, c);
            result = Math.min(reducePolymere(newPolymere).size(), result);
        }
        return result;
        /*
        String reducedPolymere = linkedListToString(reducePolymere(polymere));
        int[] scores = calculateLetterScores(reducedPolymere);
        int bestScore = 0;
        for (int i = 0; i < scores.length; i++) {
            bestScore = Math.max(bestScore, scores[i]);
        }
        return reducedPolymere.length() - bestScore;*/
    }

    private static String removeChar(String polymere, char charToRemove) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < polymere.length(); i++) {
            if (Character.toLowerCase(polymere.charAt(i)) != charToRemove) {
                sb.append(polymere.charAt(i));
            }
        }
        return sb.toString();
    }

    private static int[] calculateLetterScores(String polymere) {
        int[] scores = new int[26];
        for (int i = 0; i < polymere.length(); i++) {
            int indexInScores = (int) Character.toLowerCase(polymere.charAt(i)) - (int) 'a';
            if (indexInScores >= 0) {
                scores[indexInScores] += calculatePotentialValueFromIndex(polymere, i);
            }
        }
        return scores;
    }

    private static int calculatePotentialValueFromIndex(String polymere, int index) {
        char currentChar = polymere.charAt(index);
        //System.out.println(currentChar + " " + index);
        int score = 1;
        int index1 = index - 1;
        int index2 = index + 1;
        while (index2 < polymere.length() && areCharactersEqualIgnoringCase(polymere.charAt(index2), currentChar)) {
            index2++;
            score++;
        }

        while (index1 >= 0 && index2 < polymere.length() && areCharReacting(polymere.charAt(index1), polymere.charAt(index2))) {
            index2++;
            index1--;
            score++;
        }

       // System.out.println(score);
        return score;
    }

    // common methods
    private static boolean areCharReacting(char char1, char char2) {
        return (char1 != char2 && areCharactersEqualIgnoringCase(char1, char2));
    }

    private static boolean areCharactersEqualIgnoringCase(char char1, char char2) {
        return Character.toUpperCase(char1) == Character.toUpperCase(char2);
    }


    private static LinkedList<Character> reducePolymere(String polymere) {
        LinkedList<Character> charStack = new LinkedList<>();
        char currentChar = polymere.charAt(0);
        int index = 0;
        while (index < polymere.length() - 1) {
            index++;
            if (areCharReacting(currentChar, polymere.charAt(index))) {
                currentChar = charStack.size() > 0 ? charStack.poll() : '\u0000';
            } else {
                charStack.push(currentChar);
                currentChar = polymere.charAt(index);
            }
        }
        return charStack;
    }

    private static String linkedListToString(LinkedList<?> linkedList) {
        StringBuilder sb = new StringBuilder();
        while (!linkedList.isEmpty()) {
            sb.append(linkedList.pollLast());
        }
        return sb.toString();
    }

}