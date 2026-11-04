/*
 * @test
 * @summary Checks observable behavior of the no-arg SubmissionPublisher constructor: delivery of items to a subscriber in order, normal completion on close, and NullPointerException for null items.
 * @run main SubmissionPublisherWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

public class SubmissionPublisherWitupTest {

    public static void main(String[] args) throws Exception {
        testDeliveryInOrderAndComplete();
        testNullItemRejected();
    }

    static void testDeliveryInOrderAndComplete() throws Exception {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        List<Integer> received = new ArrayList<>();
        CountDownLatch items = new CountDownLatch(3);
        CountDownLatch completed = new CountDownLatch(1);
        final Throwable[] failure = new Throwable[1];

        publisher.subscribe(new Flow.Subscriber<Integer>() {
            public void onSubscribe(Flow.Subscription s) {
                s.request(Long.MAX_VALUE);
            }
            public void onNext(Integer item) {
                received.add(item);
                items.countDown();
            }
            public void onError(Throwable t) {
                failure[0] = t;
                completed.countDown();
            }
            public void onComplete() {
                completed.countDown();
            }
        });

        publisher.submit(1);
        publisher.submit(2);
        publisher.submit(3);
        publisher.close();

        if (!items.await(10, TimeUnit.SECONDS)) {
            throw new RuntimeException("items not delivered in time");
        }
        if (!completed.await(10, TimeUnit.SECONDS)) {
            throw new RuntimeException("onComplete not signalled in time");
        }
        if (failure[0] != null) {
            throw new RuntimeException("unexpected onError", failure[0]);
        }
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        if (!expected.equals(received)) {
            throw new RuntimeException("expected " + expected + " but got " + received);
        }
    }

    static void testNullItemRejected() {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        boolean npe = false;
        try {
            publisher.submit(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        publisher.close();
        if (!npe) {
            throw new RuntimeException("expected NullPointerException for null item");
        }
    }
}
