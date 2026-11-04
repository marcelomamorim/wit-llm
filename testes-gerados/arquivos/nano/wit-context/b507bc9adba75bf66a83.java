/*
 * @test
 * @summary Test for ReferencePipeline.toArray() method, focusing on exception path.
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testToArrayLinkedOrConsumed();
    }

    private static void testToArrayLinkedOrConsumed() {
        try {
            // Cria um pipeline que simula o estado 'linkedOrConsumed' para disparar IllegalStateException
            // Como não há construtor público, usamos uma abordagem indireta: criar um pipeline que já esteja consumido
            // ou ligado, por exemplo, usando uma stream já consumida.
            java.util.stream.Stream<Integer> stream = java.util.stream.Stream.of(1, 2, 3);
            // Consumindo a stream para simular estado 'linkedOrConsumed'
            stream.forEach(e -> {});
            // Tentando chamar toArray após consumo
            Object[] result = ReferencePipeline.toArray();
            // Se não lançar exceção, falha o teste
            System.err.println("Expected IllegalStateException was not thrown.");
            System.exit(1);
        } catch (IllegalStateException e) {
            // Esperado
            System.out.println("Caught expected IllegalStateException.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
