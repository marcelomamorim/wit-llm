/*
 * @test
 * @summary Tests the skip method of ReferencePipeline for various inputs.
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testSkipZero();
        testSkipPositive();
        testSkipNegative();
        testSkipLarge();
    }

    static void testSkipZero() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
        Stream<Integer> result = (Stream<Integer>) stream.skip(0);
        Object[] resultArray = result.toArray();
        assert resultArray.length == 3 : "Skip zero should return original stream";
        assert resultArray[0].equals(1) : "First element should be 1";
        assert resultArray[1].equals(2) : "Second element should be 2";
        assert resultArray[2].equals(3) : "Third element should be 3";
    }

    static void testSkipPositive() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
        Stream<Integer> result = (Stream<Integer>) stream.skip(2);
        Object[] resultArray = result.toArray();
        assert resultArray.length == 3 : "Skip 2 should leave 3 elements";
        assert resultArray[0].equals(3) : "First element should be 3";
        assert resultArray[1].equals(4) : "Second element should be 4";
        assert resultArray[2].equals(5) : "Third element should be 5";
    }

    static void testSkipNegative() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
        try {
            Stream<Integer> result = (Stream<Integer>) stream.skip(-1);
            // Should throw IllegalArgumentException
            assert false : "skip(-1) should throw";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("-1") : "Exception message should be -1";
        }
    }

    static void testSkipLarge() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
        Stream<Integer> result = (Stream<Integer>) stream.skip(10);
        Object[] resultArray = result.toArray();
        assert resultArray.length == 0 : "Skip larger than size should result in empty stream";
    }
}
