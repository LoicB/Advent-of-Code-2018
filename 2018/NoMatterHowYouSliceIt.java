import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NoMatterHowYouSliceIt {
    private static final Pattern p = Pattern.compile("^#([0-9]+) @ ([0-9]+),([0-9]+): ([0-9]+)x([0-9]+)");

    public static void main(String[] args) {
        System.out.println(countOverlaps(new String[] { "#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 5,5: 2x2" }));
        

        System.out.println(countOverlaps(FileToArray.readFile("./resources/NoMatterHowYouSliceIt")));


        System.out.println(findIntactClaim(new String[] { "#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 5,5: 2x2" }));
        System.out.println(findIntactClaim(FileToArray.readFile("./resources/NoMatterHowYouSliceIt")));


    }

    // step 1
    private static int countOverlaps(String[] claims) {
        Fabric fabric = new Fabric();
        for (int i = 0; i < claims.length; i++) {
            Matcher matcher = p.matcher(claims[i]);
            if (matcher.find()) {
                int x = Integer.parseInt(matcher.group(2));
                int y = Integer.parseInt(matcher.group(3));
                int width = Integer.parseInt(matcher.group(4));
                int heigh = Integer.parseInt(matcher.group(5));
                fabric.addRectangle(x, y, width, heigh);
            }
        }
        return fabric.numberOfOverLap();
    }

    private static class Fabric {
        private static final int FABRIC_SIZE = 1000;
        private int[][] dots = new int[FABRIC_SIZE][];
        private int overlaps = 0;

        private Fabric() {
            for (int i = 0; i < FABRIC_SIZE; i++) {
                dots[i] = new int[FABRIC_SIZE];
            }
        }

        private void addRectangle(int x, int y, int width, int heigh) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < heigh; j++) {
                    if (dots[i + x][j + y] >= 1) {
                        overlaps++;
                    }
                    dots[i + x][j + y]++;
                }
            }
        }

        private int numberOfOverLap() {
            return overlaps;
        }
    }


    // step 2
    private static int findIntactClaim(String[] claims) {
        FabricInteract fabric = new FabricInteract();
        for (int i = 0; i < claims.length; i++) {
            Matcher matcher = p.matcher(claims[i]);
            if (matcher.find()) {
                int claimID = Integer.parseInt(matcher.group(1));
                int x = Integer.parseInt(matcher.group(2));
                int y = Integer.parseInt(matcher.group(3));
                int width = Integer.parseInt(matcher.group(4));
                int heigh = Integer.parseInt(matcher.group(5));
                fabric.checkIntact(x, y, width, heigh, claimID);
            }
        }
        return fabric.getIntact();
    }
    

    private static class FabricInteract {
        private static final int FABRIC_SIZE = 1000;
        private int[][] dots = new int[FABRIC_SIZE][];
        private Set<Integer> intactClaims = new HashSet<>();

        private FabricInteract() {
            for (int i = 0; i < FABRIC_SIZE; i++) {
                dots[i] = new int[FABRIC_SIZE];
            }
        }

        private void checkIntact(int x, int y, int width, int heigh, int claimID) {
            intactClaims.add(claimID);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < heigh; j++) {
                    if (dots[i + x][j + y] != 0) {
                        intactClaims.remove(claimID);
                        intactClaims.remove(dots[i + x][j + y]);
                    }
                    dots[i + x][j + y] = claimID;
                }
            }
        }

        private int getIntact() {
            return intactClaims.stream().findFirst().get();
        }
    }
}
