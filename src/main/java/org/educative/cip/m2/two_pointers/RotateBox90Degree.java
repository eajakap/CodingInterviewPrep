package org.educative.cip.m2.two_pointers;

import java.util.*;
/*
 *  Time Complexity: O(m * n), where m is the number of rows and n is the number of columns in the boxGrid.
 *  We traverse through the boxGrid twice: once to simulate the gravity effect and once to rotate the box.
 *  Space Complexity: O(m * n), as we are creating a new 2D array to store the rotated box.
 *  Given a box represented as a 2D character array, where each cell is either empty ('.'), a stone ('#'), or an
 *  obstacle ('*'), rotate the box 90 degrees clockwise and return the resulting box.
 */
public class RotateBox90Degree {
    public char[][] rotateTheBox(char[][] boxGrid) {
        int m = boxGrid.length; // rows
        int n = boxGrid[0].length; // columns in each row

        for (int row = 0; row < m; row++) {
            int emptySlot = n - 1;
            for (int col = n - 1; col >= 0; col--) {
                if (boxGrid[row][col] == '*') {
                    emptySlot = col - 1;
                } else if (boxGrid[row][col] == '#') {
                    boxGrid[row][col] = '.';
                    boxGrid[row][emptySlot] = '#';
                    emptySlot--;
                }
            }
        }

        char[][] rotated = new char[n][m];
        for (char[] row : rotated) Arrays.fill(row, '.');
        // Rotate the box 90 degrees clockwise - MAP the elements from the original boxGrid to the new rotated array
        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                rotated[col][m - 1 - row] = boxGrid[row][col];
            }
        }

        return rotated;
    }

    public static void main(String[] args) {
        RotateBox90Degree sol = new RotateBox90Degree();

        char[][] testCases[] = {
                {{'#'}, {'*'}, {'#'}, {'.'}}  ,

                {{'#', '.', '.', '#', '*'},
                        {'.', '#', '#', '.', '.'}},

                {{'#', '#', '#'},
                        {'#', '#', '#'},
                        {'#', '#', '#'}},

                {{'#', '*', '#', '.'},
                        {'.', '*', '.', '#'},
                        {'#', '.', '*', '#'}},

                {{'.', '#', '.'},
                        {'#', '.', '#'},
                        {'.', '.', '*'},
                        {'#', '#', '.'}},
        };

        for (int i = 0; i < testCases.length; i++) {
            char[][] boxGrid = testCases[i];
            char[][] gridCopy = new char[boxGrid.length][];
            for (int r = 0; r < boxGrid.length; r++) {
                gridCopy[r] = Arrays.copyOf(boxGrid[r], boxGrid[r].length);
            }
            char[][] result = sol.rotateTheBox(gridCopy);

            System.out.println((i + 1) + ".\tboxGrid:");
            for (char[] rowLine : boxGrid) {
                System.out.println("\t  " + Arrays.toString(rowLine));
            }
            System.out.println("\n\tResult (rotated):");
            for (char[] rowLine : result) {
                System.out.println("\t  " + Arrays.toString(rowLine));
            }
            System.out.println("-".repeat(100));
        }
    }
}