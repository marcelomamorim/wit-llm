/*
 * @test
 * @summary Test sun.text.normalizer.Norm2AllModes.normalize(CharSequence, Appendable) for IllegalArgumentException on dest==src and normal behavior
 * @run main Norm2AllModesWitupTest
 */

import java.io.IOException;
import sun.text.normalizer.Norm2AllModes;
import sun.text.normalizer.Norm2AllModes.Normalizer2WithImpl;
import sun.text.normalizer.NormalizerImpl;

public class Norm2AllModesWitupTest {

    // Minimal stub for NormalizerImpl to allow construction of Normalizer2WithImpl
    static class DummyNormalizerImpl extends NormalizerImpl {
        DummyNormalizerImpl() {
            super();
        }
    }

    // Subclass to expose the protected constructor and method
    static class TestNormalizer2WithImpl extends Normalizer2WithImpl {
        TestNormalizer2WithImpl(NormalizerImpl impl) {
            super(impl);
        }

        // Expose normalize(CharSequence, Appendable) for testing
        @Override
        public Appendable normalize(CharSequence src, Appendable dest) {
            return super.normalize(src, dest);
        }

        // Expose normalize(CharSequence, NormalizerImpl.ReorderingBuffer) for internal call
        @Override
        public void normalize(CharSequence src, NormalizerImpl.ReorderingBuffer buffer) {
            // For testing, just append src to buffer.dest
            try {
                buffer.append(src);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        testNormalizeThrowsOnDestEqualsSrc();
        testNormalizeNormalBehavior();
        System.out.println("All tests passed.");
    }

    private static void testNormalizeThrowsOnDestEqualsSrc() {
        NormalizerImpl impl = new DummyNormalizerImpl();
        TestNormalizer2WithImpl norm = new TestNormalizer2WithImpl(impl);

        StringBuilder sb = new StringBuilder("test");
        try {
            norm.normalize(sb, sb);
            throw new AssertionError("Expected IllegalArgumentException when dest == src");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testNormalizeNormalBehavior() throws IOException {
        NormalizerImpl impl = new DummyNormalizerImpl();
        TestNormalizer2WithImpl norm = new TestNormalizer2WithImpl(impl);

        StringBuilder dest = new StringBuilder();
        String src = "abc";

        Appendable result = norm.normalize(src, dest);

        if (result != dest) {
            throw new AssertionError("Returned Appendable should be the same as dest");
        }

        if (!dest.toString().equals(src)) {
            throw new AssertionError("dest should contain normalized src content");
        }
    }
}
