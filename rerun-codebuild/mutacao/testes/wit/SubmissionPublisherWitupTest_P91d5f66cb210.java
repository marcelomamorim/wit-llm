/*
 * @test
 * @summary Test for SubmissionPublisher.subscribe(Subscriber<? super T> subscriber)
 * @run main SubmissionPublisherWitupTest_P91d5f66cb210
 */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow;

public class SubmissionPublisherWitupTest_P91d5f66cb210 {

    public static void main(String[] args) {
        testSubscribeWithNullSubscriber();
        testSubscribeWithValidSubscriber();
    }

    static void testSubscribeWithNullSubscriber() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        try {
            publisher.subscribe(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    static void testSubscribeWithValidSubscriber() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        Flow.Subscriber<Object> subscriber = new Flow.Subscriber<Object>() {
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                // no-op
            }
            @Override
            public void onNext(Object item) {
                // no-op
            }
            @Override
            public void onError(Throwable throwable) {
                // no-op
            }
            @Override
            public void onComplete() {
                // no-op
            }
        };
        try {
            publisher.subscribe(subscriber);
            System.out.println("PASS: subscribe with valid subscriber succeeded");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
