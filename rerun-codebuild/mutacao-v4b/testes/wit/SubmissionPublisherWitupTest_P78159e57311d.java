/* @test
   @summary Testa o método consume de SubmissionPublisher para comportamento normal e exceções.
   @run main SubmissionPublisherWitupTest_P78159e57311d
 */
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Consumer;

public class SubmissionPublisherWitupTest_P78159e57311d {
    public static void main(String[] args) throws Exception {
        testConsumeWithValidConsumer();
        testConsumeWithNullConsumer();
    }

    private static void testConsumeWithValidConsumer() throws Exception {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        Consumer<Integer> consumer = (item) -> { /* no-op */ };
        CompletableFuture<Void> future = publisher.consume(consumer);
        if (future == null) {
            throw new AssertionError("consume() retornou null para consumidor válido");
        }
        // Opcional: verificar se o futuro está concluído após assinatura
        // mas como o método é assíncrono, não há garantia de conclusão imediata
        publisher.close();
    }

    private static void testConsumeWithNullConsumer() {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        try {
            publisher.consume(null);
            throw new AssertionError("Esperado NullPointerException ao passar consumer null");
        } catch (NullPointerException e) {
            // esperado
        }
        publisher.close();
    }
}
