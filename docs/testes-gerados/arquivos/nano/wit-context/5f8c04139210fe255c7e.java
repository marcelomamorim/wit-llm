/*
 * @test
 * @summary Tests for DoublePipeline.mapToLong method to verify behavior with null input.
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoublePipeline;
import java.util.stream.LongStream;
import java.util.function.DoubleToLongFunction;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testMapToLongWithNullMapper();
    }

    public static void testMapToLongWithNullMapper() {
        DoublePipeline pipeline = new DoublePipeline() {
            @Override
            public LongStream mapToLong(DoubleToLongFunction mapper) {
                return null;
            }
        };
        try {
            pipeline.mapToLong(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
