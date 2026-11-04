/*
 * @test
 * @summary Smoke test that a SubmissionPublisher delivers onSubscribe to a subscriber
 * @run main SubmissionPublisherWitupTest
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class SubmissionPublisherWitupTest {
    public static void main(String[] args) throws Exception {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        AtomicReference<Flow.Subscription> ref = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);
        publisher.subscribe(new Flow.Subscriber<String>() {
            public void onSubscribe(Flow.Subscription s) {
                ref.set(s);
                latch.countDown();
            }
            public void onNext(String item) { }
            public void onError(Throwable t) { }
            public void onComplete() { }
        });
        if (!latch.await(10, TimeUnit.SECONDS)) {
            throw new AssertionError("onSubscribe not delivered");
        }
        if (ref.get() == null) {
            throw new AssertionError("subscription is null");
        }
        ref.get().cancel();
    }
}
