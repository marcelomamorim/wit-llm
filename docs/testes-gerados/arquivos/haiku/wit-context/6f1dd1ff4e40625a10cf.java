/*
 * @test
 * @summary Tests the non-blocking Semaphore.tryAcquire() method on observable permit counts
 * @run main SemaphoreWitupTest
 */
import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {
    public static void main(String[] args) {
        Semaphore s = new Semaphore(2);
        check(s.tryAcquire(), "first tryAcquire should succeed with 2 permits");
        check(s.tryAcquire(), "second tryAcquire should succeed with 2 permits");
        check(!s.tryAcquire(), "third tryAcquire should fail when no permits remain");

        Semaphore f = new Semaphore(1, true);
        check(f.tryAcquire(), "fair semaphore first tryAcquire should succeed");
        check(!f.tryAcquire(), "fair semaphore second tryAcquire should fail");

        check(!new Semaphore(0).tryAcquire(), "tryAcquire with zero permits should return false");
        check(!new Semaphore(-1).tryAcquire(), "tryAcquire with negative permits should return false");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
