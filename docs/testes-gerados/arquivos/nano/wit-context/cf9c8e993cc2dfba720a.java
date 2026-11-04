/*
 * @test
 * @summary Test for getBoolean method in UnsafeQualifiedStaticByteFieldAccessorImpl
 * @run main UnsafeQualifiedStaticByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticByteFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrowsIllegalArgumentException();
    }

    public static void testGetBooleanThrowsIllegalArgumentException() {
        try {
            // Instancia do objeto da classe alvo, usando reflexão
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticByteFieldAccessorImpl");
            Field field = clazz.getDeclaredField("field"); // campo fictício, substitua se necessário
            boolean isReadOnly = false; // valor fictício, ajuste conforme contexto
            Object accessor = clazz.getConstructor(Field.class, boolean.class).newInstance(field, isReadOnly);
            // Chamada do método getBoolean, que deve lançar IllegalArgumentException
            ((java.lang.reflect.Method)accessor.getClass().getMethod("getBoolean", Object.class)).invoke(accessor, new Object());
            // Se não lançar exceção, falha
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new AssertionError("Unexpected exception type: " + cause, cause);
            }
            // Exceção esperada, teste passa
        } catch (NoSuchMethodException | ClassNotFoundException | NoSuchFieldException | InstantiationException | IllegalAccessException e) {
            throw new AssertionError("Test setup failed: " + e, e);
        }
    }
}
