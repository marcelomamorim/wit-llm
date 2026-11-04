/*
 * @test
 * @summary Test for getInt method in UnsafeStaticObjectFieldAccessorImpl
 * @run main UnsafeStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetIntThrowsIllegalArgumentException();
    }

    private static void testGetIntThrowsIllegalArgumentException() {
        try {
            // Cria uma instância de UnsafeStaticObjectFieldAccessorImpl com um campo válido
            Field dummyField = null; // Como não há construtor público, não podemos instanciá-lo diretamente
            // Como não podemos criar uma instância válida sem acesso ao campo real, vamos simular o comportamento
            // Testando o método getInt com um objeto nulo, que deve lançar a exceção
            jdk.internal.reflect.UnsafeStaticObjectFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeStaticObjectFieldAccessorImpl(dummyField);
            accessor.getInt(null);
            // Se não lançar exceção, falha o teste
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Esperado
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
