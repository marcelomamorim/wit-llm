/*
 * @test
 * @summary Test Sink.ChainedInt constructor for null downstream argument
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
            new Sink.ChainedInt(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected exception
        }
    }
}
