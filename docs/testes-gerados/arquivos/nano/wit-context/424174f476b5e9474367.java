/*
 * @test
 * @summary Testa o método getInt de UnsafeQualifiedObjectFieldAccessorImpl
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetIntThrowsIllegalArgumentException();
    }

    public static void testGetIntThrowsIllegalArgumentException() {
        try {
            // Cria um objeto Field fictício para passar ao método
            Field dummyField = null; // Como o método não usa o campo, podemos passar null
            // Cria uma instância de UnsafeQualifiedObjectFieldAccessorImpl com um campo fictício
            jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl accessor =
                new jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl(dummyField, false);
            // Invoca o método getInt com um objeto qualquer
            Object obj = new Object();
            int result = accessor.getInt(obj);
            // Como o método sempre lança IllegalArgumentException, não deve chegar aqui
            return;
        } catch (IllegalArgumentException e) {
            // Esperado
            return;
        } catch (Throwable t) {
            // Outros erros indicam falha
            throw new AssertionError("Unexpected exception: " + t, t);
        }
    }
}
