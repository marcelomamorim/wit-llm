/*
 * @test
 * @summary Test SubmissionPublisher.onError behavior including null argument handling
 * @run main SubmissionPublisherWitupTest
 */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

public class SubmissionPublisherWitupTest {

    public static void main(String[] args) throws Exception {
        testOnErrorWithValidException();
        testOnErrorWithNullException();
    }

    static void testOnErrorWithValidException() throws Exception {
        final CompletableFuture<Throwable> completed = new CompletableFuture<>();
        BiConsumer<Subscriber<? super String>, Throwable> handler = new BiConsumer<Subscriber<? super String>, Throwable>() {
            public void accept(Subscriber<? super String> sub, Throwable ex) {
                completed.complete(ex);
            }
        };

        SubmissionPublisher<String> publisher = new SubmissionPublisher<>(Executors.newSingleThreadExecutor(), 1, handler);

        Throwable testEx = new RuntimeException("test exception");
        publisher.onError(testEx);

        Throwable received = completed.get();
        if (received != testEx) {
            throw new AssertionError("onError did not complete exceptionally with the given exception");
        }

        publisher.close();
    }

    static void testOnErrorWithNullException() throws Exception {
        final CompletableFuture<Throwable> completed = new CompletableFuture<>();
        BiConsumer<Subscriber<? super String>, Throwable> handler = new BiConsumer<Subscriber<? super String>, Throwable>() {
            public void accept(Subscriber<? super String> sub, Throwable ex) {
                completed.complete(ex);
            }
        };

        SubmissionPublisher<String> publisher = new SubmissionPublisher<>(Executors.newSingleThreadExecutor(), 1, handler);

        try {
            publisher.onError(null);
            throw new AssertionError("Expected NullPointerException when passing null to onError");
        } catch (NullPointerException npe) {
            // Expected exception
        } finally {
            publisher.close();
        }
    }
}
