import java.util.HashSet;
import java.util.Set;

public class InventoryManagementSystem {
    public static void main(String[] args) {
        System.out.println(calculateCheckSum(
                new String[] { "abcdef", "bababc", "abbcde", "abcccd", "aabcdd", "abcdee", "ababab" }));

        System.out.println(calculateCheckSum(FileToArray.readFile("./resources/InvetoryManagementSystem")));


        System.out.println(findCorrectBoxIDs(
                new String[] { "abcde", "fghij", "klmno", "pqrst", "fguij", "axcye", "wvxyz" }));

        System.out.println(findCorrectBoxIDs(FileToArray.readFile("./resources/InvetoryManagementSystem")));
   


    }

    // Step 1
    private static int calculateCheckSum(String[] ids) {
        int twoOccr = 0;
        int threeOccr = 0;
        for (String id : ids) {
            int[] charCount = countLetter(id);
            var counts = getDifferenceCount(charCount);
            if (counts.contains(2)) {
                twoOccr++;
            }
            if (counts.contains(3)) {
                threeOccr++;
            }
        }
        return threeOccr * twoOccr;
    }

    private static Set<Integer> getDifferenceCount(int[] charCount) {
        Set<Integer> differentCount = new HashSet<>();
        for (int i = 0; i < charCount.length; i++) {
            differentCount.add(charCount[i]);
        }
        return differentCount;
    }

    private static int[] countLetter(String id) {
        int[] charCount = new int[26];
        for (int i = 0; i < id.length(); i++) {
            int index = (int) id.charAt(i) - (int) 'a';
            charCount[index]++;
        }
        return charCount;
    }

    // Step 2
    private static String findCorrectBoxIDs(String[] ids) {
        int maximumDistance = 0;
        int indexI = 0;
        int indexJ = 0;
        for (int i = 0; i < ids.length; i++) {
            for (int j = i + 1; j < ids.length; j++) {
                int distance = calculateDistance(ids[i], ids[j], maximumDistance);
                if (distance > maximumDistance) {
                    indexI = i;
                    indexJ = j;
                    maximumDistance = distance;
                }
            }
        }
        return commonString(ids[indexI], ids[indexJ]);
    }

    private static int calculateDistance(String str1, String str2, int currentMax) {
        int distance = str1.length();
        for (int i = 0; i < str1.length() && distance > currentMax; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                distance--;
            }
        }
        return distance;
    }

    private static String commonString(String str1, String str2) {
        var commonStringBuilder = new StringBuilder();
        for (int i = 0; i < str1.length(); i++) {
            if (str1.charAt(i) == str2.charAt(i)) {
                commonStringBuilder.append(str1.charAt(i));
            }
        }
        return commonStringBuilder.toString();

    }
}