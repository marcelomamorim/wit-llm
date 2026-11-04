/*
 * @test
 * @summary Test for getBoolean method in UnsafeQualifiedStaticIntegerFieldAccessorImpl.
 * @run main UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrowsException();
    }

    public static void testGetBooleanThrowsException() {
        try {
            // Instancia do objeto da classe alvo, usando reflexão para obter o método
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticIntegerFieldAccessorImpl");
            // Como o construtor é package-private, usar reflexão para acessá-lo
            Field field = clazz.getDeclaredField("field"); // campo fictício, pois não há campo 'field' na classe, apenas método
            // Como não há construtor público, instanciamos via reflexão se necessário
            // Mas neste caso, o método é estático e não depende de instância
            // Portanto, não é necessário criar uma instância
            // Invoca o método getBoolean com null, pois o método não usa o estado do objeto
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getBoolean", Object.class);
            method.setAccessible(true);
            // Chamada do método, esperando uma exceção
            method.invoke(null, new Object());
            // Se não lançar exceção, falha o teste
            System.err.println("FAIL: getBoolean did not throw IllegalArgumentException as expected.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("PASS: getBoolean threw IllegalArgumentException as expected.");
            } else {
                System.err.println("FAIL: getBoolean threw unexpected exception: " + cause);
            }
        } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException e) {
            System.err.println("ERROR: Reflection error during test: " + e);
        }
    }
}
