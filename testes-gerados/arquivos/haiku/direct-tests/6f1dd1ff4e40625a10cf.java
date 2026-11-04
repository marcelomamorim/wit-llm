/*
 * @test
 * @summary Witup-style tests for Semaphore.tryAcquire() non-blocking behavior
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {

    public static void main(String[] args) {
        Semaphore zero = new Semaphore(0);
        check(!zero.tryAcquire(), "zero permits must fail on first attempt");
        check(!zero.tryAcquire(), "zero permits must fail on repeated attempt");

        Semaphore one = new Semaphore(1);
        check(one.tryAcquire(), "one permit must be acquired once");
        check(!one.tryAcquire(), "second acquire with one permit must fail");

        Semaphore fair = new Semaphore(2, true);
        check(fair.tryAcquire(), "fair semaphore: first acquire must succeed");
        check(fair.tryAcquire(), "fair semaphore: second acquire must succeed");
        check(!fair.tryAcquire(), "fair semaphore: third acquire must fail");

        Semaphore negative = new Semaphore(-1);
        check(!negative.tryAcquire(), "negative permits must fail");

        Semaphore nonFair = new Semaphore(3, false);
        int acquired = 0;
        for (int i = 0; i < 5; i++) {
            if (nonFair.tryAcquire()) {
                acquired++;
            }
        }
        check(acquired == 3, "non-fair semaphore with 3 permits must grant exactly 3 acquires, got " + acquired);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
