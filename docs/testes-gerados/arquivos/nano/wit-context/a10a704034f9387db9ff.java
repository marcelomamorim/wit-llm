/*
 * @test
 * @summary Teste do método getFloat para verificar comportamento esperado e exceções.
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetFloatWithNullObject();
        testGetFloatWithInvalidType();
    }

    private static void testGetFloatWithNullObject() {
        try {
            // Cria uma instância da classe alvo com um campo fictício para simular o método
            // Como o método não depende de estado interno, podemos invocá-lo diretamente
            jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl accessor = 
                new jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl(null, false);
            float result = accessor.getFloat(null);
            System.out.println("getFloat com null object: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("getFloat com null object lança IllegalArgumentException" );
        } catch (Throwable t) {
            System.out.println("Erro inesperado em getFloat com null object: " + t);
        }
    }

    private static void testGetFloatWithInvalidType() {
        try {
            jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl accessor = 
                new jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl(null, false);
            // Como o método getFloat não usa o parâmetro obj, podemos passar qualquer objeto
            Object invalidObject = new Object();
            float result = accessor.getFloat(invalidObject);
            System.out.println("getFloat com objeto inválido: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("getFloat com objeto inválido lança IllegalArgumentException" );
        } catch (Throwable t) {
            System.out.println("Erro inesperado em getFloat com objeto inválido: " + t);
        }
    }
}
