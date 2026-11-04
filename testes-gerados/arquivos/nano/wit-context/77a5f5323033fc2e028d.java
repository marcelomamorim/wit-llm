/*
 * @test
 * @summary Test for getBoolean method in UnsafeQualifiedStaticShortFieldAccessorImpl
 * @run main UnsafeQualifiedStaticShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticShortFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrowsException();
    }

    public static void testGetBooleanThrowsException() {
        try {
            // Cria uma instância fictícia do objeto para invocar o método
            // Como o método não depende de estado interno, podemos passar null
            jdk.internal.reflect.UnsafeQualifiedStaticShortFieldAccessorImpl instance = createInstance();
            boolean result = instance.getBoolean(new Object());
            // Como o método lança exceção, se chegar aqui, o teste falhou
            System.err.println("FAIL: getBoolean() did not throw exception as expected.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Esperado: a exceção deve ser lançada
            System.out.println("PASS: getBoolean() threw IllegalArgumentException as expected.");
        } catch (Throwable t) {
            System.err.println("FAIL: getBoolean() threw unexpected exception: " + t);
            System.exit(1);
        }
    }

    private static jdk.internal.reflect.UnsafeQualifiedStaticShortFieldAccessorImpl createInstance() {
        // Como o construtor é package-private, não podemos instanciá-lo diretamente.
        // Para fins de teste, podemos usar reflexão para criar uma instância.
        try {
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticShortFieldAccessorImpl");
            java.lang.reflect.Constructor<?> ctor = clazz.getDeclaredConstructor(Field.class, boolean.class);
            ctor.setAccessible(true);
            // Passa null para Field e false para isReadOnly, pois não usamos esses atributos no método
            return (jdk.internal.reflect.UnsafeQualifiedStaticShortFieldAccessorImpl) ctor.newInstance((Object) null, false);
        } catch (Exception e) {
            System.err.println("FAIL: Unable to instantiate UnsafeQualifiedStaticShortFieldAccessorImpl: " + e);
            System.exit(1);
            return null; // Nunca chega aqui
        }
    }
}
