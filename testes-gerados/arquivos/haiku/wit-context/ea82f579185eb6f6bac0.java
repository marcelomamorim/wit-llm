/*
 * @test
 * @summary WITUP-derived checks for SubmissionPublisher(Executor, int) argument validation
 * @run main SubmissionPublisherWitupTest
 */

import java.util.concurrent.Executor;
import java.util.concurrent.SubmissionPublisher;

public class SubmissionPublisherWitupTest {

    interface Action {
        void run() throws Exception;
    }

    static void expectNpe(Action action) {
        try {
            action.run();
        } catch (NullPointerException expected) {
            return;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception: " + e, e);
        }
        throw new RuntimeException("Expected NullPointerException");
    }

    static void expectIae(Action action) {
        try {
            action.run();
        } catch (IllegalArgumentException expected) {
            return;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception: " + e, e);
        }
        throw new RuntimeException("Expected IllegalArgumentException");
    }

    public static void main(String[] args) {
        Executor direct = Runnable::run;

        expectNpe(() -> new SubmissionPublisher<String>(null, 8));
        expectIae(() -> new SubmissionPublisher<String>(direct, 0));
        expectIae(() -> new SubmissionPublisher<String>(direct, -1));

        SubmissionPublisher<String> publisher = new SubmissionPublisher<>(direct, 1);
        if (publisher == null) {
            throw new RuntimeException("Constructor returned null for valid arguments");
        }
        publisher.close();
    }
}
