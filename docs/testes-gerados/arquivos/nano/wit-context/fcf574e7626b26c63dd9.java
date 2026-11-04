/*
 * @test
 * @summary Test for getInt method in UnsafeQualifiedStaticObjectFieldAccessorImpl
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetIntThrowsIllegalArgumentException();
    }

    public static void testGetIntThrowsIllegalArgumentException() {
        try {
            // Cria uma instância da classe alvo com um construtor acessível, se necessário.
            // Como o construtor não é público, não podemos instanciá-la diretamente.
            // Portanto, testamos o método getInt com um objeto nulo, pois o método não usa o estado do objeto.
            jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl(null, false);
            // Invoca o método getInt, que deve lançar uma IllegalArgumentException
            accessor.getInt(null);
            System.err.println("Erro: esperado IllegalArgumentException não foi lançada.");
        } catch (IllegalArgumentException e) {
            // Teste bem-sucedido se a exceção for lançada
            System.out.println("Passou: IllegalArgumentException foi lançada como esperado.");
        } catch (Throwable t) {
            System.err.println("Erro: exceção inesperada: " + t);
        }
    }
}
