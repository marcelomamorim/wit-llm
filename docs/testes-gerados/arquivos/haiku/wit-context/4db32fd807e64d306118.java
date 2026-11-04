/*
 * @test
 * @summary Verify Cleaner.create(ThreadFactory) rejects a null factory and returns a usable cleaner
 * @run main CleanerWitupTest
 */
import java.lang.ref.Cleaner;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class CleanerWitupTest {

    public static void main(String[] args) throws Exception {
        testNullThreadFactoryThrowsNPE();
        testFactoryIsUsedAndCleanableRunsAction();
    }

    static void testNullThreadFactoryThrowsNPE() {
        try {
            Cleaner.create(null);
            throw new AssertionError("NullPointerException expected for null threadFactory");
        } catch (NullPointerException expected) {
        }
    }

    static void testFactoryIsUsedAndCleanableRunsAction() {
        AtomicInteger factoryCalls = new AtomicInteger();
        ThreadFactory tf = r -> {
            factoryCalls.incrementAndGet();
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        };
        Cleaner cleaner = Cleaner.create(tf);
        if (cleaner == null) {
            throw new AssertionError("Cleaner.create returned null");
        }
        if (factoryCalls.get() < 1) {
            throw new AssertionError("ThreadFactory was not used");
        }

        AtomicInteger actionRuns = new AtomicInteger();
        Object resource = new Object();
        Cleaner.Cleanable cleanable = cleaner.register(resource, actionRuns::incrementAndGet);
        cleanable.clean();
        if (actionRuns.get() != 1) {
            throw new AssertionError("Cleaning action expected once, was " + actionRuns.get());
        }
        cleanable.clean();
        if (actionRuns.get() != 1) {
            throw new AssertionError("Cleaning action must run at most once, was " + actionRuns.get());
        }
    }
}
