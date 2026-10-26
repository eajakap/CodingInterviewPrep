package org.educative.cip.m2.two_pointers;

/**
 * Given two non-negative integers num1 and num2 represented as strings, return the sum of num1 and num2. Since the sum can be very large, return it as a string.
 * Add digit by digit from the right, track the carry and avoid overflow - the inputs can be far longer than any integer type.
 *
 * 1 <= num1.length, num2.length <= 5 * 10^5
 */
public class DigitStringsSum {
    private static int validate(String num) {
        int number = 0;
        int length = num.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(num.charAt(i))) {
                return -1; // Invalid character found
            }
            // skip leading zeros
            if (num.charAt(i) != '0') {
                number = number * 10 + (num.charAt(i) - '0');
            }
        }
        return number;
    }

    public static String digitStringsSum2(String num1, String num2) {
        int n1 = validate(num1);
        int n2 = validate(num2);
        if (n1 == -1 || n2 == -1) {
            throw new IllegalArgumentException("Input strings must be valid non-negative integers.");
        }
        return String.valueOf(n1 + n2);
    }

    private static String skipNonDigitCharsAndLeadingZeros(String num1) {
        int i = 0;
        while (i < num1.length() && (!Character.isDigit(num1.charAt(i)) || num1.charAt(i) == '0')) {
            i++;
        }
        return num1.substring(i);
    }

    public static String digitStringsSum(String num1, String num2) {
        String s1 = skipNonDigitCharsAndLeadingZeros(num1);
        String s2 = skipNonDigitCharsAndLeadingZeros(num2);
        // At this point, s1 and s2 are the strings with non-digit characters and leading zeros removed.
        StringBuilder sb = new StringBuilder();
        int end1 = s1.length() - 1, end2 = s2.length() - 1, carry = 0;
        while (end1 >= 0 || end2 >= 0 || carry != 0) {
            if (end1 >= 0 && !Character.isDigit(s1.charAt(end1))) {
                return "-1"; // Invalid character found
            }
            if (end2 >= 0 && !Character.isDigit(s2.charAt(end2))) {
                return "-1"; // Invalid character found
            }
            int x = end1 >= 0 ? s1.charAt(end1) - '0' : 0;
            int y = end2 >= 0 ? s2.charAt(end2) - '0' : 0;
            int sum = x + y + carry;
            sb.append(sum % 10);
            carry = sum / 10;
            end1--;
            end2--;
        }
        return sb.length() == 0 ? "0" : sb.reverse().toString();
    }
    public static void main(String[] args) {
        String num1 = "000123", num2 = "000000486";
        System.out.println(digitStringsSum(num1, num2));
        System.out.println(digitStringsSum2(num1, num2));
    }
}
