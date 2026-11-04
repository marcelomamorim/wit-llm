/*
 * @test
 * @summary Witup-derived tests for SubmissionPublisher.consume
 * @run main SubmissionPublisherWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

public class SubmissionPublisherWitupTest {

    public static void main(String[] args) throws Exception {
        testNullConsumerThrowsNPE();
        testConsumeDeliversItemsAndCompletes();
    }

    static void testNullConsumerThrowsNPE() {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>(Runnable::run, 16);
        try {
            publisher.consume(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null consumer");
    }

    static void testConsumeDeliversItemsAndCompletes() throws Exception {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>(Runnable::run, 16);
        List<Integer> received = new ArrayList<>();
        CompletableFuture<Void> status = publisher.consume(received::add);
        publisher.submit(1);
        publisher.submit(2);
        publisher.submit(3);
        publisher.close();
        Void result = status.get(5, TimeUnit.SECONDS);
        if (result != null) {
            throw new AssertionError("expected null result, got " + result);
        }
        if (status.isCompletedExceptionally()) {
            throw new AssertionError("status completed exceptionally");
        }
        List<Integer> expected = Arrays.asList(1, 2, 3);
        if (!expected.equals(received)) {
            throw new AssertionError("expected " + expected + " but got " + received);
        }
    }
}
