/*
 * @test    @summary Test for ReferencePipeline.toArray() method.
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testToArrayReturnsArrayOfCorrectSize();
        testToArrayWithEmptyStream();
    }

    static void testToArrayReturnsArrayOfCorrectSize() {
        Object[] array = ReferencePipeline.of(() -> java.util.stream.Stream.of("a", "b", "c"))
            .toArray();
        if (array == null) {
            throw new AssertionError("toArray() returned null");
        }
        if (array.length != 3) {
            throw new AssertionError("Expected array length 3, but got " + array.length);
        }
        if (!"a".equals(array[0]) || !"b".equals(array[1]) || !"c".equals(array[2])) {
            throw new AssertionError("Array contents do not match expected values");
        }
    }

    static void testToArrayWithEmptyStream() {
        Object[] array = ReferencePipeline.of(() -> java.util.stream.Stream.<String>empty())
            .toArray();
        if (array == null) {
            throw new AssertionError("toArray() returned null for empty stream");
        }
        if (array.length != 0) {
            throw new AssertionError("Expected empty array, but got length " + array.length);
        }
    }
}
