/*
 * @test
 * @summary Verifies public DoubleStream.mapToObj behavior, including NullPointerException for a null mapper
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testNullMapperThrowsNPE();
        testMapToObjProducesMappedElements();
        testEmptyStreamMapsToNothing();
    }

    static void testNullMapperThrowsNPE() {
        try {
            DoubleStream.of(1.0).mapToObj(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null mapper");
    }

    static void testMapToObjProducesMappedElements() {
        Object[] result = DoubleStream.of(1.0, 2.5)
                .mapToObj(d -> "v" + d)
                .toArray();
        if (result.length != 2) {
            throw new AssertionError("unexpected length: " + result.length);
        }
        if (!"v1.0".equals(result[0])) {
            throw new AssertionError("unexpected element 0: " + result[0]);
        }
        if (!"v2.5".equals(result[1])) {
            throw new AssertionError("unexpected element 1: " + result[1]);
        }
    }

    static void testEmptyStreamMapsToNothing() {
        long count = DoubleStream.empty()
                .mapToObj(d -> d)
                .count();
        if (count != 0L) {
            throw new AssertionError("unexpected count: " + count);
        }
    }
}
