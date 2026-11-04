/*
 * @test
 * @summary Test ReferencePipeline.reduce(BinaryOperator) behavior
 * @run main ReferencePipelineWitupTest
 */

import java.util.Optional;
import java.util.stream.Stream;
import java.util.function.BinaryOperator;

public class ReferencePipelineWitupTest {
    public static void main(String[] args) {
        testReduceSum();
        testReduceEmpty();
        testReduceSingleElement();
    }

    private static void testReduceSum() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4);
        Optional<Integer> result = stream.reduce(new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer a, Integer b) {
                return a + b;
            }
        });
        if (!result.isPresent() || result.get() != 10) {
            throw new AssertionError("Expected sum 10 but got " + result);
        }
    }

    private static void testReduceEmpty() {
        Stream<String> stream = Stream.empty();
        Optional<String> result = stream.reduce(new BinaryOperator<String>() {
            @Override
            public String apply(String a, String b) {
                return a + b;
            }
        });
        if (result.isPresent()) {
            throw new AssertionError("Expected empty Optional but got " + result);
        }
    }

    private static void testReduceSingleElement() {
        Stream<String> stream = Stream.of("single");
        Optional<String> result = stream.reduce(new BinaryOperator<String>() {
            @Override
            public String apply(String a, String b) {
                return a + b;
            }
        });
        if (!result.isPresent() || !"single".equals(result.get())) {
            throw new AssertionError("Expected Optional with 'single' but got " + result);
        }
    }
}
