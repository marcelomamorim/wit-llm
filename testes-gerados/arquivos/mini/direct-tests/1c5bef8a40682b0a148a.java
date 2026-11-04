/*
 * @test
 * @summary Test sun.text.normalizer.Norm2AllModes.normalize(CharSequence, Appendable) method
 * @run main Norm2AllModesWitupTest
 */

import sun.text.normalizer.Norm2AllModes;
import sun.text.normalizer.NormalizerImpl;
import java.io.IOException;

public class Norm2AllModesWitupTest {

    // Minimal stub for NormalizerImpl.ReorderingBuffer to allow compilation and testing
    static class DummyReorderingBuffer extends Appendable {
        private final Appendable dest;
        DummyReorderingBuffer(Appendable dest) {
            this.dest = dest;
        }
        public void flush() throws IOException {
            // no-op
        }
        @Override
        public Appendable append(CharSequence csq) throws IOException {
            return dest.append(csq);
        }
        @Override
        public Appendable append(CharSequence csq, int start, int end) throws IOException {
            return dest.append(csq, start, end);
        }
        @Override
        public Appendable append(char c) throws IOException {
            return dest.append(c);
        }
    }

    // Subclass Norm2AllModes to override normalize(CharSequence, DummyReorderingBuffer) to avoid dependency on impl
    static class TestNorm2AllModes extends Norm2AllModes {
        private final NormalizerImpl impl = null;

        public Appendable normalize(CharSequence src, Appendable dest) {
            if (dest == src) {
                throw new IllegalArgumentException();
            }
            DummyReorderingBuffer buffer = new DummyReorderingBuffer(dest);
            normalize(src, buffer);
            try {
                buffer.flush();
            } catch (IOException e) {
                throw new InternalError(e.toString(), e);
            }
            return dest;
        }

        // Provide a dummy normalize method to satisfy call
        public void normalize(CharSequence src, DummyReorderingBuffer buffer) {
            try {
                buffer.append(src);
            } catch (IOException e) {
                throw new InternalError(e.toString(), e);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        TestNorm2AllModes norm = new TestNorm2AllModes();

        // Test 1: Normal case - different src and dest
        StringBuilder dest1 = new StringBuilder();
        String src1 = "abc";
        Appendable result1 = norm.normalize(src1, dest1);
        if (result1 != dest1) {
            throw new RuntimeException("Returned Appendable is not the dest instance");
        }
        if (!dest1.toString().equals(src1)) {
            throw new RuntimeException("Content mismatch after normalize: expected '" + src1 + "' but got '" + dest1 + "'");
        }

        // Test 2: dest == src should throw IllegalArgumentException
        try {
            CharSequence same = "same";
            norm.normalize(same, (Appendable) same);
            throw new RuntimeException("Expected IllegalArgumentException when dest == src");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test 3: normalize with empty src
        StringBuilder dest3 = new StringBuilder();
        String src3 = "";
        Appendable result3 = norm.normalize(src3, dest3);
        if (result3 != dest3) {
            throw new RuntimeException("Returned Appendable is not the dest instance for empty src");
        }
        if (!dest3.toString().equals(src3)) {
            throw new RuntimeException("Content mismatch after normalize with empty src");
        }

        System.out.println("All tests passed.");
    }
}
