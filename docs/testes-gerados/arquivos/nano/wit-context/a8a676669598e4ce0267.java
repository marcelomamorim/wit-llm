/*
 * @test
 * @summary Test for getChar method in UnsafeStaticDoubleFieldAccessorImpl
 * @run main UnsafeStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsIllegalArgumentException();
    }

    public static void testGetCharThrowsIllegalArgumentException() {
        try {
            // Cria uma instância de UnsafeStaticDoubleFieldAccessorImpl com um campo fictício
            // Como não há construtor público, simula-se a criação via reflexão, assumindo que o campo existe
            // e é acessível. Como não há detalhes do campo, a tentativa é de invocar o método e verificar a exceção.
            // Como o método lança a exceção imediatamente, não é necessário criar um campo real.
            // Portanto, apenas invoca-se o método com null, pois o método não usa o parâmetro.
            jdk.internal.reflect.UnsafeStaticDoubleFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeStaticDoubleFieldAccessorImpl(null);
            accessor.getChar(null);
            System.err.println("Erro: expected IllegalArgumentException não foi lançada.");
        } catch (IllegalArgumentException e) {
            System.out.println("Passou: IllegalArgumentException foi lançada como esperado.");
        } catch (Throwable t) {
            System.err.println("Erro inesperado: " + t);
        }
    }
}
