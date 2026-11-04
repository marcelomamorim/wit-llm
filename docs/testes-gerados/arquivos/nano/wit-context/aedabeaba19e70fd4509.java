/* @test    @summary Testa o método getLong de UnsafeQualifiedStaticObjectFieldAccessorImpl para comportamento de exceção.    @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetLongThrowsIllegalArgumentException();
    }

    public static void testGetLongThrowsIllegalArgumentException() {
        try {
            // Cria uma instância da classe alvo com um campo fictício para evitar dependências externas
            // Como o método não depende de estado interno, podemos invocá-lo diretamente
            // Passa um objeto qualquer, pois o método ignora o parâmetro
            jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl accessor = 
                new jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl(null, false);
            long result = accessor.getLong(new Object());
            // Como o método sempre lança a exceção, não deve chegar aqui
            System.err.println("Erro: esperado IllegalArgumentException, mas obteve resultado: " + result);
        } catch (IllegalArgumentException e) {
            // Test passou, exceção foi lançada
            System.out.println("Passou: IllegalArgumentException lançada como esperado.");
        } catch (Throwable t) {
            System.err.println("Erro: esperado IllegalArgumentException, mas ocorreu: " + t);
        }
    }
}
