/* @test    @summary Test for getChar method in UnsafeQualifiedStaticIntegerFieldAccessorImpl
 * @run main UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsIllegalArgumentException();
    }

    public static void testGetCharThrowsIllegalArgumentException() {
        try {
            // Cria uma instância da classe alvo com reflexão, se necessário
            // Como o método não depende de estado interno, podemos invocá-lo diretamente
            jdk.internal.reflect.UnsafeQualifiedStaticIntegerFieldAccessorImpl instance = createInstance();
            // Invoca o método getChar, que deve lançar IllegalArgumentException
            instance.getChar(new Object());
            System.err.println("FAIL: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException was correctly thrown.");
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static jdk.internal.reflect.UnsafeQualifiedStaticIntegerFieldAccessorImpl createInstance() {
        // Como o construtor é package-private, não podemos instanciá-lo diretamente.
        // Como o método não depende de estado, podemos usar reflexão para criar uma instância.
        try {
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticIntegerFieldAccessorImpl");
            java.lang.reflect.Constructor<?> ctor = clazz.getDeclaredConstructor(java.lang.reflect.Field.class, boolean.class);
            ctor.setAccessible(true);
            // Passa null para Field e false para isReadOnly, pois o método não usa esses atributos para lançar exceções
            return (jdk.internal.reflect.UnsafeQualifiedStaticIntegerFieldAccessorImpl) ctor.newInstance((Field) null, false);
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate UnsafeQualifiedStaticIntegerFieldAccessorImpl", e);
        }
    }
}
