/*
 * @test
 * @summary Test LongStream.concat static method behavior
 * @run main LongStreamWitupTest
 */

import java.util.stream.LongStream;
import java.util.stream.StreamSupport;
import java.util.Spliterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class LongStreamWitupTest {
    public static void main(String[] args) {
        testConcatBasic();
        testConcatNulls();
        testConcatParallelFlag();
        testConcatOnCloseCalled();
    }

    static void testConcatBasic() {
        LongStream s1 = LongStream.of(1L, 2L, 3L);
        LongStream s2 = LongStream.of(4L, 5L);
        LongStream concat = LongStream.concat(s1, s2);

        long[] result = concat.toArray();
        if (result.length != 5) throw new AssertionError("Expected length 5 but got " + result.length);
        for (int i = 0; i < 5; i++) {
            if (result[i] != i + 1) {
                throw new AssertionError("Expected " + (i + 1) + " but got " + result[i]);
            }
        }
    }

    static void testConcatNulls() {
        try {
            LongStream.concat(null, LongStream.of(1L));
            throw new AssertionError("Expected NullPointerException for null first argument");
        } catch (NullPointerException expected) {
        }
        try {
            LongStream.concat(LongStream.of(1L), null);
            throw new AssertionError("Expected NullPointerException for null second argument");
        } catch (NullPointerException expected) {
        }
    }

    static void testConcatParallelFlag() {
        LongStream s1 = LongStream.of(1L, 2L).parallel();
        LongStream s2 = LongStream.of(3L, 4L);
        LongStream concat = LongStream.concat(s1, s2);
        if (!concat.isParallel()) {
            throw new AssertionError("Expected concat stream to be parallel when one source is parallel");
        }

        s1 = LongStream.of(1L, 2L);
        s2 = LongStream.of(3L, 4L).parallel();
        concat = LongStream.concat(s1, s2);
        if (!concat.isParallel()) {
            throw new AssertionError("Expected concat stream to be parallel when one source is parallel");
        }

        s1 = LongStream.of(1L, 2L);
        s2 = LongStream.of(3L, 4L);
        concat = LongStream.concat(s1, s2);
        if (concat.isParallel()) {
            throw new AssertionError("Expected concat stream to be sequential when both sources are sequential");
        }
    }

    static void testConcatOnCloseCalled() {
        final AtomicBoolean closedA = new AtomicBoolean(false);
        final AtomicBoolean closedB = new AtomicBoolean(false);

        LongStream s1 = LongStream.of(1L).onClose(new Runnable() {
            public void run() {
                closedA.set(true);
            }
        });

        LongStream s2 = LongStream.of(2L).onClose(new Runnable() {
            public void run() {
                closedB.set(true);
            }
        });

        LongStream concat = LongStream.concat(s1, s2);
        concat.close();

        if (!closedA.get()) {
            throw new AssertionError("Expected first stream's onClose to be called");
        }
        if (!closedB.get()) {
            throw new AssertionError("Expected second stream's onClose to be called");
        }
    }
}
