/*
 * @test
 * @summary Test for getInt method in UnsafeQualifiedStaticDoubleFieldAccessorImpl
 * @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetIntThrowsException();
    }

    public static void testGetIntThrowsException() {
        try {
            // Cria uma instância da classe alvo usando reflexão
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticDoubleFieldAccessorImpl");
            Field field = clazz.getDeclaredField("field"); // campo fictício, apenas para obter a classe
            boolean isReadOnly = false;
            Object instance = clazz.getConstructor(Field.class, boolean.class).newInstance(field, isReadOnly);
            // Invoca o método getInt
            clazz.getMethod("getInt", Object.class).invoke(instance, new Object());
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new AssertionError("Unexpected exception type: " + cause, cause);
            }
            // Exceção esperada, teste passa
        } catch (NoSuchMethodException | NoSuchFieldException | InstantiationException | IllegalAccessException e) {
            throw new AssertionError("Reflection setup failed", e);
        }
    }
}
