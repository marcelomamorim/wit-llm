/* @test
   @summary Testa o método subscribe de SubmissionPublisher para comportamento esperado.
   @run main SubmissionPublisherWitupTest_Pf4352327f8b9
 */
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow.Subscriber;

public class SubmissionPublisherWitupTest_Pf4352327f8b9 {
    public static void main(String[] args) {
        testSubscribeWithNullSubscriber();
        testSubscribeWithValidSubscriber();
    }

    static void testSubscribeWithNullSubscriber() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        try {
            publisher.subscribe(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testSubscribeWithValidSubscriber() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        DummySubscriber subscriber = new DummySubscriber();
        publisher.subscribe(subscriber);
        // Verifica se o subscriber foi adicionado à lista de clientes
        if (!subscriber.isSubscribed()) {
            throw new AssertionError("Subscriber was not properly subscribed");
        }
        publisher.close();
    }

    static class DummySubscriber implements Subscriber<Object> {
        private boolean subscribed = false;
        @Override
        public void onSubscribe(java.util.concurrent.Flow.Subscription subscription) {
            subscribed = true;
        }
        @Override
        public void onNext(Object item) { }
        @Override
        public void onError(Throwable throwable) { }
        @Override
        public void onComplete() { }
        public boolean isSubscribed() { return subscribed; }
    }
}
