/*
 * @test
 * @summary Test for getChar method in UnsafeObjectFieldAccessorImpl
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetCharThrowsIllegalArgumentException();
    }

    public static void testGetCharThrowsIllegalArgumentException() throws Exception {
        // Cria um campo fictício para passar ao método, não é necessário que seja válido
        Field dummyField = null;
        // Cria uma instância de UnsafeObjectFieldAccessorImpl com o campo fictício
        jdk.internal.reflect.UnsafeObjectFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeObjectFieldAccessorImpl(dummyField);
        Object obj = new Object();
        try {
            accessor.getChar(obj);
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Exceção esperada, o teste passa
        }
    }
}
