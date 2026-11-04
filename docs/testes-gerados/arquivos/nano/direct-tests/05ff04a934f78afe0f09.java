/*
 * @test
 * @summary Tests the allMatch method of DoublePipeline for various predicates.
 * @run main DoublePipelineWitupTest
 */
import java.util.stream.DoublePipeline;
import java.util.function.DoublePredicate;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testAllMatchWithAllTruePredicate();
        testAllMatchWithAllFalsePredicate();
        testAllMatchWithEmptyStream();
    }

    static void testAllMatchWithAllTruePredicate() {
        DoublePredicate alwaysTrue = d -> true;
        boolean result = DoublePipeline.allMatch(DoubleStream.of(1.0, 2.0, 3.0), alwaysTrue);
        if (!result) {
            throw new AssertionError("Expected allMatch to return true for all true predicate");
        }
    }

    static void testAllMatchWithAllFalsePredicate() {
        DoublePredicate alwaysFalse = d -> false;
        boolean result = DoublePipeline.allMatch(DoubleStream.of(1.0, 2.0, 3.0), alwaysFalse);
        if (result) {
            throw new AssertionError("Expected allMatch to return false for all false predicate");
        }
    }

    static void testAllMatchWithEmptyStream() {
        DoublePredicate anyPredicate = d -> true;
        boolean result = DoublePipeline.allMatch(DoubleStream.of(), anyPredicate);
        if (result) {
            throw new AssertionError("Expected allMatch to return true for empty stream");
        }
    }
}
