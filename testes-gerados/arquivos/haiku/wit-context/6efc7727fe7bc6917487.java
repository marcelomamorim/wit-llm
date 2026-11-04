/*
 * @test
 * @summary Verifies mapToInt maps elements via the supplied mapper and rejects a null mapper with NullPointerException
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.Stream;

public class ReferencePipelineWitupTest {
    public static void main(String[] args) {
        int sum = Stream.of("a", "bb", "ccc").mapToInt(s -> s.length()).sum();
        if (sum != 6) {
            throw new AssertionError("sum=" + sum);
        }
        try {
            Stream.of("a").mapToInt(null);
            throw new AssertionError("NullPointerException esperada para mapper nulo");
        } catch (NullPointerException expected) {
        }
    }
}
