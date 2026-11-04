/*
 * @test
 * @summary Test for getChar method in UnsafeQualifiedStaticShortFieldAccessorImpl
 * @run main UnsafeQualifiedStaticShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticShortFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsIllegalArgumentException();
    }

    public static void testGetCharThrowsIllegalArgumentException() {
        try {
            // Cria uma instância fictícia do objeto para passar ao método
            Object obj = null;
            // Cria uma instância do método alvo usando reflexão
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticShortFieldAccessorImpl");
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getChar", Object.class);
            method.setAccessible(true);
            // Invoca o método, esperando que lance IllegalArgumentException
            method.invoke(null, obj);
            // Se não lançar exceção, falha o teste
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new AssertionError("Unexpected exception type: " + cause, cause);
            }
            // Exceção esperada, teste passa
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception during test execution: " + e, e);
        }
    }
}
