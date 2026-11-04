/*
 * @test
 * @summary Tests for java.util.stream.ReferencePipeline.anyMatch(Predicate<? super P_OUT> predicate)
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.function.Predicate;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testAnyMatchWithNullPredicate();
        testAnyMatchWithAlwaysTruePredicate();
        testAnyMatchWithAlwaysFalsePredicate();
    }

    private static void testAnyMatchWithNullPredicate() {
        try {
            ReferencePipeline<?> pipeline = createSamplePipeline();
            pipeline.anyMatch(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testAnyMatchWithAlwaysTruePredicate() {
        try {
            ReferencePipeline<String> pipeline = createSamplePipeline();
            boolean result = pipeline.anyMatch(s -> true);
            if (result) {
                System.out.println("PASS: anyMatch with always true predicate returned true");
            } else {
                System.out.println("FAIL: anyMatch with always true predicate returned false");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testAnyMatchWithAlwaysFalsePredicate() {
        try {
            ReferencePipeline<String> pipeline = createSamplePipeline();
            boolean result = pipeline.anyMatch(s -> false);
            if (!result) {
                System.out.println("PASS: anyMatch with always false predicate returned false");
            } else {
                System.out.println("FAIL: anyMatch with always false predicate returned true");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static ReferencePipeline<String> createSamplePipeline() {
        // Cria uma pipeline simples com uma lista de exemplo
        return java.util.Arrays.asList("a", "b", "c").stream();
    }
}
