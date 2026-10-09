/* @test
   @summary Verifies the subscribe method behavior of SubmissionPublisher.
   @run main SubmissionPublisherWitupTest_Pf4352327f8b9
 */
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class SubmissionPublisherWitupTest_Pf4352327f8b9 {
    static class DummySubscriber implements Subscriber<Object> {
        boolean subscribed = false;
        boolean errorOccurred = false;
        boolean completed = false;
        Throwable error;
        Subscription subscription;

        @Override
        public void onSubscribe(Subscription s) {
            this.subscription = s;
            this.subscribed = true;
            s.request(Long.MAX_VALUE);
        }

        @Override
        public void onNext(Object item) {
            // no-op
        }

        @Override
        public void onError(Throwable t) {
            this.errorOccurred = true;
            this.error = t;
        }

        @Override
        public void onComplete() {
            this.completed = true;
        }
    }

    public static void main(String[] args) {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        DummySubscriber subscriber = new DummySubscriber();
        publisher.subscribe(subscriber);
        assert subscriber.subscribed : "Subscriber should be subscribed";
        // Test that subscribing again with the same subscriber does not throw
        try {
            publisher.subscribe(subscriber);
        } catch (Exception e) {
            throw new AssertionError("Exception on duplicate subscribe", e);
        }
        // Test that subscribing with null throws NullPointerException
        boolean npeThrown = false;
        try {
            publisher.subscribe(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("NullPointerException expected when subscribing null");
        }
        // Clean up
        publisher.close();
    }
}
