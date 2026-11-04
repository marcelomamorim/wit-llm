/*
 * @test
 * @summary Checks that a SubmissionPublisher built with the (Executor, int) constructor delivers submitted items in order and completes subscribers on close
 * @run main SubmissionPublisherWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class SubmissionPublisherWitupTest {

    static final class CollectingSubscriber implements Flow.Subscriber<Integer> {
        final List<Integer> items = new ArrayList<>();
        volatile boolean completed;
        volatile Throwable error;

        public void onSubscribe(Flow.Subscription s) {
            s.request(Long.MAX_VALUE);
        }

        public void onNext(Integer item) {
            items.add(item);
        }

        public void onError(Throwable t) {
            error = t;
        }

        public void onComplete() {
            completed = true;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testDeliversItemsInOrderAndCompletes() {
        SubmissionPublisher<Integer> publisher =
            new SubmissionPublisher<>(Runnable::run, 16);
        CollectingSubscriber sub = new CollectingSubscriber();
        publisher.subscribe(sub);
        publisher.submit(1);
        publisher.submit(2);
        publisher.submit(3);
        publisher.close();
        check(sub.error == null, "unexpected error: " + sub.error);
        check(sub.completed, "subscriber was not completed on close");
        check(sub.items.size() == 3, "expected 3 items but got " + sub.items);
        check(sub.items.get(0) == 1 && sub.items.get(1) == 2 && sub.items.get(2) == 3,
              "items out of order: " + sub.items);
    }

    static void testEachSubscriberReceivesAllItems() {
        SubmissionPublisher<Integer> publisher =
            new SubmissionPublisher<>(Runnable::run, 8);
        CollectingSubscriber a = new CollectingSubscriber();
        CollectingSubscriber b = new CollectingSubscriber();
        publisher.subscribe(a);
        publisher.subscribe(b);
        publisher.submit(10);
        publisher.submit(20);
        publisher.close();
        check(a.items.equals(List.of(10, 20)) || a.items.size() == 2,
              "subscriber a got wrong items: " + a.items);
        check(b.items.size() == 2, "subscriber b got wrong items: " + b.items);
        check(a.items.get(0) == 10 && a.items.get(1) == 20,
              "subscriber a order wrong: " + a.items);
        check(b.items.get(0) == 10 && b.items.get(1) == 20,
              "subscriber b order wrong: " + b.items);
        check(a.completed && b.completed, "not all subscribers completed");
    }

    static void testCloseWithoutItemsCompletesSubscriber() {
        SubmissionPublisher<Integer> publisher =
            new SubmissionPublisher<>(Runnable::run, 4);
        CollectingSubscriber sub = new CollectingSubscriber();
        publisher.subscribe(sub);
        publisher.close();
        check(sub.completed, "subscriber not completed after close without items");
        check(sub.items.isEmpty(), "unexpected items: " + sub.items);
        check(sub.error == null, "unexpected error: " + sub.error);
    }

    public static void main(String[] args) {
        testDeliversItemsInOrderAndCompletes();
        testEachSubscriberReceivesAllItems();
        testCloseWithoutItemsCompletesSubscriber();
    }
}
