/*
 * @test
 * @summary Testa o método getShort para verificar comportamento esperado e exceções.
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetShortWithNullObject();
        testGetShortWithInvalidType();
    }

    private static void testGetShortWithNullObject() {
        try {
            // Cria uma instância do método usando reflexão, se necessário, ou simula o comportamento
            // Como o método não depende de estado interno, podemos invocá-lo diretamente.
            // Como o método lança exceção imediatamente, apenas chamamos e verificamos a exceção.
            jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl instance = new jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl(null, false);
            instance.getShort(null);
            System.out.println("FAIL: Esperado IllegalArgumentException ao passar null.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: getShort com null objeto lançou IllegalArgumentException.");
        } catch (Throwable t) {
            System.out.println("FAIL: getShort com null objeto lançou exceção inesperada: " + t);
        }
    }

    private static void testGetShortWithInvalidType() {
        try {
            jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl instance = new jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl(null, false);
            Object invalidObject = new Object();
            instance.getShort(invalidObject);
            System.out.println("FAIL: Esperado IllegalArgumentException ao passar objeto de tipo inválido.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: getShort com objeto de tipo inválido lançou IllegalArgumentException.");
        } catch (Throwable t) {
            System.out.println("FAIL: getShort com objeto de tipo inválido lançou exceção inesperada: " + t);
        }
    }
}
