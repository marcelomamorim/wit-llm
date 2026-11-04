/*
 * @test
 * @summary Tests for StringBuilder.insert(int, Object) method, focusing on exception paths.
 * @run main StringBuilderWitupTest
 */

import java.lang.StringBuilder;

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertWithNegativeOffset();
        testInsertWithOffsetExceedingLength();
    }

    private static void testInsertWithNegativeOffset() {
        StringBuilder sb = new StringBuilder("test");
        try {
            sb.insert(-1, new Object());
            System.out.println("FAIL: Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected StringIndexOutOfBoundsException for negative offset");
        }
    }

    private static void testInsertWithOffsetExceedingLength() {
        StringBuilder sb = new StringBuilder("test");
        int offset = sb.length() + 1;
        try {
            sb.insert(offset, new Object());
            System.out.println("FAIL: Expected StringIndexOutOfBoundsException for offset exceeding length");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected StringIndexOutOfBoundsException for offset exceeding length");
        }
    }
}
