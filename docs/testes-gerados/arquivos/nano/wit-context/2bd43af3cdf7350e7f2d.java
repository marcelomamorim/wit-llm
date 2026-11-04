/*
 * @test
 * @summary Test for getChar method in UnsafeQualifiedStaticFloatFieldAccessorImpl
 * @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsIllegalArgumentException();
    }

    public static void testGetCharThrowsIllegalArgumentException() {
        // Cria uma instância fictícia do objeto, pois o método não usa o parâmetro
        Object obj = null;
        try {
            jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl instance = null;
            // Como o método é estático e não depende de estado, podemos invocá-lo via reflexão
            java.lang.reflect.Method method = jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl.class.getDeclaredMethod("getChar", Object.class);
            method.setAccessible(true);
            method.invoke(instance, obj);
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new AssertionError("Unexpected exception type: " + cause, cause);
            }
            // Exceção esperada, teste passa
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new AssertionError("Reflection error during test execution", e);
        }
    }
}
