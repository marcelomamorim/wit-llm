/*
 * @test
 * @summary Test for getShort method in UnsafeQualifiedStaticIntegerFieldAccessorImpl
 * @run main UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetShortThrowsIllegalArgumentException();
    }

    public static void testGetShortThrowsIllegalArgumentException() {
        try {
            // Cria uma instância da classe alvo usando reflexão
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticIntegerFieldAccessorImpl");
            Field field = clazz.getDeclaredField("field"); // campo fictício, apenas para instância
            boolean isReadOnly = false;
            Object accessor = clazz.getConstructor(Field.class, boolean.class).newInstance(field, isReadOnly);
            // Invoca o método getShort
            clazz.getMethod("getShort", Object.class).invoke(accessor, new Object());
            // Se não lançar exceção, o teste falha
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (Throwable t) {
            Throwable cause = t instanceof java.lang.reflect.InvocationTargetException ? t.getCause() : t;
            if (cause instanceof IllegalArgumentException) {
                // Teste passou
                System.out.println("getShort() threw IllegalArgumentException as expected.");
                System.exit(0);
            } else {
                System.err.println("Unexpected exception: " + cause);
                System.exit(1);
            }
        }
    }
}
