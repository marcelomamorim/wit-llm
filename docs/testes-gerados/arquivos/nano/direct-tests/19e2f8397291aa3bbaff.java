/* @test    @summary Test getBoolean method of UnsafeStaticDoubleFieldAccessorImpl that always throws IllegalArgumentException.    @run main UnsafeStaticDoubleFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        try {
            testGetBooleanThrowsException();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("All tests passed.");
    }

    public static void testGetBooleanThrowsException() {
        // Cria uma instância de UnsafeStaticDoubleFieldAccessorImpl com um campo fictício
        // Como o construtor é package-private, usar reflexão para acessá-lo
        try {
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeStaticDoubleFieldAccessorImpl");
            Field field = clazz.getDeclaredConstructor(Field.class).newInstance((Field) null);
            // Invoca o método getBoolean, que deve lançar IllegalArgumentException
            try {
                Object result = (Boolean) clazz.getMethod("getBoolean", Object.class).invoke(field, (Object) null);
                // Se não lançar exceção, falha
                throw new AssertionError("Expected IllegalArgumentException was not thrown.");
            } catch (java.lang.reflect.InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (!(cause instanceof IllegalArgumentException)) {
                    throw new AssertionError("Unexpected exception cause: " + cause, cause);
                }
                // Exceção esperada, teste bem-sucedido
            }
        } catch (ReflectiveOperationException e) {
            throw new AssertionError("Reflection error during test setup", e);
        }
    }
}
