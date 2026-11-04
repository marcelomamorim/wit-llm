/*
 * @test
 * @summary Test ReferencePipeline.reduce method with various inputs
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.Stream;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public class ReferencePipelineWitupTest {
    public static void main(String[] args) {
        testReduceSum();
        testReduceConcat();
        testReduceEmptyStream();
        testReduceWithNulls();
    }

    private static void testReduceSum() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4);
        Integer result = stream.reduce(
            0,
            new BiFunction<Integer, Integer, Integer>() {
                public Integer apply(Integer a, Integer b) {
                    return a + b;
                }
            },
            new BinaryOperator<Integer>() {
                public Integer apply(Integer a, Integer b) {
                    return a + b;
                }
            }
        );
        if (result == null || result != 10) {
            throw new AssertionError("Expected sum 10 but got " + result);
        }
    }

    private static void testReduceConcat() {
        Stream<String> stream = Stream.of("a", "b", "c");
        String result = stream.reduce(
            "",
            new BiFunction<String, String, String>() {
                public String apply(String a, String b) {
                    return a + b;
                }
            },
            new BinaryOperator<String>() {
                public String apply(String a, String b) {
                    return a + b;
                }
            }
        );
        if (!"abc".equals(result)) {
            throw new AssertionError("Expected concatenation 'abc' but got '" + result + "'");
        }
    }

    private static void testReduceEmptyStream() {
        Stream<String> stream = Stream.empty();
        String result = stream.reduce(
            "empty",
            new BiFunction<String, String, String>() {
                public String apply(String a, String b) {
                    return a + b;
                }
            },
            new BinaryOperator<String>() {
                public String apply(String a, String b) {
                    return a + b;
                }
            }
        );
        if (!"empty".equals(result)) {
            throw new AssertionError("Expected identity 'empty' for empty stream but got '" + result + "'");
        }
    }

    private static void testReduceWithNulls() {
        Stream<String> stream = Stream.of("x", null, "y");
        String result = stream.reduce(
            "",
            new BiFunction<String, String, String>() {
                public String apply(String a, String b) {
                    if (b == null) return a;
                    return a + b;
                }
            },
            new BinaryOperator<String>() {
                public String apply(String a, String b) {
                    if (a == null) return b;
                    if (b == null) return a;
                    return a + b;
                }
            }
        );
        if (!"xy".equals(result)) {
            throw new AssertionError("Expected 'xy' ignoring nulls but got '" + result + "'");
        }
    }
}
