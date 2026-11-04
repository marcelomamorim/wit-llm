/*
 * @test
 * @summary Test for getLong method in UnsafeFloatFieldAccessorImpl
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetLongThrowsException();
    }

    public static void testGetLongThrowsException() throws Exception {
        // Cria um campo fictício para passar ao construtor, não é necessário um campo real
        Field dummyField = null;
        try {
            // Instancia a classe com um campo fictício
            jdk.internal.reflect.UnsafeFloatFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeFloatFieldAccessorImpl(dummyField);
            // Invoca o método getLong com um objeto qualquer
            accessor.getLong(new Object());
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Esperado, o teste passa
        } catch (Throwable t) {
            // Se lançar outra exceção, o teste falha
            throw new AssertionError("Unexpected exception: " + t, t);
        }
    }
}
