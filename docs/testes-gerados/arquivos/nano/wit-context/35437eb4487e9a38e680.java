/*
 * @test
 * @summary Tests for java.util.stream.ReferencePipeline
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.stream.Stream;
import java.util.function.Predicate;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testFilterWithNullPredicate();
        testFilterWithValidPredicate();
    }

    public static void testFilterWithNullPredicate() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
        try {
            stream.filter(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("NullPointerException correctly thrown for null predicate");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }

    public static void testFilterWithValidPredicate() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
        Stream<Integer> filteredStream = stream.filter(i -> i > 1);
        StringBuilder result = new StringBuilder();
        filteredStream.forEach(i -> result.append(i).append(","));
        if ("2,3,".equals(result.toString())) {
            System.out.println("Filter with valid predicate passed");
        } else {
            System.err.println("Filter with valid predicate failed, got: " + result);
            System.exit(1);
        }
    }
}
