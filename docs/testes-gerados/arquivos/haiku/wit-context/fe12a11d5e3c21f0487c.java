/*
 * @test
 * @summary Verify that ReferencePipeline.mapToLong rejects a null mapper eagerly and maps elements correctly
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testNullMapperThrowsNpe();
        testMapToLongComputesValues();
    }

    static void testNullMapperThrowsNpe() {
        try {
            Stream.of("a").mapToLong(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null mapper");
    }

    static void testMapToLongComputesValues() {
        long sum = Stream.of("1", "22", "333").mapToLong(String::length).sum();
        if (sum != 6L) {
            throw new RuntimeException("expected sum 6 but was " + sum);
        }
    }
}
