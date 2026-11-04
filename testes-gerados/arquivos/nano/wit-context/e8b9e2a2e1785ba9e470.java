/*
 * @test
 * @summary Tests the filter method of DoublePipeline for null predicate.
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoublePipeline;
import java.util.stream.DoubleStream;
import java.util.function.DoublePredicate;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testFilterWithNullPredicate();
        testFilterWithValidPredicate();
    }

    private static void testFilterWithNullPredicate() {
        DoubleStream stream = DoubleStream.of(1.0, 2.0, 3.0);
        try {
            DoubleStream result = stream.filter(null);
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            System.out.println("Caught expected NullPointerException for null predicate.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }

    private static void testFilterWithValidPredicate() {
        DoubleStream stream = DoubleStream.of(1.0, 2.0, 3.0);
        DoublePredicate predicate = d -> d > 1.0;
        DoubleStream filteredStream = stream.filter(predicate);
        double[] resultArray = filteredStream.toArray();
        if (resultArray.length != 2 || resultArray[0] != 2.0 || resultArray[1] != 3.0) {
            System.err.println("Filter did not produce expected results.");
            System.exit(1);
        } else {
            System.out.println("Filter with valid predicate passed.");
        }
    }
}
