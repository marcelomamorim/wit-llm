/*
 * @test
 * @summary Verify ThreadLocal.withInitial supplies initial values lazily and per thread
 * @run main ThreadLocalWitupTest
 */

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadLocalWitupTest {

    public static void main(String[] args) throws Exception {
        testInitialValueFromSupplier();
        testSupplierCalledOnce();
        testSetOverridesInitialWithoutSupplier();
        testRemoveThenGetReinitializes();
        testPerThreadIndependence();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testInitialValueFromSupplier() {
        ThreadLocal<String> tl = ThreadLocal.withInitial(() -> "init");
        check("init".equals(tl.get()), "withInitial should supply initial value on get");
    }

    static void testSupplierCalledOnce() {
        AtomicInteger calls = new AtomicInteger();
        ThreadLocal<Integer> tl = ThreadLocal.withInitial(() -> calls.incrementAndGet());
        int first = tl.get().intValue();
        int second = tl.get().intValue();
        check(first == 1, "first get should return 1 but was " + first);
        check(second == 1, "second get should return cached value 1 but was " + second);
        check(calls.get() == 1, "supplier should be called once but was " + calls.get());
    }

    static void testSetOverridesInitialWithoutSupplier() {
        AtomicInteger calls = new AtomicInteger();
        ThreadLocal<String> tl = ThreadLocal.withInitial(() -> {
            calls.incrementAndGet();
            return "init";
        });
        tl.set("explicit");
        check("explicit".equals(tl.get()), "set value should be returned by get");
        check(calls.get() == 0, "supplier should not be called after set but was " + calls.get());
    }

    static void testRemoveThenGetReinitializes() {
        AtomicInteger calls = new AtomicInteger();
        ThreadLocal<Integer> tl = ThreadLocal.withInitial(() -> calls.incrementAndGet());
        int before = tl.get().intValue();
        tl.remove();
        int after = tl.get().intValue();
        check(before == 1, "initial get should return 1 but was " + before);
        check(after == 2, "get after remove should re-invoke supplier and return 2 but was " + after);
        check(calls.get() == 2, "supplier should be called twice but was " + calls.get());
    }

    static void testPerThreadIndependence() throws InterruptedException {
        AtomicInteger calls = new AtomicInteger();
        ThreadLocal<Integer> tl = ThreadLocal.withInitial(() -> calls.incrementAndGet());
        int mainValue = tl.get().intValue();
        final int[] otherValue = new int[1];
        Thread t = new Thread(() -> otherValue[0] = tl.get().intValue());
        t.start();
        t.join();
        check(mainValue == 1, "main thread should get 1 but was " + mainValue);
        check(otherValue[0] == 2, "other thread should get its own initial value 2 but was " + otherValue[0]);
        check(calls.get() == 2, "supplier should be called once per thread but was " + calls.get());
    }
}
