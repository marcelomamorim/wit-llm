/*
 * @test
 * @summary Tests for DoublePipeline class methods
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoublePipeline;
import java.util.stream.Stream;
import java.util.function.DoubleFunction;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testMapToObjWithNullMapper();
    }

    public static void testMapToObjWithNullMapper() {
        DoubleFunction<Object> mapper = null;
        try {
            DoublePipeline.<Object>mapToObj(mapper);
            System.err.println("Expected NullPointerException was not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("NullPointerException correctly thrown for null mapper");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
