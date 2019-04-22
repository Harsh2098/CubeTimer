package com.hmproductions.cubetimer.utils;

import java.util.Random;

public class ScrambleGenerator {

    private static final int BASIC_MOVES = 6;

    public static String generate3x3Scramble() {

        String[] moves = {
                "F", "R", "U", "D", "L", "B", "F'", "R'", "U'", "D'", "L'", "B'",
                "F2", "R2", "U2", "D2", "L2", "B2"};

        return generateScramble(moves, 20);
    }

    public static String generate4x4Scramble() {

        String[] moves = {
                "F", "R", "U", "D", "L", "B", "F'", "R'", "U'", "D'", "L'", "B'",
                "Fw", "Rw", "Uw", "Dw", "Lw", "Bw", "F2", "R2", "U2", "D2", "L2", "B2",
                "Fw'", "Rw'", "Uw'", "Dw'", "Lw'", "Bw'", "Fw2", "Rw2", "Uw2", "Dw2", "Lw2", "Bw2"};

        return generateScramble(moves, 45);
    }

    public static String generate5x5Scramble() {

        String[] moves = {
                "F", "R", "U", "D", "L", "B", "F'", "R'", "U'", "D'", "L'", "B'",
                "Fw", "Rw", "Uw", "Dw", "Lw", "Bw", "F2", "R2", "U2", "D2", "L2", "B2",
                "Fw'", "Rw'", "Uw'", "Dw'", "Lw'", "Bw'", "Fw2", "Rw2", "Uw2", "Dw2", "Lw2", "Bw2"};

        return generateScramble(moves, 62);
    }

    private static String generateScramble(String[] moves, int length) {
        StringBuilder scrambleBuilder = new StringBuilder();
        Random random = new Random();
        int[] arr = new int[length];
        int temp;

        arr[0] = Math.abs(random.nextInt() % moves.length);
        scrambleBuilder.append(moves[arr[0]]);

        for (int i = 1; i < length; ++i) {

            do {
                temp = Math.abs(random.nextInt() % moves.length);
            } while (temp % BASIC_MOVES == arr[i - 1] % BASIC_MOVES);

            arr[i] = temp;
        }

        for (int i = 1; i < arr.length; ++i) {
            scrambleBuilder.append(" ").append(moves[arr[i]]);
        }

        return scrambleBuilder.toString();
    }
}
