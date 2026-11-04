/* @test    @summary Test for Character.codePointAt with boundary conditions    @run main CharacterWitupTest */

import java.lang.Character;

public class CharacterWitupTest {

    public static void main(String[] args) {
        testCodePointAtWithInvalidIndices();
        testCodePointAtWithValidIndices();
    }

    private static void testCodePointAtWithInvalidIndices() {
        char[] array = {'a', 'b', 'c'};
        int limit = 3;
        // index >= limit
        try {
            Character.codePointAt(array, 3, limit);
            System.out.println("Failed: Expected IndexOutOfBoundsException for index == limit");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Passed: Caught expected IndexOutOfBoundsException for index == limit");
        }
        // index > limit
        try {
            Character.codePointAt(array, 4, limit);
            System.out.println("Failed: Expected IndexOutOfBoundsException for index > limit");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Passed: Caught expected IndexOutOfBoundsException for index > limit");
        }
        // limit < 0
        try {
            Character.codePointAt(array, 0, -1);
            System.out.println("Failed: Expected IndexOutOfBoundsException for limit < 0");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Passed: Caught expected IndexOutOfBoundsException for limit < 0");
        }
        // limit > array.length
        try {
            Character.codePointAt(array, 0, 10);
            System.out.println("Failed: Expected IndexOutOfBoundsException for limit > array.length");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Passed: Caught expected IndexOutOfBoundsException for limit > array.length");
        }
    }

    private static void testCodePointAtWithValidIndices() {
        char[] array = {'a', '\uD83D', '\uDE00'}; // 'a', 😀 (surrogate pair)
        int limit = array.length;
        try {
            int cp = Character.codePointAt(array, 0, limit);
            System.out.println("Code point at index 0: " + cp);
        } catch (Exception e) {
            System.out.println("Failed: Unexpected exception at valid index 0");
        }
        try {
            int cp = Character.codePointAt(array, 1, limit);
            System.out.println("Code point at index 1: " + cp);
        } catch (Exception e) {
            System.out.println("Failed: Unexpected exception at valid index 1");
        }
    }
}
