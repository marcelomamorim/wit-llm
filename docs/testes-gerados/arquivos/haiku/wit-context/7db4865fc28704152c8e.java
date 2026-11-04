/*
 * @test
 * @summary Verifies that Cleaner.register rejects null obj and null action with NullPointerException and that the returned Cleanable runs the action at most once.
 * @run main CleanerWitupTest
 */

import java.lang.ref.Cleaner;
import java.util.concurrent.atomic.AtomicInteger;

public class CleanerWitupTest {

    interface Action {
        void run() throws Throwable;
    }

    static void expectNpe(String name, Action a) {
        try {
            a.run();
        } catch (NullPointerException expected) {
            return;
        } catch (Throwable t) {
            throw new AssertionError(name + ": unexpected " + t, t);
        }
        throw new AssertionError(name + ": expected NullPointerException");
    }

    public static void main(String[] args) {
        Cleaner cleaner = Cleaner.create();

        expectNpe("null obj", () -> cleaner.register(null, () -> { }));
        expectNpe("null action", () -> cleaner.register(new Object(), null));
        expectNpe("null obj and null action", () -> cleaner.register(null, null));

        AtomicInteger counter = new AtomicInteger();
        var cleanable = cleaner.register(new Object(), () -> {
            counter.incrementAndGet();
        });
        if (cleanable == null) {
            throw new AssertionError("register returned null");
        }
        if (counter.get() != 0) {
            throw new AssertionError("action ran before clean: " + counter.get());
        }
        cleanable.clean();
        if (counter.get() != 1) {
            throw new AssertionError("expected action once after clean, was " + counter.get());
        }
        cleanable.clean();
        if (counter.get() != 1) {
            throw new AssertionError("action ran more than once: " + counter.get());
        }
    }
}
