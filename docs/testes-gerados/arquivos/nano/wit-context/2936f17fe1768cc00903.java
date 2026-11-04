/*
 * @test
 * @summary Test for DoublePipeline.noneMatch(DoublePredicate predicate) method.
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoublePipeline;
import java.util.function.DoublePredicate;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testNoneMatchWithNullPredicate();
        testNoneMatchWithAlwaysTruePredicate();
        testNoneMatchWithAlwaysFalsePredicate();
    }

    private static void testNoneMatchWithNullPredicate() {
        try {
            DoublePipeline.noneMatch(null);
            System.out.println("FAIL: Expected NullPointerException for null predicate");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testNoneMatchWithAlwaysTruePredicate() {
        DoublePredicate alwaysTrue = d -> true;
        boolean result = DoublePipeline.noneMatch(alwaysTrue);
        if (result == false) {
            System.out.println("PASS: noneMatch with alwaysTrue predicate returned false as expected");
        } else {
            System.out.println("FAIL: noneMatch with alwaysTrue predicate returned true unexpectedly");
        }
    }

    private static void testNoneMatchWithAlwaysFalsePredicate() {
        DoublePredicate alwaysFalse = d -> false;
        boolean result = DoublePipeline.noneMatch(alwaysFalse);
        if (result == true) {
            System.out.println("PASS: noneMatch with alwaysFalse predicate returned true as expected");
        } else {
            System.out.println("FAIL: noneMatch with alwaysFalse predicate returned false unexpectedly");
        }
    }
}
