/*
 * @test
 * @summary Witup-style test for StringBuilder.deleteCharAt observable behavior
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testReturnsSameInstance();
        testDeleteMiddleCharacter();
        testDeleteFirstAndLastCharacter();
        testDeleteSingleCharacterLeavesEmpty();
        testOutOfRangeIndexThrowsAndLeavesBuilderUnchanged();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReturnsSameInstance() {
        StringBuilder sb = new StringBuilder("abc");
        StringBuilder result = sb.deleteCharAt(1);
        check(result == sb, "deleteCharAt must return this");
    }

    static void testDeleteMiddleCharacter() {
        StringBuilder sb = new StringBuilder("abcde");
        sb.deleteCharAt(2);
        check(sb.toString().equals("abde"), "expected abde but was " + sb);
        check(sb.length() == 4, "expected length 4 but was " + sb.length());
    }

    static void testDeleteFirstAndLastCharacter() {
        StringBuilder sb = new StringBuilder("xyz");
        sb.deleteCharAt(0);
        check(sb.toString().equals("yz"), "after deleting first expected yz but was " + sb);
        sb.deleteCharAt(1);
        check(sb.toString().equals("y"), "after deleting last expected y but was " + sb);
    }

    static void testDeleteSingleCharacterLeavesEmpty() {
        StringBuilder sb = new StringBuilder("q");
        sb.deleteCharAt(0);
        check(sb.length() == 0, "expected empty builder but length was " + sb.length());
        check(sb.toString().equals(""), "expected empty string");
    }

    static void testOutOfRangeIndexThrowsAndLeavesBuilderUnchanged() {
        int[] badIndexes = {-1, 3, 4};
        for (int index : badIndexes) {
            StringBuilder sb = new StringBuilder("abc");
            boolean thrown = false;
            try {
                sb.deleteCharAt(index);
            } catch (StringIndexOutOfBoundsException e) {
                thrown = true;
            }
            check(thrown, "expected StringIndexOutOfBoundsException for index " + index);
            check(sb.toString().equals("abc"), "builder changed after failed deleteCharAt(" + index + ")");
        }

        StringBuilder empty = new StringBuilder();
        boolean thrownEmpty = false;
        try {
            empty.deleteCharAt(0);
        } catch (StringIndexOutOfBoundsException e) {
            thrownEmpty = true;
        }
        check(thrownEmpty, "expected StringIndexOutOfBoundsException on empty builder");
        check(empty.length() == 0, "empty builder must remain empty");
    }
}
