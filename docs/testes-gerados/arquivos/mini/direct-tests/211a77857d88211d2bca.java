/*
 * @test
 * @summary Test ReferencePipeline.skip(long) method behavior
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.Stream;
import java.util.stream.ReferencePipeline;
import java.util.stream.SliceOps;

public class ReferencePipelineWitupTest {

    // Minimal subclass to instantiate ReferencePipeline for testing
    static class RP<T> extends ReferencePipeline<T, T> {
        RP() {
            super((Supplier) null, 0, false);
        }

        RP(AbstractPipeline<?, T, ?> upstream, int opFlags) {
            super(upstream, opFlags);
        }

        @Override
        public void forEach(java.util.function.Consumer<? super T> action) {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RP<String> rp = new RP<>();

        // Test skip with n == 0 returns this
        Stream<String> s0 = rp.skip(0);
        if (s0 != rp) {
            throw new AssertionError("skip(0) should return this");
        }

        // Test skip with n > 0 returns SliceOps.makeRef result
        Stream<String> s5 = rp.skip(5);
        if (s5 == rp) {
            throw new AssertionError("skip(n>0) should not return this");
        }

        // Test skip with negative n throws IllegalArgumentException
        try {
            rp.skip(-1);
            throw new AssertionError("skip(-1) should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            if (!"-1".equals(e.getMessage())) {
                throw new AssertionError("Exception message should be the string of the negative number");
            }
        }
    }
}
