/* @test
   @summary Testa o método isSubscribed para verificar comportamento com entrada nula.
   @run main SubmissionPublisherWitupTest_Pc42d96f31fd9
 */
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow;
public class SubmissionPublisherWitupTest_Pc42d96f31fd9 {
    public static void main(String[] args) {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        try {
            publisher.isSubscribed(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
