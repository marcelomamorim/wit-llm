/*
 * @test
 * @summary Tests for LongStream.concat method to verify exception paths and behavior.
 * @run main LongStreamWitupTest
 */

import java.util.stream.LongStream;
import java.util.Objects;

public class LongStreamWitupTest {

    public static void main(String[] args) {
        testConcatWithNullA();
        testConcatWithNullB();
    }

    private static void testConcatWithNullA() {
        try {
            LongStream.concat(null, LongStream.of(1L, 2L));
            System.err.println("Expected NullPointerException for null 'a' not thrown");
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("Caught expected NullPointerException for null 'a'");
        } catch (Throwable t) {
            System.err.println("Unexpected exception for null 'a': " + t);
        }
    }

    private static void testConcatWithNullB() {
        try {
            LongStream.concat(LongStream.of(1L, 2L), null);
            System.err.println("Expected NullPointerException for null 'b' not thrown");
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("Caught expected NullPointerException for null 'b'");
        } catch (Throwable t) {
            System.err.println("Unexpected exception for null 'b': " + t);
        }
    }
}
