/*
 * @test
 * @summary Black-box tests for SubmissionPublisher.consume(Consumer)
 * @run main SubmissionPublisherWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class SubmissionPublisherWitupTest {

    public static void main(String[] args) throws Exception {
        testNullConsumerThrowsNPE();
        testConsumesItemsInOrderAndCompletes();
        testConsumerExceptionCompletesExceptionally();
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException(msg);
        }
    }

    static void testNullConsumerThrowsNPE() {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        try {
            publisher.consume(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            publisher.close();
        }
    }

    static void testConsumesItemsInOrderAndCompletes() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            SubmissionPublisher<Integer> publisher =
                new SubmissionPublisher<>(executor, 16);
            List<Integer> received = new ArrayList<>();
            Consumer<Integer> consumer = received::add;
            CompletableFuture<Void> status = publisher.consume(consumer);
            publisher.submit(1);
            publisher.submit(2);
            publisher.submit(3);
            publisher.close();
            Void result = status.get(10, TimeUnit.SECONDS);
            check(result == null, "expected null result on normal completion");
            check(status.isDone(), "future should be done");
            check(!status.isCompletedExceptionally(), "future should not be exceptional");
            check(received.size() == 3, "expected 3 items, got " + received.size());
            check(received.get(0) == 1 && received.get(1) == 2 && received.get(2) == 3,
                  "items out of order: " + received);
        } finally {
            executor.shutdown();
        }
    }

    static void testConsumerExceptionCompletesExceptionally() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            SubmissionPublisher<Integer> publisher =
                new SubmissionPublisher<>(executor, 16);
            CompletableFuture<Void> status = publisher.consume(item -> {
                throw new IllegalStateException("boom");
            });
            publisher.submit(1);
            try {
                status.get(10, TimeUnit.SECONDS);
                throw new RuntimeException("expected ExecutionException");
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                check(cause instanceof IllegalStateException,
                      "unexpected cause: " + cause);
                check("boom".equals(cause.getMessage()),
                      "unexpected message: " + cause.getMessage());
            }
            check(status.isCompletedExceptionally(),
                  "future should complete exceptionally");
            publisher.close();
        } finally {
            executor.shutdown();
        }
    }
}
