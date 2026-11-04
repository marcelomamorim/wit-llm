/*
 * @test
 * @summary Test for getShort method in UnsafeQualifiedStaticFloatFieldAccessorImpl
 * @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetShortThrowsIllegalArgumentException();
    }

    public static void testGetShortThrowsIllegalArgumentException() {
        try {
            // Cria uma instância fictícia do objeto, já que o método não usa o parâmetro
            Object obj = null;
            jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl accessor = null;
            // Como o método é estático e não depende de estado, podemos invocá-lo via reflexão
            java.lang.reflect.Method method = jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl.class.getDeclaredMethod("getShort", Object.class);
            method.setAccessible(true);
            method.invoke(null, obj);
            // Se não lançar exceção, o teste falha
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
