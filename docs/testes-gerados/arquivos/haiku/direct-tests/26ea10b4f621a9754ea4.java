/*
 * @test
 * @summary Verifies the mapToInt accept path of ReferencePipeline through public Stream API behavior
 * @run main ReferencePipelineWitupTest
 */
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {
    public static void main(String[] args) {
        int sum = Stream.of("a", "bb", "ccc").mapToInt(String::length).sum();
        if (sum != 6) {
            throw new RuntimeException("Unexpected sum: " + sum);
        }

        List<Integer> out = new ArrayList<>();
        Stream.of("x", "yy", "zzz").mapToInt(String::length).forEach(i -> out.add(i));
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        if (!out.equals(expected)) {
            throw new RuntimeException("Unexpected order/values: " + out);
        }

        long emptyCount = Stream.<String>empty().mapToInt(String::length).count();
        if (emptyCount != 0L) {
            throw new RuntimeException("Expected empty count 0, got " + emptyCount);
        }

        final int[] calls = new int[1];
        int total = Stream.of("a", "bb", "ccc")
                .mapToInt(s -> {
                    calls[0]++;
                    return s.length();
                })
                .sum();
        if (calls[0] != 3 || total != 6) {
            throw new RuntimeException("Mapper calls=" + calls[0] + ", total=" + total);
        }

        boolean npe = false;
        try {
            Stream.of("a", null).mapToInt(String::length).sum();
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("Expected NullPointerException for null element");
        }
    }
}
