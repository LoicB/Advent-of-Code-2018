public class ReposeRecord {
    // [1518-11-01 00:05] falls asleep
    private static final Pattern p = Pattern.compile("^[([0-9-]+) @ ([0-9):]+ (.+)]");
    //([0-9]+) @ ([0-9]+),([0-9]+): ([0-9]+)x([0-9]+)");

    public static void main(String[] args) {

    }

    private static void analyseLogs(String[] logs){
        Fabric fabric = new Fabric();
        for (int i = 0; i < claims.length; i++) {
            Matcher matcher = p.matcher(claims[i]);
            if (matcher.find()) {
                String date = matcher.group(1);
                int minute = Integer.parseInt(matcher.group(2).substring(2));
                String action = matcher.group(3);
                fabric.addRectangle(x, y, width, heigh);
            }
        }
    }

    private static class Guard {
        private final int id;
        private final HashMap<String, int[]> minutesAsleep = new HashMap<String, int[]>();
        private final String[] guardEvent = new String[60];
        private final int numberOfShift;

        private Guard(String id) {
            this.id = id;
            numberOfShift = 0;
        }

    }
}