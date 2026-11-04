/*
 * @test
 * @summary Test Sink.ChainedReference constructor for null downstream handling
 * @run main SinkWitupTest
 */

import java.util.stream.Sink;
import java.util.Objects;

public class SinkWitupTest {
    public static void main(String[] args) {
        testConstructorRejectsNull();
    }

    private static void testConstructorRejectsNull() {
        try {
            new Sink.ChainedReference<>(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception, test passes
        }
    }
}
