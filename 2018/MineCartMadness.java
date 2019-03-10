import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class MineCartMadness {
    public static void main(String[] args) {

        System.out.println(findIntersection(FileToArray.readFileToCharArray("./resources/MimeCartMadness")));
       System.out.println(findIntersection(FileToArray.readFileToCharArray("./resources/MimeCartMadness_2")));

    }

    private static Point findIntersection(char[][] tracks) {
        displayCarts(tracks);
        List<Point> carts = extractCarts(tracks);
        System.out.println(carts);
        moveCarts(carts, tracks);
        displayCarts(tracks);
        // moveCarts(carts, tracks);
        // displayCarts(tracks);
        // moveCarts(carts, tracks);
        // displayCarts(tracks);
        // moveCarts(carts, tracks);
        // displayCarts(tracks);
        // moveCarts(carts, tracks);
        // displayCarts(tracks);
        // moveCarts(carts, tracks);
        // displayCarts(tracks);
        // moveCarts(carts, tracks);
        // displayCarts(tracks);
        // moveCarts(carts, tracks);
        // displayCarts(tracks);
        // moveCarts(carts, tracks);
        // displayCarts(tracks);
        // moveCarts(carts, tracks);
        // displayCarts(tracks);
        // moveCarts(carts, tracks);
        // displayCarts(tracks);
        // moveCarts(carts, tracks);
        // displayCarts(tracks);
        // moveCarts(carts, tracks);
        // displayCarts(tracks);
        // moveCarts(carts, tracks);
        // // displayCarts(tracks);

        // moveCarts(carts, tracks);
        // displayCarts(tracks);
        // return null;

        Optional<Point> intersection = Optional.empty();
        while (!intersection.isPresent()) {
            intersection = moveCarts(carts, tracks);
            Collections.sort(carts);
        }
        displayCarts(tracks);
        return intersection.get();
    }

    private static Optional<Point> moveCarts(List<Point> carts, char[][] tracks) {
        Optional<Point> result = Optional.empty();
        Set<Point> pointsSet = new HashSet<>();
        int index = 0;
        // carts.get(index).move(tracks);
        while (!result.isPresent() && index < carts.size()) {
            // pointsSet.remove(carts.get(index));
            carts.get(index).move(tracks);
            if (!pointsSet.add(carts.get(index))) {
                result = Optional.of(carts.get(index));
            }
            index++;
        }
        return result;
    }

    private static void displayCarts(char[][] tracks) {
        for (int i = 0; i < tracks.length; i++) {
            for (int j = 0; j < tracks[i].length; j++) {
                System.out.print(tracks[i][j]);
            }
            System.out.println();
        }
    }

    private static List<Point> extractCarts(char[][] tracks) {
        List<Point> carts = new ArrayList<>();
        for (int i = 0; i < tracks.length; i++) {
            for (int j = 0; j < tracks[i].length; j++) {
                if (isItCart(tracks[i][j])) {
                    carts.add(new Point(i, j, tracks[i][j]));
                }
            }
        }
        Collections.sort(carts);
        return carts;
    }

    private static boolean isItCart(char c) {
        return (c == '<' || c == '>' || c == 'v' || c == '^');
    }

    private final static char LEFT = '<';
    private final static char UP = '^';
    private final static char DOWN = 'v';
    private final static char RIGHT = '>';


    private static class Point implements Comparable<Point> {
        private int x;
        private int y;
        private char cart;
        private char lastCart;
        private Direction direction = Direction.LEFT;

        public String toString() {
            return y + ", " + x + " -> " + cart;
        }

        private Point(int x, int y, char cart) {
            this.x = x;
            this.y = y;
            this.cart = cart;
            this.lastCart = cart;
        }

        private boolean move(char[][] tracks) {
            // System.out.println(this);
            int newX = x;
            int newY = y;
            char originalChar = lastCart;
            switch (cart) {
            case '>':
                newY++;
                originalChar = '-';
                break;
            case '<':
                newY--;
                originalChar = '-';
                break;
            case 'v':
                newX++;
                originalChar = '|';
                break;
            case '^':
            default:
                newX--;
                originalChar = '|';
                break;
            }
            char currentChar = tracks[newX][newY];
            if (currentChar == '+') {
                if (direction == Direction.LEFT) {
                    cart = turnLeft(cart);
                } else if (direction == Direction.RIGHT) {
                    cart = turnRight(cart);
                }
                passIntersection();
            } else if (currentChar == '/') {
                cart = turnSlash(cart);
            } else if (currentChar == '\\') {
                cart = turnAntiSlash(cart);
            }

            tracks[x][y] = isItCart(lastCart)?originalChar:lastCart;
            lastCart = tracks[newX][newY];
            tracks[newX][newY] = cart;
            x = newX;
            y = newY;

           // System.out.println(this);
            return true;
        }

        private char turnLeft(char cart) {
            switch (cart) {
            case LEFT:
                return DOWN;
            case UP:
                return LEFT;
            case DOWN:
                return RIGHT;
            default:
            case RIGHT:
                return UP;
            }

        }

        private char turnRight(char cart) {
            switch (cart) {
            case LEFT:
                return UP;
            case UP:
                return RIGHT;
            case DOWN:
                return LEFT;
            default:
            case RIGHT:
                return DOWN;
            }

        }

        private char turnSlash(char cart) {
            switch (cart) {
            case LEFT:
                return DOWN;
            case UP:
                return RIGHT;
            case DOWN:
                return LEFT;
            default:
            case RIGHT:
                return UP;
            }

        }

        private char turnAntiSlash(char cart) {
            switch (cart) {
            case LEFT:
                return UP;
            case UP:
                return LEFT;
            case DOWN:
                return RIGHT;
            default:
            case RIGHT:
                return DOWN;
            }

        }

        private void passIntersection() {
            Direction nextDirection;
            switch (direction) {
            case LEFT:
                nextDirection = Direction.STRAIGHT;
                break;
            case STRAIGHT:
                nextDirection = Direction.RIGHT;
                break;
            default:
            case RIGHT:
                nextDirection = Direction.LEFT;
                break;
            }
            direction = nextDirection;
        }

        public int compareTo(Point point) {
            int diff = x - point.x;
            if (diff == 0) {
                diff = y - point.y;
            }
            return diff;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (x != 0 ? x : 1237);
            result = prime * result + y;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Point other = (Point) obj;

            return this.x == other.x && this.y == other.y;
        }
    }

    private enum Direction {
        LEFT, STRAIGHT, RIGHT;
    }

}