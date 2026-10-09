/* @test    @summary Test Random.ints() method for correct stream generation    @run main RandomWitupTest_P66bddb2829fe */

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class RandomWitupTest_P66bddb2829fe {
    public static void main(String[] args) {
        testIntsReturnsStream();
        testMultipleCallsProduceDifferentStreams();
        testStreamHasExpectedSize();
    }

    private static void testIntsReturnsStream() {
        Random rand = new Random(12345L);
        IntStream stream = rand.ints();
        int[] values = stream.limit(10).toArray();
        if (values.length != 10) {
            throw new AssertionError("Expected 10 elements, got " + values.length);
        }
        int[] expected = { 
            1062861642,  121842174,  157245927,  107245927,  174245927,
            124245927,  134245927,  144245927,  154245927,  164245927
        };
        // Since the sequence is deterministic with seed, check first element
        int firstValue = values[0];
        int expectedFirst = 1062861642;
        if (firstValue != expectedFirst) {
            throw new AssertionError("First value mismatch: expected " + expectedFirst + ", got " + firstValue);
        }
    }

    private static void testMultipleCallsProduceDifferentStreams() {
        Random rand = new Random(54321L);
        IntStream stream1 = rand.ints();
        IntStream stream2 = rand.ints();
        int[] arr1 = stream1.limit(5).toArray();
        int[] arr2 = stream2.limit(5).toArray();
        boolean allEqual = true;
        for (int i = 0; i < 5; i++) {
            if (arr1[i] != arr2[i]) {
                allEqual = false;
                break;
            }
        }
        if (allEqual) {
            throw new AssertionError("Consecutive calls produce identical sequences, which is unlikely.");
        }
    }

    private static void testStreamHasExpectedSize() {
        Random rand = new Random(98765L);
        IntStream stream = rand.ints();
        long count = stream.limit(15).count();
        if (count != 15) {
            throw new AssertionError("Stream size mismatch: expected 15, got " + count);
        }
    }
}
