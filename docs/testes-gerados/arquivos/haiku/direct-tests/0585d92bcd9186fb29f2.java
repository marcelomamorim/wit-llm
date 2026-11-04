/*
 * @test
 * @summary Verifies the no-arg SubmissionPublisher constructor defaults and basic delivery of submitted items
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
        SubmissionPublisher<Integer> p = new SubmissionPublisher<>();

        check(p.getExecutor() != null, "executor must not be null");
        check(p.getMaxBufferCapacity() == Flow.defaultBufferSize(),
              "max buffer capacity must equal Flow.defaultBufferSize()");
        check(!p.isClosed(), "new publisher must not be closed");
        check(p.getNumberOfSubscribers() == 0, "new publisher must have no subscribers");
        check(!p.hasSubscribers(), "new publisher must report no subscribers");
        check(p.getSubscribers().isEmpty(), "subscriber list must be empty");

        List<Integer> received = new ArrayList<>();
        CountDownLatch done = new CountDownLatch(1);

        p.subscribe(new Flow.Subscriber<Integer>() {
            @Override
            public void onSubscribe(Flow.Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer item) {
                synchronized (received) {
                    received.add(item);
                }
            }

            @Override
            public void onError(Throwable t) {
                throw new RuntimeException("unexpected onError", t);
            }

            @Override
            public void onComplete() {
                done.countDown();
            }
        });

        for (int i = 0; i < 3; i++) {
            p.submit(i);
        }
        p.close();

        check(done.await(10, TimeUnit.SECONDS), "onComplete not received in time");
        check(p.isClosed(), "publisher must be closed after close()");
        synchronized (received) {
            check(received.size() == 3, "expected 3 items but got " + received.size());
            check(received.get(0) == 0 && received.get(1) == 1 && received.get(2) == 2,
                  "items out of order: " + received);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
