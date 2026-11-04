/*
 * @test
 * @summary Test for getLong method in UnsafeQualifiedBooleanFieldAccessorImpl
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetLongThrowsIllegalArgumentException();
    }

    public static void testGetLongThrowsIllegalArgumentException() {
        try {
            // Cria um objeto de uma classe dummy para passar como parâmetro
            Object dummyObj = new Object();
            // Obtém o método getLong da classe alvo
            jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl accessor = 
                new jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl(null, false);
            // Invoca o método com um objeto inválido
            accessor.getLong(dummyObj);
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Teste bem-sucedido se a exceção for lançada
            System.out.println("Caught expected IllegalArgumentException");
        } catch (Throwable t) {
            // Qualquer outra exceção indica falha
            throw new AssertionError("Unexpected exception: " + t, t);
        }
    }
}
