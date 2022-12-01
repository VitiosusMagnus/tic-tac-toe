import java.util.Scanner;

public class tictactoe {
    static int depthOfMinmax = 0;

    // main method
    public static void main(String[] args) {
        int result = 3;
        String[] table = { "0", "1", "2", "3", "4", "5", "6", "7", "8" };
        Scanner sc = new Scanner(System.in);

        while (result >= 3) {

            // user move
            pTable(table);
            System.out.println("take  a move");
            int pMove = sc.nextInt();

            while (table[pMove].equals("x") || table[pMove].equals("o")) {
                System.out.println("take  a move");
                pMove = sc.nextInt();
            }
            table[pMove] = "x";

            result = winTest(table);
            if (result == 0) {
                System.out.println("You win");
                break;
            }
            if (result == 2) {
                System.out.println("tied");
                break;
            }

            // minimax comp move
            table[bestMove(table)] = "o";

            System.out.println(depthOfMinmax + " possible ends");
            depthOfMinmax = 0;

            result = winTest(table);

            if (result == 1) {
                System.out.println("Comp wins try again");
                break;
            }
            if (result == 2) {
                System.out.println("tied");
                break;
            }
        }
        sc.close();
        pTable(table);

    }

    // trying all possible comp moves
    public static int bestMove(String[] table) {
        int score;
        int move = 0;
        int bestScore = -1000;
        int count = 0;
        while (count < 9) {
            if (table[count].equals(count + "")) {
                score = minMax(count, true, 0, table);
                if (score > bestScore) {
                    bestScore = score;
                    move = count;
                }
            }
            count++;
        }
        return move;
    }

    // this is minmax algorithm
    public static int minMax(int count, Boolean isMaximizing, int depth, String[] newTable) {
        int score = 1000;
        int bestScore = 1000;
        int result;
        int count5 = 0;
        String[] table = new String[9];
        while (count5 < 9) {
            table[count5] = newTable[count5];
            count5++;
        }

        if (isMaximizing) {

            if (depth == 0) {

                table[count] = "o";
                result = winTest(table);
                if (result == 1) {
                    score += 100;
                    depthOfMinmax++;
                    bestScore = Math.max(score, bestScore);
                } else {
                    bestScore = Math.max(score, minMax(count, false, depth + 1, table));
                }
                return bestScore;

            } else {// depth isnt zero
                int count2 = 0;
                while (count2 < 9) {
                    if (table[count2].equals(count2 + "")) {
                        table[count2] = "o";
                        result = winTest(table);
                        if (result == 1) {
                            score += 100;
                            bestScore = Math.max(score, bestScore);
                            depthOfMinmax++;
                        }
                        if (result == 2) {
                            score = score - depth;
                            bestScore = Math.max(score, bestScore);
                            depthOfMinmax++;
                        } else {
                            bestScore = Math.max(minMax(count2, false, depth + 1, table), score);
                        }
                        table[count2] = "" + count2;
                    }
                    count2++;
                }
                return bestScore - depth;
            }
        } else {// isMaximizing false
            int count3 = 0;
            while (count3 < 9) {
                if (table[count3].equals(count3 + "")) {
                    table[count3] = "x";
                    result = winTest(table);
                    if (result == 0) {
                        score -= 100;
                        depthOfMinmax++;
                        bestScore = Math.min(score, bestScore);
                    }
                    if (result == 2) {
                        score = score + depth;
                        depthOfMinmax++;
                        bestScore = Math.min(score, bestScore);
                    } else {
                        bestScore = Math.min(score, minMax(count3, true, depth + 1, table));
                    }
                    table[count3] = "" + count3;
                }
                count3++;
            }
            return bestScore + depth;
        }

    }

    // print table
    public static void pTable(String[] table) {
        System.out.print(
                table[0] + " | " + table[1] + " | " + table[2] + "\n" +
                        "---------\n" +
                        table[3] + " | " + table[4] + " | " + table[5] + "\n" +
                        "---------\n" +
                        table[6] + " | " + table[7] + " | " + table[8] + "\n");
    }

    // checks who win
    public static int winTest(String table[]) {
        String line = "";
        int count = 0;
        int result = 3;
        int draw = 0;
        while (count < 9) {
            if (table[count].equals("x") || table[count].equals("o")) {
                draw++;
            }
            count++;
        }
        count = 0;
        if (draw == 9) {
            result = 2;
        }

        while (count < 8) {
            switch (count) {
                case 0:
                    line = table[0] + table[1] + table[2];
                    break;
                case 1:
                    line = table[3] + table[4] + table[5];
                    break;
                case 2:
                    line = table[6] + table[7] + table[8];
                    break;
                case 3:
                    line = table[0] + table[4] + table[8];
                    break;
                case 4:
                    line = table[6] + table[4] + table[2];
                    break;
                case 5:
                    line = table[0] + table[3] + table[6];
                    break;
                case 6:
                    line = table[1] + table[4] + table[7];
                    break;
                case 7:
                    line = table[2] + table[5] + table[8];
                    break;
            }
            if (line.equals("xxx")) {
                result = 0;
            }
            if (line.equals("ooo")) {
                result = 1;
            }
            count++;
        }

        return result;
    }
}