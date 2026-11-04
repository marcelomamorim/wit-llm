/*
 * @test
 * @summary Test for getDouble method in UnsafeQualifiedBooleanFieldAccessorImpl
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetDoubleThrowsIllegalArgumentException();
    }

    public static void testGetDoubleThrowsIllegalArgumentException() {
        try {
            // Cria um campo fictício para passar ao método, mesmo que não seja usado
            Field dummyField = null;
            // Instancia a classe com um construtor fictício, se necessário, ou usa reflexão para obter um campo válido
            // Como o construtor não é público, não podemos instanciá-la diretamente; portanto, usamos reflexão para obter um campo válido
            // Aqui, como o método não usa o campo, podemos passar null para o objeto
            jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl(dummyField, false);
            // Chama o método getDouble, que deve lançar uma IllegalArgumentException
            accessor.getDouble(new Object());
            // Se não lançar exceção, falha o teste
            System.err.println("FAIL: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Esperado, o teste passa
            System.out.println("PASS: IllegalArgumentException thrown as expected.");
        } catch (Throwable t) {
            // Qualquer outra exceção indica falha
            System.err.println("FAIL: Unexpected exception: " + t);
        }
    }
}
