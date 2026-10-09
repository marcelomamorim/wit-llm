/* @test    @summary Test LinkedBlockingDeque putLast method  @run main LinkedBlockingDequeWitupTest_Pf4f90145f75d */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingDequeWitupTest_Pf4f90145f75d {

    public static void main(String[] args) throws InterruptedException {
        testPutLastBasic();
        testPutLastNull();
        testPutLastBlockingBehavior();
    }

    static void testPutLastBasic() throws InterruptedException {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.putLast("element");
        String result = deque.takeFirst();
        assert result.equals("element") : "Expected 'element', but got: " + result;
    }

    static void testPutLastNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.putLast(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException expected) {
        }
    }

    static void testPutLastBlockingBehavior() throws InterruptedException {
        final LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(1);
        deque.putLast("first");
        Thread t = new Thread(() -> {
            try {
                deque.putLast("second");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        t.start();
        TimeUnit.MILLISECONDS.sleep(100);
        boolean isAliveBefore = t.isAlive();
        assert isAliveBefore : "Thread should be blocked on putLast()";
        deque.takeFirst();
        t.join(1000);
        boolean tFinished = !t.isAlive();
        assert tFinished : "Thread should have unblocked after removing element";
    }
}
