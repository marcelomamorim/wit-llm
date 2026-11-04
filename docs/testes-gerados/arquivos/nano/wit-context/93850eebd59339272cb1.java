/*
 * @test
 * @summary Test for getLong method in UnsafeQualifiedObjectFieldAccessorImpl
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetLongWithNullObject();
        testGetLongWithInvalidObject();
    }

    private static void testGetLongWithNullObject() {
        try {
            // Cria uma instância fictícia da classe, usando reflexão para acessar o construtor
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl");
            Constructor<?> constructor = clazz.getDeclaredConstructor(Field.class, boolean.class);
            constructor.setAccessible(true);
            Field dummyField = null; // Como não temos um campo real, usamos null
            Object accessor = constructor.newInstance(dummyField, false);
            // Invoca getLong com objeto null, espera uma IllegalArgumentException
            ((java.lang.reflect.Method)clazz.getMethod("getLong", Object.class)).invoke(accessor, (Object) null);
            // Se não lançar exceção, falha o teste
            System.err.println("Failed: getLong with null object did not throw exception");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("Passed: getLong with null object threw IllegalArgumentException");
            } else {
                System.err.println("Failed: getLong with null object threw unexpected exception: " + cause);
            }
        } catch (Exception e) {
            System.err.println("Failed: getLong with null object exception: " + e);
        }
    }

    private static void testGetLongWithInvalidObject() {
        try {
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl");
            Constructor<?> constructor = clazz.getDeclaredConstructor(Field.class, boolean.class);
            constructor.setAccessible(true);
            Field dummyField = null; // Como não temos um campo real, usamos null
            Object accessor = constructor.newInstance(dummyField, false);
            // Invoca getLong com um objeto de tipo inválido, espera uma IllegalArgumentException
            ((java.lang.reflect.Method)clazz.getMethod("getLong", Object.class)).invoke(accessor, "string_invalida");
            System.err.println("Failed: getLong with invalid object did not throw exception");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("Passed: getLong with invalid object threw IllegalArgumentException");
            } else {
                System.err.println("Failed: getLong with invalid object threw unexpected exception: " + cause);
            }
        } catch (Exception e) {
            System.err.println("Failed: getLong with invalid object exception: " + e);
        }
    }
}
