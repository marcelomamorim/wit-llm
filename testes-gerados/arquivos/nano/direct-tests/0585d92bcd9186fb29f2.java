/* @test    @summary Test default constructor of SubmissionPublisher    @run main SubmissionPublisherWitupTest */

import java.util.concurrent.SubmissionPublisher;

public class SubmissionPublisherWitupTest {

    public static void main(String[] args) {
        testDefaultConstructor();
    }

    public static void testDefaultConstructor() {
        SubmissionPublisher<?> publisher = new SubmissionPublisher<>();
        if (publisher == null) {
            throw new AssertionError("SubmissionPublisher instance is null");
        }
        System.out.println("Default constructor test passed.");
    }
}
