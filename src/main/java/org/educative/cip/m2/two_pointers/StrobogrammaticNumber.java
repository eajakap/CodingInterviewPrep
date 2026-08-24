package org.educative.cip.m2.two_pointers;

import java.util.HashMap;
import java.util.Map;
/*
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
