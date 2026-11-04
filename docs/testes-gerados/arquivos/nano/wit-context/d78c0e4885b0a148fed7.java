/*
 * @test
 * @summary Testa o método getShort para verificar comportamento esperado e exceções.
 * @run main UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetShortThrowsException();
    }

    private static void testGetShortThrowsException() {
        try {
            // Como o método getShort sempre lança uma exceção, testamos se ela é lançada.
            // Passar null como objeto, pois o método não usa o parâmetro.
            jdk.internal.reflect.UnsafeQualifiedStaticCharacterFieldAccessorImpl instance = null;
            // Invoca o método via reflexão para evitar dependência direta.
            // Como o método é package-private, usar reflexão é necessário.
            java.lang.reflect.Method method = jdk.internal.reflect.UnsafeQualifiedStaticCharacterFieldAccessorImpl.class.getDeclaredMethod("getShort", Object.class);
            method.setAccessible(true);
            method.invoke(instance, new Object());
            // Se não lançar exceção, falha o teste.
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new AssertionError("Unexpected exception type: " + cause, cause);
            }
            // Exceção esperada, teste passou.
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception during test execution", e);
        }
    }
}
