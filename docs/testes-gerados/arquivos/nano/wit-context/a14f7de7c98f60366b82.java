/*
 * @test
 * @summary Test for getLong method in UnsafeStaticFloatFieldAccessorImpl
 * @run main UnsafeStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetLongThrowsIllegalArgumentException();
    }

    private static void testGetLongThrowsIllegalArgumentException() throws Exception {
        // Cria um campo fictício para passar como parâmetro, não é necessário que seja válido
        Field dummyField = null;
        // Instancia a classe alvo com um campo fictício
        jdk.internal.reflect.UnsafeStaticFloatFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeStaticFloatFieldAccessorImpl(dummyField);
        try {
            // Chama o método getLong com um objeto qualquer
            accessor.getLong(new Object());
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Exceção esperada, o teste passa
        }
    }
}
