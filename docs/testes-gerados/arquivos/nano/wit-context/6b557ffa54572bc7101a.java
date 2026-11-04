/*
 * @test
 * @summary Tests for LongPipeline.filter(LongPredicate)
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongPipeline;
import java.util.stream.LongStream;
import java.util.function.LongPredicate;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testFilterWithNullPredicate();
        testFilterWithValidPredicate();
    }

    public static void testFilterWithNullPredicate() {
        try {
            LongStream stream = LongStream.of(1L, 2L, 3L);
            stream.filter(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("NullPointerException caught as expected");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }

    public static void testFilterWithValidPredicate() {
        LongStream stream = LongStream.of(1L, 2L, 3L, 4L);
        LongStream filteredStream = stream.filter(t -> t > 2);
        long[] result = filteredStream.toArray();
        if (result.length != 2 || result[0] != 3L || result[1] != 4L) {
            System.err.println("Filter did not produce expected results: " + java.util.Arrays.toString(result));
            System.exit(1);
        } else {
            System.out.println("Filter with valid predicate passed");
        }
    }
}
