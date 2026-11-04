/* @test    @summary Test for SoftCleanable.enqueue() method to verify exception behavior    @run main SoftCleanableWitupTest */

import java.lang.UnsupportedOperationException;

public class SoftCleanableWitupTest {

    public static void main(String[] args) {
        testEnqueueThrowsException();
    }

    public static void testEnqueueThrowsException() {
        try {
            // Cria uma instância anônima de SoftCleanable, pois o construtor é protegido e não acessível
            jdk.internal.ref.SoftCleanable<?> instance = new jdk.internal.ref.SoftCleanable<Object>() {
                @Override
                public boolean enqueue() {
                    return super.enqueue();
                }
            };
            // Invoca o método enqueue, que deve lançar UnsupportedOperationException
            instance.enqueue();
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Teste bem-sucedido, a exceção foi lançada
            System.out.println("enqueue() correctly threw UnsupportedOperationException.");
        } catch (Exception e) {
            // Se outra exceção for lançada, o teste falha
            throw new AssertionError("Unexpected exception thrown: " + e, e);
        }
    }
}
