/*
 * @test
 * @summary Test for getByte method in UnsafeQualifiedStaticDoubleFieldAccessorImpl
 * @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetByteThrowsIllegalArgumentException();
    }

    public static void testGetByteThrowsIllegalArgumentException() {
        try {
            // Cria uma instância da classe alvo usando reflexão
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticDoubleFieldAccessorImpl");
            Field field = clazz.getDeclaredField("field"); // campo fictício, apenas para instanciar
            // Como o construtor é package-private, não podemos acessá-lo diretamente, então usamos reflexão
            java.lang.reflect.Constructor<?> constructor = clazz.getDeclaredConstructor(Field.class, boolean.class);
            constructor.setAccessible(true);
            Object instance = constructor.newInstance(field, false);
            // Invoca o método getByte, que deve lançar IllegalArgumentException
            java.lang.reflect.Method method = clazz.getMethod("getByte", Object.class);
            method.invoke(instance, new Object());
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new AssertionError("Unexpected exception type: " + cause, cause);
            }
            // Exceção esperada, teste passa
        } catch (NoSuchMethodException | NoSuchFieldException | InstantiationException | IllegalAccessException e) {
            throw new AssertionError("Reflection setup failed: " + e, e);
        }
    }
}
