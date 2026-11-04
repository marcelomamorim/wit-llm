/*
 * @test
 * @summary Test for IntPipeline.findAny() method to observe behavior and exception handling.
 * @run main IntPipelineWitupTest
 */

import java.util.OptionalInt;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testFindAnyOnEmptyStream();
        testFindAnyOnNonEmptyStream();
    }

    private static void testFindAnyOnEmptyStream() {
        IntStream emptyStream = IntStream.empty();
        try {
            OptionalInt result = emptyStream.findAny();
            if (result.isPresent()) {
                System.out.println("Unexpected value: " + result.getAsInt());
            } else {
                System.out.println("Empty stream: no value present as expected.");
            }
        } catch (Exception e) {
            System.out.println("Exception caught on empty stream: " + e);
        }
    }

    private static void testFindAnyOnNonEmptyStream() {
        IntStream stream = IntStream.of(1, 2, 3);
        try {
            OptionalInt result = stream.findAny();
            if (result.isPresent()) {
                System.out.println("Found value: " + result.getAsInt());
            } else {
                System.out.println("Stream unexpectedly empty.");
            }
        } catch (Exception e) {
            System.out.println("Exception caught on non-empty stream: " + e);
        }
    }
}
