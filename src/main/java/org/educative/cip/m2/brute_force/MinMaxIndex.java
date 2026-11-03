package org.educative.cip.m2.brute_force;

/**
 * This class provides methods to find the index of the maximum and minimum values in an array.
 * It includes a main method to demonstrate the functionality of these methods.
 */
public class MinMaxIndex {
    /**
     * This method finds the index of the maximum value in an array.
     * It iterates through the array, comparing each element to the current maximum value.
     * If a larger value is found, it updates the maximum value and its index.
     *
     * @param array      The input array of integers.
     * @param array_size The size of the input array.
     * @return The index of the maximum value in the array.
     */
    public static int maximum_index(int array[], int array_size) {
        int max_index = 0; // initialize index to 0
        int max_val = Integer.MIN_VALUE; // set max value to least number
        for (int i = 0; i < array_size; i++) // loop through each element in array
        {
            if (max_val < array[i]) {
                max_val = array[i];
                max_index = i;
            }
        }
        return max_index;
    }

    /**
     * This method finds the index of the minimum value in an array.
     * It iterates through the array, comparing each element to the current minimum value.
     * If a smaller value is found, it updates the minimum value and its index.
     *
     * @param array      The input array of integers.
     * @param array_size The size of the input array.
     * @return The index of the minimum value in the array.
     */
    public static int minimum_index(int array[], int array_size) {
        int min_index = 0; // initialize index to 0
        int min_val = Integer.MAX_VALUE; // set min value to largest number
        for (int i = 0; i < array_size; i++) // loop through each element in array
        {
            if (min_val > array[i]) {
                min_val = array[i];
                min_index = i;
            }
        }
        return min_index;
    }

    /**
     * Driver method to demonstrate the functionality of the maximum_index and minimum_index methods.
     * The main method demonstrates the functionality of the maximum_index and minimum_index methods.
     * It initializes an array of integers, finds the indices of the maximum and minimum values,
     * and prints the results to the console.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String args[]) {
        int array_size = 9;
        int nums[] = {2,4,6,3,5,7,9,1,8};

        int max_index = maximum_index(nums, array_size);
        System.out.println("The MAX number " + nums[max_index] + " is found at index " + max_index);
        int min_index = minimum_index(nums, array_size);
        System.out.println("The MIN number " + nums[min_index] + " is found at index " + min_index);
    }
}