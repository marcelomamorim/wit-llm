/* @test
   @summary Testa o método closeExceptionally para verificar o lançamento de NullPointerException ao passar null.
   @run main SubmissionPublisherWitupTest_P7668b1225223
 */
import java.util.concurrent.SubmissionPublisher;
public class SubmissionPublisherWitupTest_P7668b1225223 {
    public static void main(String[] args) {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        try {
            publisher.closeExceptionally(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // esperado
        }
    }
}
