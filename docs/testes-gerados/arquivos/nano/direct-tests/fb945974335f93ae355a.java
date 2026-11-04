/* @test    @summary Test for ReferencePipeline.limit method with valid and invalid inputs    @run main ReferencePipelineWitupTest */

import java.util.stream.ReferencePipeline;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testLimitWithPositive();
        testLimitWithZero();
        testLimitWithNegative();
    }

    static void testLimitWithPositive() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
        Stream<Integer> limitedStream = (Stream<Integer>) stream.limit(3);
        Object[] result = limitedStream.toArray();
        assert result.length == 3 : "Expected 3 elements, got " + result.length;
        assert result[0].equals(1) : "First element should be 1";
        assert result[1].equals(2) : "Second element should be 2";
        assert result[2].equals(3) : "Third element should be 3";
    }

    static void testLimitWithZero() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
        Stream<Integer> limitedStream = (Stream<Integer>) stream.limit(0);
        Object[] result = limitedStream.toArray();
        assert result.length == 0 : "Expected 0 elements, got " + result.length;
    }

    static void testLimitWithNegative() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
        try {
            stream.limit(-1);
            assert false : "Expected IllegalArgumentException for negative maxSize";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("-1") : "Exception message mismatch";
        }
    }
}
