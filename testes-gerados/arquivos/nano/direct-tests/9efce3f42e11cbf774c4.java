/*
 * @test
 * @summary Test the max method of ReferencePipeline with various comparators
 * @run main ReferencePipelineWitupTest
 */
import java.util.Optional;
import java.util.Comparator;
import java.util.stream.ReferencePipeline;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testMaxWithNaturalOrder();
        testMaxWithReverseOrder();
        testMaxWithCustomComparator();
    }

    static void testMaxWithNaturalOrder() {
        Integer[] data = {1, 3, 2, 5, 4};
        Optional<Integer> result = ReferencePipeline.of(data)
            .max(Comparator.naturalOrder());
        assert result.isPresent() : "Result should be present";
        assert result.get().equals(5) : "Expected max to be 5";
    }

    static void testMaxWithReverseOrder() {
        String[] data = {"a", "c", "b"};
        Optional<String> result = ReferencePipeline.of(data)
            .max(Comparator.reverseOrder());
        assert result.isPresent() : "Result should be present";
        assert result.get().equals("c") : "Expected max to be c";
    }

    static void testMaxWithCustomComparator() {
        String[] data = {"apple", "banana", "cherry"};
        Comparator<String> lengthComparator = new Comparator<String>() {
            public int compare(String s1, String s2) {
                return Integer.compare(s1.length(), s2.length());
            }
        };
        Optional<String> result = ReferencePipeline.of(data)
            .max(lengthComparator);
        assert result.isPresent() : "Result should be present";
        assert result.get().equals("banana") : "Expected max to be banana";
    }
}
