/*
 * @test
 * @summary Test for getByte method in UnsafeStaticFloatFieldAccessorImpl
 * @run main UnsafeStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetByteThrowsIllegalArgumentException();
    }

    private static void testGetByteThrowsIllegalArgumentException() throws Exception {
        // Cria um campo fictício para passar ao método, mesmo que não seja utilizado
        Field dummyField = null;
        // Cria uma instância do objeto alvo usando reflexão, se necessário
        // Como o método não usa o estado do objeto, podemos passar null
        jdk.internal.reflect.UnsafeStaticFloatFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeStaticFloatFieldAccessorImpl(dummyField);
        try {
            accessor.getByte(new Object());
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Exceção esperada, teste passa
        }
    }
}
