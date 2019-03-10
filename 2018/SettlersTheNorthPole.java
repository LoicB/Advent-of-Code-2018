import java.io.CharArrayReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SettlersTheNorthPole {

    private final static char OPEN = '.';
    private final static char TREES = '|';
    private final static char LUMBERYAD = '#';
    public static void main(String[] args) {
        System.out.println(partOne(FileToArray.readFileToCharArray("./resources/SettlersTheNorthPole")));
        System.out.println(partOne(FileToArray.readFileToCharArray("./resources/SettlersTheNorthPole2")));
        System.out.println(partOne(FileToArray.readFileToCharArray("./resources/SettlersTheNorthPole2"), 1000000000));
       
    }

    private static int partOne(char[][] acres) {
        return partOne(acres, 10);
    }

    

    private static int partOne(char[][] acres, int second) {
        //1000000000
        //  42200000
        // printAcres(acres);
        // printAcres(updateAcresWithBorders(acres));
        return waitNumberOfMinutes(acres, second);
    }

    private static char[][] updateAcresWithBorders(char[][] acres) {
        
        char[][] updatedAcres = new char[acres.length + 2][];
        updatedAcres[0] = new char[acres[0].length + 2];
        for (int i = 1; i < acres.length + 1; i++) {
            updatedAcres[i] = new char[acres[i - 1].length + 2];
            for (int j = 1; j < acres[i - 1].length + 1; j++) {
                updatedAcres[i][j] = acres[i - 1][j - 1];
            }
        }
        updatedAcres[acres.length + 1] = new char[acres[0].length + 2];
        return updatedAcres;
    }
    
    private static void printAcres(char[][] acres) {
        for (int i = 0; i < acres.length; i++) {
            for (int j = 0; j < acres[i].length; j++) {
                System.out.print(acres[i][j]);
            }
            System.out.println();
        }
    }

    private static int waitNumberOfMinutes(char[][] acres, int minutes) {
        char[][] nextAcres = new char[acres.length][];
        for (int i = 0; i < acres.length; i++) {
            nextAcres[i] = new char[acres[i].length];
            
        }
        for (int i = 0; i < minutes; i++) {
            if (i % 1000 == 0) {
                System.out.println(i);
            }
            nextMinute(acres, nextAcres);
            char[][] swap = acres;
            acres = nextAcres;
            nextAcres = swap;
          //  printAcres(currAcress);
        }
        return countAcres(TREES, acres) * countAcres(LUMBERYAD, acres);
    }

    private static void nextMinute(char[][] acres, char[][] nextAcres) {
        for (int i = 0; i < acres.length; i++) {
            nextAcres[i] = new char[acres[i].length];
            for (int j = 0; j < acres[i].length; j++) {
                nextAcres[i][j] = getNextAcres(acres, i, j);
            }
        }
    }

    private static char getNextAcres(char acre, char[] adjacentAcres) {
        char nextAcre;
        if (acre == OPEN) {
            if (countAcres(TREES, adjacentAcres) >= 3) {
                nextAcre = TREES;
            } else {
                nextAcre = acre;
            }
        } else if (acre == TREES) {
            if (countAcres(LUMBERYAD, adjacentAcres) >= 3) {
                nextAcre = LUMBERYAD;
            } else {
                nextAcre = acre;
            }
        } else if (acre == LUMBERYAD) {
            if (countAcres(LUMBERYAD, adjacentAcres) >= 1 && countAcres(TREES, adjacentAcres) >= 1) {
                nextAcre = LUMBERYAD;
            } else {
                nextAcre = OPEN;
            }
        } else {
            nextAcre = acre;
        }
        return nextAcre;
    }

    

    private static char getNextAcres(char[][] acres, int x, int y) {
        char acre = acres[x][y];
        char nextAcre;
        if (acre == OPEN) {
            if (countAcres(TREES, acres,x, y) >= 3) {
                nextAcre = TREES;
            } else {
                nextAcre = acre;
            }
        } else if (acre == TREES) {
            if (countAcres(LUMBERYAD, acres,x, y) >= 3) {
                nextAcre = LUMBERYAD;
            } else {
                nextAcre = acre;
            }
        } else if (acre == LUMBERYAD) {
            if (countAcres(LUMBERYAD, acres,x, y) >= 1 && countAcres(TREES, acres,x, y) >= 1) {
                nextAcre = LUMBERYAD;
            } else {
                nextAcre = OPEN;
            }
        } else {
            nextAcre = acre;
        }
        return nextAcre;
    }
    

    private static int countAcres(char acre, char[][] acres,  int x, int y) {
        int count = 0;
        count += (x > 0 && y > 0 && acre == acres[x - 1][y - 1]) ? 1 : 0;
        count += (x > 0 && acre == acres[x - 1][y]) ? 1 : 0;
        count += (x > 0 && y < acres[x - 1].length - 1 && acre == acres[x - 1][y + 1]) ? 1 : 0;

        count += (y > 0 && acre == acres[x][y - 1]) ? 1 : 0;
        count += (y < acres[x].length - 1 && acre == acres[x][y + 1]) ? 1 : 0;

        count += (x < acres.length - 1 && y > 0 && acre == acres[x + 1][y - 1]) ? 1 : 0;
        count += (x < acres.length - 1 && acre == acres[x + 1][y]) ? 1 : 0;
        count += (x < acres.length - 1 && y < acres[x + 1].length - 1 && acre == acres[x + 1][y + 1]) ? 1 : 0;
        return count;
    }

    
    private static int countAcres(char acre,  char[]  adjacentAcres) {
        int count = 0;
        for (Character adjAcre:adjacentAcres) {
            if (adjAcre == acre) {
                count++;
            }
        }
        return count;
    }

    

    private static int countAcres(char acre, char[][] acres) {
        int count = 0;
        for (int i = 0; i < acres.length; i++) {
            for (int j = 0; j < acres[i].length; j++) {
                if (acres[i][j] == acre) {
                    count++;
                }
            }
        }
        return count;
    }

    private static  char[]  getAdjacentAcres(int x, int y, char[][] acres) {
        char[]  adjacentAcres = new char[8];
        adjacentAcres[0] = acres[x - 1][y - 1];
        adjacentAcres[1] = acres[x - 1][y];
        adjacentAcres[2] = acres[x - 1][y + 1];
        
        adjacentAcres[3] = acres[x][y - 1];
        adjacentAcres[4] = acres[x][y];
        adjacentAcres[5] = acres[x][y + 1];
        
        adjacentAcres[6] = acres[x + 1][y - 1];
        adjacentAcres[7] = acres[x + 1][y];
        adjacentAcres[8] = acres[x + 1][y + 1];
        return adjacentAcres;
    }

}