/*
 * @test
 * @summary Witup-derived checks for ThreadLocal.withInitial: null supplier rejection and supplier-based initialization
 * @run main ThreadLocalWitupTest
 */

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadLocalWitupTest {

    public static void main(String[] args) throws Exception {
        testNullSupplierThrowsNPE();
        testInitialValueComesFromSupplier();
        testSetOverridesInitialValue();
        testRemoveForcesReinitialization();
        testPerThreadIndependence();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullSupplierThrowsNPE() {
        try {
            ThreadLocal.withInitial(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("withInitial(null) should throw NullPointerException");
    }

    static void testInitialValueComesFromSupplier() {
        AtomicInteger counter = new AtomicInteger();
        ThreadLocal<String> tl = ThreadLocal.withInitial(() -> "v" + counter.incrementAndGet());
        check(tl != null, "withInitial must return a non-null ThreadLocal");
        check("v1".equals(tl.get()), "first get should use supplier value v1");
        check("v1".equals(tl.get()), "second get should return cached value v1");
        check(counter.get() == 1, "supplier should be invoked exactly once per thread without remove");
    }

    static void testSetOverridesInitialValue() {
        AtomicInteger counter = new AtomicInteger();
        ThreadLocal<String> tl = ThreadLocal.withInitial(() -> "init" + counter.incrementAndGet());
        tl.set("explicit");
        check("explicit".equals(tl.get()), "get after set should return explicitly set value");
        check(counter.get() == 0, "supplier must not be invoked when value was set explicitly");
    }

    static void testRemoveForcesReinitialization() {
        AtomicInteger counter = new AtomicInteger();
        ThreadLocal<String> tl = ThreadLocal.withInitial(() -> "v" + counter.incrementAndGet());
        check("v1".equals(tl.get()), "initial get should return v1");
        tl.remove();
        check("v2".equals(tl.get()), "get after remove should invoke supplier again and return v2");
        check(counter.get() == 2, "supplier should have been invoked twice");
    }

    static void testPerThreadIndependence() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger();
        ThreadLocal<Integer> tl = ThreadLocal.withInitial(counter::incrementAndGet);
        Integer mainValue = tl.get();
        Integer[] workerValue = new Integer[1];
        Thread worker = new Thread(() -> workerValue[0] = tl.get());
        worker.start();
        worker.join();
        check(mainValue != null && workerValue[0] != null, "both threads should obtain a value");
        check(!mainValue.equals(workerValue[0]), "each thread should get its own supplier-initialized value");
        check(counter.get() == 2, "supplier should be invoked once per thread");
    }
}
