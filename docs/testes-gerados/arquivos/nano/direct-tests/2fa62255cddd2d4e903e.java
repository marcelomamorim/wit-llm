/* @test    @summary Test StringBuilder.insert(int, char[]) method for correctness and regressions    @run main StringBuilderWitupTest */

import java.lang.StringBuilder;

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertCharArrayAtBeginning();
        testInsertCharArrayInMiddle();
        testInsertCharArrayAtEnd();
        testInsertEmptyCharArray();
        testInsertNullCharArray();
    }

    private static void testInsertCharArrayAtBeginning() {
        StringBuilder sb = new StringBuilder("world");
        char[] chars = {'H', 'e', 'l', 'l', 'o'};
        sb.insert(0, chars);
        assert sb.toString().equals("Hello world") : "Failed insert at beginning";
    }

    private static void testInsertCharArrayInMiddle() {
        StringBuilder sb = new StringBuilder("Hello world");
        char[] chars = {' ', 'J', 'a', 'v', 'a'};
        sb.insert(6, chars);
        assert sb.toString().equals("Hello  Java world") : "Failed insert in middle";
    }

    private static void testInsertCharArrayAtEnd() {
        StringBuilder sb = new StringBuilder("Hello");
        char[] chars = {' ', '!', '!'};
        sb.insert(sb.length(), chars);
        assert sb.toString().equals("Hello !!") : "Failed insert at end";
    }

    private static void testInsertEmptyCharArray() {
        StringBuilder sb = new StringBuilder("Test");
        char[] empty = {};
        sb.insert(2, empty);
        assert sb.toString().equals("TeTest") : "Failed insert empty array";
    }

    private static void testInsertNullCharArray() {
        StringBuilder sb = new StringBuilder("NullTest");
        try {
            sb.insert(0, (char[]) null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
