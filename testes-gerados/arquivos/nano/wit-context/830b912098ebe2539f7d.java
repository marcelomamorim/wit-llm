/*
 * @test
 * @summary Testa o método insert de StringBuilder com entrada long.
 * @run main StringBuilderWitupTest
 */

import java.lang.StringBuilder;

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertLongValid();
        testInsertLongWithNegativeOffset();
        testInsertLongWithOffsetExceedingLength();
    }

    private static void testInsertLongValid() {
        StringBuilder sb = new StringBuilder("Hello");
        sb.insert(5, 123L);
        if (!"Hello123".equals(sb.toString())) {
            throw new AssertionError("Expected 'Hello123', but got: " + sb.toString());
        }
    }

    private static void testInsertLongWithNegativeOffset() {
        StringBuilder sb = new StringBuilder("Test");
        try {
            sb.insert(-1, 456L);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testInsertLongWithOffsetExceedingLength() {
        StringBuilder sb = new StringBuilder("Data");
        try {
            sb.insert(10, 789L);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset exceeding length");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
