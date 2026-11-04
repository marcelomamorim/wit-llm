/*
 * @test
 * @summary Tests Cleaner.register null checks and explicit clean behavior of the returned Cleanable
 * @run main CleanerWitupTest
 */

import java.lang.ref.Cleaner;
import java.util.concurrent.atomic.AtomicInteger;

public class CleanerWitupTest {

    public static void main(String[] args) throws Exception {
        Cleaner cleaner = Cleaner.create();

        AtomicInteger counter = new AtomicInteger();
        Object obj = new Object();
        Cleaner.Cleanable cleanable = cleaner.register(obj, counter::incrementAndGet);
        if (cleanable == null) {
            throw new AssertionError("register returned null");
        }

        cleanable.clean();
        cleanable.clean();
        if (counter.get() != 1) {
            throw new AssertionError("cleaning action expected once, was: " + counter.get());
        }

        expectNpe(() -> cleaner.register(null, () -> { }));
        expectNpe(() -> cleaner.register(new Object(), null));
        expectNpe(() -> cleaner.register(null, null));

        AtomicInteger second = new AtomicInteger();
        Cleaner.Cleanable c2 = cleaner.register(new Object(), second::incrementAndGet);
        c2.clean();
        if (second.get() != 1) {
            throw new AssertionError("second registration not cleaned once: " + second.get());
        }
        if (counter.get() != 1) {
            throw new AssertionError("first action affected by second registration");
        }
    }

    private static void expectNpe(Runnable r) {
        try {
            r.run();
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("NullPointerException expected");
    }
}
