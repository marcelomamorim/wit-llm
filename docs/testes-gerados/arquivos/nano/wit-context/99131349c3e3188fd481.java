/*
 * @test
 * @summary Testa o método insert de StringBuilder para comportamentos observáveis.
 * @run main StringBuilderWitupTest
 */

import java.lang.StringBuilder;

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertWithValidOffset();
        testInsertWithNegativeOffset();
        testInsertWithOffsetExceedingLength();
    }

    private static void testInsertWithValidOffset() {
        StringBuilder sb = new StringBuilder("Hello");
        sb.insert(5, " World");
        if (!"Hello World".equals(sb.toString())) {
            throw new AssertionError("Expected 'Hello World', but got: " + sb.toString());
        }
    }

    private static void testInsertWithNegativeOffset() {
        StringBuilder sb = new StringBuilder("Test");
        try {
            sb.insert(-1, "X");
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testInsertWithOffsetExceedingLength() {
        StringBuilder sb = new StringBuilder("Sample");
        try {
            sb.insert(100, "Y");
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset exceeding length");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
