/*
 * @test
 * @summary Test for getDouble method in UnsafeQualifiedStaticBooleanFieldAccessorImpl
 * @run main UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetDoubleThrowsIllegalArgumentException();
    }

    public static void testGetDoubleThrowsIllegalArgumentException() {
        try {
            // Cria uma instância da classe alvo usando reflexão
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticBooleanFieldAccessorImpl");
            Field field = clazz.getDeclaredField("field"); // campo fictício, apenas para instanciar
            boolean isReadOnly = false;
            Object instance = clazz.getConstructor(Field.class, boolean.class).newInstance(field, isReadOnly);
            // Invoca o método getDouble, que deve lançar IllegalArgumentException
            clazz.getMethod("getDouble", Object.class).invoke(instance, new Object());
            // Se não lançar exceção, falha o teste
            System.err.println("Failed: Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("Passed: IllegalArgumentException was thrown as expected.");
            } else {
                System.err.println("Failed: Unexpected exception: " + cause);
            }
        } catch (Exception e) {
            System.err.println("Failed: Exception during test execution: " + e);
        }
    }
}
