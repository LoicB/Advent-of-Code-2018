import java.util.HashSet;
import java.util.Set;

public class ChronalCalibration {
    public static void main(String[] argv) {
        if (argv != null && argv.length > 0) {
            System.out.println(calculateFrequency(argv));
        }
        System.out.println(calculateFrequency(new String[] { "+1", "+1", "+1" }));
        System.out.println(calculateFrequency(new String[] { "+1", "+1", "-2" }));
        System.out.println(calculateFrequency(new String[] { "-1", "-2", "-3" }));

        if (argv != null && argv.length > 0) {
            System.out.println(calculateFirstFrequencyReachedTwice(argv));
        }
        System.out.println(calculateFirstFrequencyReachedTwice(new String[] { "+1", "-1" }));
        System.out.println(calculateFirstFrequencyReachedTwice(new String[] { "+3", "+3", "+4", "-2", "-4" }));
        System.out.println(calculateFirstFrequencyReachedTwice(new String[] { "-6", "+3", "+8", "+5", "-6" }));
        System.out.println(calculateFirstFrequencyReachedTwice(new String[] { "+7", "+7", "-2", "-7", "-4" }));
    }

    private static int calculateFrequency(String[] changes) {
        int finalValue = 0;
        for (String change : changes) {
            finalValue += Integer.parseInt(change);
        }
        return finalValue;
    }

    private static int calculateFirstFrequencyReachedTwice(String[] changes) {
        Set<Integer> values = new HashSet<>();
        int finalValue = 0;
        for (int i = 0; values.add(finalValue); i++) {
            finalValue += Integer.parseInt(changes[i % changes.length]);
        }
        return finalValue;
    }

}