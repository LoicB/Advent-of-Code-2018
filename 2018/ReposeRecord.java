import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ReposeRecord {
    private final static int GUARD_ID_INDEX_START = 26;
    private final static int GUARD_ID_INDEX_END = 28;
    private final static int GUARD_TIME_INDEX_START = 15;
    private final static int GUARD_TIME_INDEX_END = 17;

    public static void main(String[] args) {
        System.out.println(findGuardSleepign(
                new String[] { "[1518-11-01 00:00] Guard #10 begins shift", "[1518-11-01 00:05] falls asleep",
                        "[1518-11-01 00:25] wakes up", "[1518-11-01 00:30] falls asleep", "[1518-11-01 00:55] wakes up",
                        "[1518-11-01 23:58] Guard #99 begins shift", "[1518-11-02 00:40] falls asleep",
                        "[1518-11-02 00:50] wakes up", "[1518-11-03 00:05] Guard #10 begins shift",
                        "[1518-11-03 00:24] falls asleep", "[1518-11-03 00:29] wakes up",
                        "[1518-11-04 00:02] Guard #99 begins shift", "[1518-11-04 00:36] falls asleep",
                        "[1518-11-04 00:46] wakes up", "[1518-11-05 00:03] Guard #99 begins shift",
                        "[1518-11-05 00:45] falls asleep", "[1518-11-05 00:55] wakes up" }

        ));

        System.out.println(findGuardSleepign(FileToArray.readFile("./resources/ReposeRecord")));
    }

    private static int findGuardSleepign(String[] records) {
        int index = 0;
        int guardId = 0;
        Arrays.sort(records);
        Map<Integer, int[]> guardMapper = new HashMap<>();
        int[] currentTime = null;
        while (index < records.length) {
            String record = records[index];
            if (record.endsWith("begins shift")) {
                index++;
                guardId = Integer.parseInt(record.substring(GUARD_ID_INDEX_START, GUARD_ID_INDEX_END));
                if (!guardMapper.containsKey(guardId)) {
                    guardMapper.put(guardId, new int[60]);
                }
                currentTime = guardMapper.get(guardId);
            } else if (record.endsWith("falls asleep")) {
                int from = Integer.parseInt(record.substring(GUARD_TIME_INDEX_START, GUARD_TIME_INDEX_END));
                int to = Integer.parseInt(records[index + 1].substring(GUARD_TIME_INDEX_START, GUARD_TIME_INDEX_END));
               // System.out.println("! fron " + from + " to "+ to);
                
                index += 2;
                for (int i = from; i < to; i++) {
                    currentTime[i]++;
                }
            }
        }
        System.out.println(guardMapper.size());
        //System.out.println(Arrays.toString(guardMapper.get(10)));
        int sleepingGuardId = getMostSleepingGuard(guardMapper);
        System.out.println(sleepingGuardId);
        System.out.println(Arrays.toString(guardMapper.get(sleepingGuardId)));
        int[] minutes = guardMapper.get(sleepingGuardId);
        return sleepingGuardId * findMostSleptMinute(minutes);
    }

    private static int getMostSleepingGuard(Map<Integer, int[]> guardMapper) {
        int maxGuardValue = 0;
        int maxGuardKey = 0;
        for (int guardKey : guardMapper.keySet()) {
            int time = countGuardSleepingTime(guardMapper.get(guardKey));
         //   System.out.print("["+guardKey+"] "+time);
            if (maxGuardValue < time) {
                maxGuardValue = time;
                maxGuardKey = guardKey;
            }
        }
//System.out.println("maxGuardKey "+maxGuardKey);
        return maxGuardKey;
    }

    private static int countGuardSleepingTime(int[] minutes) {
        int number = 0;
        for (int i = 0; i < minutes.length; i++) {
            number += minutes[i];
        }
        return number;
    }

    private static int findMostSleptMinute(int[] minutes) {
        int max = 0;
        int index = 0;
        for (int i = 0; i < minutes.length; i++) {
            // max = Math.max(max, minutes[i]);
            if (max < minutes[i]) {
                max = minutes[i];
                index = i;
            }
        }
        return index;
    }
}

