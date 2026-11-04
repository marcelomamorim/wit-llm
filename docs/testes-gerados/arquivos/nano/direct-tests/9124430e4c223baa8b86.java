/*
 * @test
 * @summary Tests for StringLatin1.compareToUTF16 method.
 * @run main StringLatin1WitupTest
 */

import java.lang.StringLatin1;

public class StringLatin1WitupTest {

    public static void main(String[] args) {
        testCompareToUTF16EqualLengths();
        testCompareToUTF16DifferentLengths();
        testCompareToUTF16EmptyStrings();
        testCompareToUTF16DifferentContents();
    }

    static void testCompareToUTF16EqualLengths() {
        byte[] value = {0x41, 0x42, 0x43}; // "ABC"
        byte[] other = {0x41, 0x42, 0x43}; // "ABC"
        int result = StringLatin1.compareToUTF16(value, other, value.length, other.length);
        if (result != 0) {
            throw new AssertionError("Expected 0 for equal strings, got " + result);
        }
    }

    static void testCompareToUTF16DifferentLengths() {
        byte[] value = {0x41, 0x42}; // "AB"
        byte[] other = {0x41, 0x42, 0x43}; // "ABC"
        int result = StringLatin1.compareToUTF16(value, other, value.length, other.length);
        if (result >= 0) {
            throw new AssertionError("Expected negative result for value shorter than other, got " + result);
        }
        byte[] value2 = {0x41, 0x42, 0x43}; // "ABC"
        byte[] other2 = {0x41, 0x42}; // "AB"
        int result2 = StringLatin1.compareToUTF16(value2, other2, value2.length, other2.length);
        if (result2 <= 0) {
            throw new AssertionError("Expected positive result for value longer than other, got " + result2);
        }
    }

    static void testCompareToUTF16EmptyStrings() {
        byte[] empty = {};
        int result = StringLatin1.compareToUTF16(empty, empty, 0, 0);
        if (result != 0) {
            throw new AssertionError("Expected 0 for empty strings, got " + result);
        }
    }

    static void testCompareToUTF16DifferentContents() {
        byte[] value = {0x41, 0x42, 0x43}; // "ABC"
        byte[] other = {0x41, 0x42, 0x44}; // "ABD"
        int result = StringLatin1.compareToUTF16(value, other, value.length, other.length);
        if (result >= 0) {
            throw new AssertionError("Expected negative result for different contents, got " + result);
        }
        byte[] value2 = {0x41, 0x42, 0x44}; // "ABD"
        byte[] other2 = {0x41, 0x42, 0x43}; // "ABC"
        int result2 = StringLatin1.compareToUTF16(value2, other2, value2.length, other2.length);
        if (result2 <= 0) {
            throw new AssertionError("Expected positive result for different contents, got " + result2);
        }
    }
}
