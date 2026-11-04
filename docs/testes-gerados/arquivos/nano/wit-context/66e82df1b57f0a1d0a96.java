/*
 * @test
 * @summary Tests for IntPipeline.forEach method to observe behavior and exception paths.
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.stream.IntStream;
import java.util.function.IntConsumer;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithValidAction();
    }

    private static void testForEachWithNullAction() {
        IntStream stream = IntStream.of(1, 2, 3);
        try {
            stream.forEach(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testForEachWithValidAction() {
        int[] sum = {0};
        IntStream stream = IntStream.of(4, 5, 6);
        try {
            stream.forEach(i -> sum[0] += i);
            if (sum[0] == 15) {
                System.out.println("PASS: forEach executed correctly, sum=" + sum[0]);
            } else {
                System.out.println("FAIL: Unexpected sum: " + sum[0]);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception during forEach: " + e);
        }
    }
}
