package org.educative.cip.m2.two_pointers;

import java.util.*;
/**
 * Problem: Rotate Box 90 Degrees Clockwise with Gravity
 * Given a box represented as a 2D character array, where each cell is either empty ('.'), a stone ('#'),
 * or an obstacle ('*'), rotate the box 90 degrees clockwise and return the resulting box.
 * The stones will fall down due to gravity, and obstacles will block the stones from falling through them.
 * The stones in the horizontal direction will fall down to the lowest available empty slot in their respective columns.
 * The stone can fall down on another stone or an obstacle, but cannot fall through an obstacle.
 * Return the rotated box as a 2D character array.
 * Example:
 * Input: boxGrid = [['#', '.', '#', '*'], ['.', '#', '.', '#'], ['#', '.', '*', '#']]
 * Output: [['#', '.', '#'], ['.', '#', '.'], ['*', '.', '*'], ['#', '#', '#']]
 * Constraints:
 * 1 <= boxGrid.length, boxGrid[i].length <= 500
 * boxGrid[i][j] is either '.', '#', or '*'.
 *
 * Steps to Solve:
 * 1. Read and store m (number of rows) and n (number of columns) from boxGrid.
 * 2. Apply gravity to each row using two pointers. For each row from 0 to m-1
 *   , initialize emptySlot to n - 1 (the rightmost column, where a stone would land first).
 * 3. Simulate the gravity effect on the boxGrid:
 *   - For each row, traverse from right to left.
 *   - Keep track of the rightmost empty slot ('.') where a stone ('#') can fall.
 *   - If an obstacle ('*') is encountered, reset the rightmost empty slot to
 *      the left of the obstacle, as no stone can fall through an obstacle.
 *   - If a stone ('#') is encountered, move it to the rightmost empty slot and update the empty slot index.
 * 4. Rotate the boxGrid 90 degrees clockwise:
 *   - Create a new 2D array with dimensions [n][m].
 *   - Map each element from the original boxGrid to the new rotated array.
 * 5. Return the rotated array as the final result.
 *
 *  Time Complexity: O(m * n), where m is the number of rows and n is the number of columns in the boxGrid.
 *  We traverse through the boxGrid twice: once to simulate the gravity effect and once to rotate the box.
 *  Space Complexity: O(m * n), as we are creating a new 2D array to store the rotated box.
 */
public class RotateBox90DegreeClockwise {
    /**
     * Rotates the box represented as a 2D character array 90 degrees clockwise.
     * @param boxGrid The input 2D character array representing the box.
     * @return The rotated 2D character array after applying gravity and rotation.
     */
    public char[][] rotateTheBox(char[][] boxGrid) {
        int rows = boxGrid.length;
        int cols = boxGrid[0].length;

        // Step 1: Apply gravity to each row.
        // We scan each row from right to left, because stones fall toward the right side of the row.
        // 'emptySlot' marks the nearest available position where a stone can land.
        for (int row = 0; row < rows; row++) {
            int emptySlot = cols - 1;
            // Traverse the row from right to left.
            for (int col = cols - 1; col >= 0; col--) {
                if (boxGrid[row][col] == '*') {
                    // An obstacle blocks all stones below it in this row.
                    // So the next stone must land to the left of this obstacle.
                    emptySlot = col - 1;
                } else if (boxGrid[row][col] == '#') {
                    // A stone falls to the current rightmost empty position.
                    boxGrid[row][col] = '.';
                    boxGrid[row][emptySlot] = '#';
                    emptySlot--;
                }
                // If we see '.', we keep it as an empty space and do nothing.
            }
        }

        // Step 2: Rotate the box 90 degrees clockwise.
        // The new matrix has dimensions [cols][rows].
        // Original cell (row, col) moves to (col, rows - 1 - row) in the rotated matrix.
        char[][] rotated = new char[cols][rows];
        for (char[] r : rotated) {
            Arrays.fill(r, '.');
        }
        // Fill the rotated matrix with the transformed positions of stones and obstacles.
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                rotated[col][rows - 1 - row] = boxGrid[row][col];
            }
        }

        return rotated;
    }

    public static void main(String[] args) {
        RotateBox90DegreeClockwise sol = new RotateBox90DegreeClockwise();

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