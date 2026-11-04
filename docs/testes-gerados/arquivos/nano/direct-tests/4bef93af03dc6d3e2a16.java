/* @test    @summary Test ThreadLocal.withInitial method for correct behavior    @run main ThreadLocalWitupTest */

import java.lang.ThreadLocal;
import java.util.function.Supplier;

public class ThreadLocalWitupTest {

    public static void main(String[] args) {
        testWithInitialReturnsCorrectThreadLocal();
    }

    private static void testWithInitialReturnsCorrectThreadLocal() {
        Supplier<Integer> supplier = new Supplier<Integer>() {
            private int count = 0;
            @Override
            public Integer get() {
                return ++count;
            }
        };
        ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(supplier);
        Integer value1 = threadLocal.get();
        Integer value2 = threadLocal.get();
        if (value1 == null || !value1.equals(1)) {
            throw new AssertionError("Expected initial value 1, but got: " + value1);
        }
        if (value2 == null || !value2.equals(1)) {
            throw new AssertionError("Subsequent get should return same initial value 1, but got: " + value2);
        }
        // Create a new thread to verify that initialValue is re-initialized per thread
        Thread t = new Thread(() -> {
            Integer threadValue = threadLocal.get();
            if (threadValue == null || !threadValue.equals(2)) {
                throw new AssertionError("In new thread, expected initial value 2, but got: " + threadValue);
            }
            Integer secondValue = threadLocal.get();
            if (secondValue == null || !secondValue.equals(2)) {
                throw new AssertionError("In new thread, subsequent get should return same value 2, but got: " + secondValue);
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            throw new AssertionError("Thread interrupted", e);
        }
    }
}
