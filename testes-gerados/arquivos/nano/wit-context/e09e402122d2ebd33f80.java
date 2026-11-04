/*
 * @test
 * @summary Test for getInt method in UnsafeQualifiedStaticLongFieldAccessorImpl
 * @run main UnsafeQualifiedStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticLongFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetIntThrowsIllegalArgumentException();
    }

    public static void testGetIntThrowsIllegalArgumentException() {
        try {
            // Cria uma instância da classe alvo usando reflexão
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticLongFieldAccessorImpl");
            Field field = clazz.getDeclaredField("field"); // campo fictício, apenas para obter a classe
            // Como o método getInt lança IllegalArgumentException sempre, apenas invoca para verificar se lança
            Object instance = null; // método é estático, portanto, null
            clazz.getMethod("getInt", Object.class).invoke(instance, new Object());
            // Se não lançar exceção, falha
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new AssertionError("Unexpected exception type: " + cause, cause);
            }
            // Exceção esperada, teste passa
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception during test execution", e);
        }
    }
}
