public class ChronalCharge {
    public static void main(String[] args) {
        // System.out.println(getLargestPower(8));
        System.out.println(getLargestPower(18));
        System.out.println(getLargestPowerBrute(18));
        // System.out.println(getLargestPower(42));
        // System.out.println(getLargestPower(3214));
        // System.out.println(calcCell(21, 61, 42));

        // System.out.println(getLargestPowerPart2(113));
        // System.out.println(getLargestPowerPart2(119));
        // System.out.println(getLargestPowerPart2(3214));
    }

    private static String getLargestPower(int gridSerialNumber) {
        long[][] grid = buildGrid(gridSerialNumber);
        long[][] integral = buildIntegralGrid(grid);
        long[] coords = findMax(integral);
        return String.format("%d,%d", coords[0] + 1, coords[1] + 1);

    }

    

    private static String getLargestPowerBrute(int gridSerialNumber) {
        long[][] grid = buildGrid(gridSerialNumber);
        long[] coords = findMaxBrute(grid);
        return String.format("%d,%d", coords[0] + 1, coords[1] + 1);

    }

    private static void printGrid(long[][] grid) {
        printGrid(0, 0, grid.length, grid);
    }

    private static void printGrid(int x, int y, int size, long[][] grid) {
        for (int i = x - 1; i < x + size - 1; i++) {
            for (int j = y - 1; j < y + size - 1; j++) {
                System.out.print(grid[i][j] + " | ");
            }
            System.out.println();
        }
    }

    private static long[][] buildGrid(int gridSerialNumber) {
        long[][] grid = new long[300][];
        for (int i = 0; i < grid.length; i++) {
            grid[i] = new long[300];
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = calcCell(i + 1, j + 1, gridSerialNumber);
                // grid[i][j] += i > 0?grid[i - 1][j]:0;
                // grid[i][j] += j > 0?grid[i][j - 1]:0;
            }
        }
        return grid;
    }

    private static long[][] buildIntegralGrid(long[][] grid) {
        long[][] integral = new long[300][];
        for (int i = 0; i < integral.length; i++) {
            integral[i] = new long[300];
           
            for (int j = 0; j < integral[i].length; j++) {
                if (i > 0 && j > 0) {
                    integral[i][j] = grid[i][j] + integral[i - 1][j] + integral[i][j - 1] - integral[i - 1][j - 1]  ;
                } else if (i > 0) {
                    integral[i][j] = grid[i][j] + integral[i - 1][j];
                } else if (j > 0) {
                    integral[i][j] = grid[i][j] + integral[i][j - 1];
                }else {
                    integral[i][j] = grid[i][j];
                }
            //     integral[i][j] = grid[i][j];
            // //     //integral[i][j] += sum;
               
            //     integral[i][j] +=  i > 0 ? integral[i - 1][j] : 0;
            //     integral[i][j] += j > 0 ? integral[i][j - 1] : 0;
            //     integral[i][j] += (i > 0 && j > 0) ? integral[i][j - 1] + integral[i - 1][j] - integral[i - 1][j - 1]
            //             : 0;
            }
        }
        return integral;
    }

    private static long[] findMax(long[][] integral) {
        long max = 0;
        int indexI = 0;
        int indexJ = 0;

        for (int i = 3; i < integral.length; i++) {
            for (int j = 3; j < integral[i].length; j++) {
                long value = integral[i][j];
                value -= i > 3 ? integral[i - 3][j] : 0;
                value -= j > 3 ? integral[i][j - 3] : 0;
                value += (i < 3 && j > 3) ? integral[i - 3][j - 3] : 0;
                if (value > max) {
                    max = value;
                    indexI = i;
                    indexJ = j;
                }
            }
        }
        return new long[] { indexI, indexJ };
    }

    
    private static long[] findMaxBrute(long[][] integral) {
        // int max = 0;
        // int indexI = 0;
        // int indexJ = 0;

        // for (int i = 0; i < integral.length - 3; i++) {
        // for (int j = 0; j < integral[i].length - 3; j++) {
        // int value = calCValueCell(i + 1, j + 1, integral);

        // if (value > max) {
        // max = value;
        // indexI = i;
        // indexJ = j;
        // }
        // }
        // }
        // // for (int i = 3; i < integral.length; i++) {
        // // for (int j = 3; j < integral[i].length; j++) {
        // // int value = integral[i][j];
        // // value -= i > 3 ? integral[i - 3][j] : 0;
        // // value -= j > 3 ? integral[i][j - 3] : 0;
        // // value += (i < 3 && j > 3) ? integral[i - 3][j - 3] : 0;
        // // if (value > max) {
        // // max = value;
        // // indexI = i;
        // // indexJ = j;
        // // }
        // // }
        // // }
        return findMax(integral, 3, 3);
    }
    
    private static int calcCell(int x, int y, int gridSerialNumber) {
        int rackId = x + 10;
        int cell = rackId * y;
        cell += gridSerialNumber;
        cell *= rackId;
        return getHundreds(cell) - 5;
    }

    private static int getHundreds(int num) {
        return (num / 100) % 10;
    }

    // step 2

    private static String getLargestPowerPart2(int gridSerialNumber) {
        long[][] grid = buildGrid(gridSerialNumber);
        long[] coords = findMax(grid, 1, 300);
        return String.format("%d,%d,%d", coords[0] + 1, coords[1] + 1, coords[2]);
    }

    private static long[] findMax(long[][] integral, int minSize, int maxSize) {
        int max = 0;
        int indexI = 0;
        int indexJ = 0;
        int indexSize = 0;
        for (int size = minSize; size <= maxSize; size++) {
            for (int i = 0; i < integral.length - size; i++) {
                for (int j = 0; j < integral[i].length - size; j++) {
                    int value = calCValueCell(i + 1, j + 1, size, integral);

                    if (value > max) {
                        max = value;
                        indexI = i;
                        indexJ = j;
                        indexSize = size;
                    }
                }
            }
        }
        return new long[] { indexI, indexJ, indexSize };
    }

    private static int calCValueCell(int x, int y, int size, long[][] grid) {
        int res = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                res += grid[i + x - 1][j + y - 1];
            }
        }
        return res;

    }

}