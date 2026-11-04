/*
 * @test
 * @summary Teste do método getInt para verificar comportamento esperado.
 * @run main UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetIntThrowsIllegalArgumentException();
    }

    private static void testGetIntThrowsIllegalArgumentException() {
        try {
            // Cria uma instância da classe alvo usando reflexão
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticBooleanFieldAccessorImpl");
            Field field = clazz.getDeclaredField("field"); // campo fictício, apenas para obter a classe
            boolean isReadOnly = false;
            Object instance = clazz.getConstructor(Field.class, boolean.class).newInstance(field, isReadOnly);
            // Invoca o método getInt, que deve lançar IllegalArgumentException
            clazz.getMethod("getInt", Object.class).invoke(instance, new Object());
            // Se não lançar exceção, falha o teste
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new AssertionError("Unexpected exception type: " + cause, cause);
            }
            // Exceção esperada, teste passa
        } catch (Exception e) {
            throw new AssertionError("Test failed due to unexpected exception: " + e, e);
        }
    }
}
