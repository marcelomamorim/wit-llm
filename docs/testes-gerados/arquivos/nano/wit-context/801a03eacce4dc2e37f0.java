/* @test    @summary Test for LongPipeline.findFirst() method to verify normal behavior and exception handling.    @run main LongPipelineWitupTest */

import java.util.OptionalLong;
import java.util.stream.LongStream;
import java.util.stream.Collectors;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testFindFirstNormal();
        testFindFirstLinkedOrConsumed();
    }

    private static void testFindFirstNormal() {
        LongStream stream = LongStream.of(1L, 2L, 3L);
        OptionalLong result = stream.findFirst();
        if (!result.isPresent() || result.getAsLong() != 1L) {
            throw new AssertionError("Expected first element to be 1L, but got: " + result);
        }
    }

    private static void testFindFirstLinkedOrConsumed() {
        LongStream stream = LongStream.of(4L, 5L, 6L);
        stream.close(); // simulate linked or consumed state
        try {
            stream.findFirst();
            throw new AssertionError("Expected IllegalStateException when stream is closed or linked.");
        } catch (IllegalStateException e) {
            // expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }
}
