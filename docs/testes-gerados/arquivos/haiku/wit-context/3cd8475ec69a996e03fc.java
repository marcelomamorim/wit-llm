/*
 * @test
 * @summary Tests public toArray(IntFunction) behavior of ReferencePipeline via Stream API
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testToArrayPreservesOrderAndType();
        testToArrayEmptyStream();
        testReuseAfterToArrayThrowsIllegalStateException();
        testIncompatibleGeneratorThrowsArrayStoreException();
    }

    static void testToArrayPreservesOrderAndType() {
        String[] result = Stream.of("a", "b", "c").toArray(String[]::new);
        if (result.length != 3) {
            throw new RuntimeException("unexpected length: " + result.length);
        }
        if (!"a".equals(result[0]) || !"b".equals(result[1]) || !"c".equals(result[2])) {
            throw new RuntimeException("unexpected contents");
        }
    }

    static void testToArrayEmptyStream() {
        String[] result = Stream.<String>empty().toArray(String[]::new);
        if (result.length != 0) {
            throw new RuntimeException("expected empty array, got length " + result.length);
        }
    }

    static void testReuseAfterToArrayThrowsIllegalStateException() {
        Stream<String> s = Stream.of("x");
        s.toArray(String[]::new);
        try {
            s.toArray(String[]::new);
            throw new RuntimeException("expected IllegalStateException on reuse");
        } catch (IllegalStateException expected) {
        }
    }

    static void testIncompatibleGeneratorThrowsArrayStoreException() {
        try {
            Stream.of(1, 2).toArray(String[]::new);
            throw new RuntimeException("expected ArrayStoreException");
        } catch (ArrayStoreException expected) {
        }
    }
}
