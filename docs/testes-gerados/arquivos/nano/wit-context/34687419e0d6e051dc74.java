/*
 * @test
 * @summary Testa o método getInt de UnsafeQualifiedLongFieldAccessorImpl para verificar comportamento esperado.
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetIntThrowsIllegalArgumentException();
    }

    private static void testGetIntThrowsIllegalArgumentException() throws Exception {
        // Cria um objeto de classe anônima para simular o objeto de entrada
        Object obj = new Object();
        // Obtém o método getInt da classe alvo
        jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl(null, false);
        try {
            accessor.getInt(obj);
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Teste bem-sucedido se a exceção for lançada
        }
    }
}
