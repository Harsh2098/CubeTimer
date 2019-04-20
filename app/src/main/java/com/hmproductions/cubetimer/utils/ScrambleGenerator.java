package com.hmproductions.cubetimer.utils;

import java.util.Random;

public class ScrambleGenerator {

    private static final int BASIC_MOVES = 6;

    public static String generate4x4Scramble() {

        String[] moves = {
                "F", "R", "U", "D", "L", "B", "F'", "R'", "U'", "D'", "L'", "B'",
                "Fw", "Rw", "Uw", "Dw", "Lw", "Bw", "F2", "R2", "U2", "D2", "L2", "B2",
                "Fw2", "Rw2", "Uw2", "Dw2", "Lw2", "Bw2"};

        return generateScramble(moves, 45);
    }

    private static String generateScramble(String moves[], int length) {
        StringBuilder scrambleBuilder = new StringBuilder();
        Random random = new Random();
        int[] arr = new int[length];
        int temp;

        arr[0] = random.nextInt() % moves.length;
        scrambleBuilder.append(moves[arr[0]]);

        for (int i = 1; i < arr.length; ++i) {

            do {
                temp = random.nextInt() % moves.length;
            } while (temp % BASIC_MOVES == arr[i - 1] % BASIC_MOVES);

            arr[i] = temp;
        }

        for (int num : arr) {
            scrambleBuilder.append(" ").append(moves[num]);
        }

        return scrambleBuilder.toString();
    }
}