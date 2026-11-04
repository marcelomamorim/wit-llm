/*
 * @test
 * @summary Test for getChar method in UnsafeStaticShortFieldAccessorImpl
 * @run main UnsafeStaticShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticShortFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsIllegalArgumentException();
    }

    public static void testGetCharThrowsIllegalArgumentException() {
        try {
            // Cria uma instância de UnsafeStaticShortFieldAccessorImpl com um Field fictício
            // Como não há construtor público, podemos usar reflexão para obter um campo existente
            Field dummyField = String.class.getDeclaredFields()[0];
            jdk.internal.reflect.UnsafeStaticShortFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeStaticShortFieldAccessorImpl(dummyField);
            // Chama getChar, que deve lançar IllegalArgumentException
            accessor.getChar(new Object());
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Esperado, o teste passa
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t, t);
        }
    }
}
