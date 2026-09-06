package org.educative.cip.m2.two_pointers;

import java.util.HashMap;
import java.util.Map;
/*
    * A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
    * For example, "69", "88", and "818" are all strobogrammatic numbers.
    *
    * This class provides two methods to check if a given string representation of a number is strobogrammatic.
    *
    * Method 1: isStrobogrammatic
    * - Uses a HashMap to store valid strobogrammatic digit pairs.
    * - Compares characters from the start and end of the string moving towards the center.
    *
    * Method 2: isStrobogrammatic2
    * - Uses a string containing valid strobogrammatic pairs for comparison.
    * - Similar logic to the first method but checks pairs using string containment.
    *
 * Time Complexity: O(n) - We traverse the string once to check if it's strobogrammatic.
 * Space Complexity: O(1) - We use a constant amount of space for the dictionary.
 */
public class StrobogrammaticNumber {
    private static Map<Character, Character> dict = new HashMap<>();
    static {
        dict.put('0', '0');
        dict.put('1', '1');
        dict.put('6', '9');
        dict.put('8', '8');
        dict.put('9', '6');
    }


    public static boolean isStrobogrammatic (String num)
    {
        // Replace this placeholder return statement with your code
        int left = 0;
        int right = num.length() - 1;
        while (left <= right) {
            if (!dict.containsKey(num.charAt(left)) || dict.get(num.charAt(left)) != num.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    public static boolean isStrobogrammatic2 (String num)
    {
        // Replace this placeholder return statement with your code
        int left = 0;
        int right = num.length() - 1;
        String strobogrammaticPairs = "00 11 69 88 96";
        while (left <= right) {
            String pair = "" + num.charAt(left) + num.charAt(right);
            if (!strobogrammaticPairs.contains(pair)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    // Driver code
    public static void main(String[] args) {
        String[] nums = {
                "609",
                "88",
                "962",
                "101",
                "123"
        };

        int i = 0;
        for (String num : nums) {
            System.out.println((i + 1) + ".\tnum: " + num);
            System.out.println("\n\tIs strobogrammatic: " + (isStrobogrammatic(num) ? "true" : "false"));
            System.out.println(new String(new char[100]).replace("\0", "-"));
            i++;
        }
    }

}
