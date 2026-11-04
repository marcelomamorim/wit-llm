/*
 * @test
 * @summary Test for getByte method in UnsafeQualifiedStaticLongFieldAccessorImpl
 * @run main UnsafeQualifiedStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticLongFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetByteThrowsIllegalArgumentException();
    }

    public static void testGetByteThrowsIllegalArgumentException() {
        try {
            // Cria uma instância fictícia do objeto, já que o método não usa o parâmetro
            Object obj = null;
            // Invoca o método getByte, que deve lançar IllegalArgumentException
            jdk.internal.reflect.UnsafeQualifiedStaticLongFieldAccessorImpl accessor = null;
            // Como o método é não estático, normalmente precisaríamos de uma instância, mas aqui o método é estático na classe
            // Como o método não depende de estado, podemos invocá-lo diretamente
            jdk.internal.reflect.UnsafeQualifiedStaticLongFieldAccessorImpl.getByte(obj);
            // Se não lançar exceção, o teste falha
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Teste bem-sucedido: exceção esperada
            System.out.println("IllegalArgumentException thrown as expected.");
        } catch (Throwable t) {
            // Se outra exceção for lançada, o teste falha
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
