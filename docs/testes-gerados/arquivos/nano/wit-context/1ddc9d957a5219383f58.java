/* @test    @summary Testando o método takeFirst() de LinkedBlockingDeque para comportamento normal e exceções.    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testTakeFirstNormal();
        testTakeFirstInterrupted();
    }

    static void testTakeFirstNormal() throws InterruptedException {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.offer("element");
        String result = deque.takeFirst();
        if (!"element".equals(result)) {
            throw new AssertionError("Expected 'element', got: " + result);
        }
    }

    static void testTakeFirstInterrupted() throws InterruptedException {
        final LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        Thread t = new Thread(() -> {
            try {
                deque.takeFirst();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        t.start();
        Thread.sleep(50);
        t.interrupt();
        t.join();
        // Como takeFirst() pode ser interrompido, verificar se a thread terminou
        if (t.isAlive()) {
            throw new AssertionError("Thread should have terminated after interruption");
        }
    }
}
