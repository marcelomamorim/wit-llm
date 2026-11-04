/*
 * @test
 * @summary Smoke test for public stream pipelines whose intermediate stages wrap Sink.ChainedReference
 * @run main SinkWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SinkWitupTest {
    public static void main(String[] args) {
        List<Integer> result = Arrays.asList(1, 2, 3, 4).stream()
                .filter(x -> x % 2 == 0)
                .map(x -> x * 10)
                .collect(Collectors.toList());
        List<Integer> expected = Arrays.asList(20, 40);
        if (!result.equals(expected)) {
            throw new RuntimeException("Unexpected result: " + result);
        }
    }
}
