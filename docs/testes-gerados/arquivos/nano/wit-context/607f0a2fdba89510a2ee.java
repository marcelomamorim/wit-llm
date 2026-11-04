/*
 * @test
 * @summary Test for getInt method in UnsafeQualifiedDoubleFieldAccessorImpl
 * @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetIntThrowsIllegalArgumentException();
    }

    private static void testGetIntThrowsIllegalArgumentException() throws Exception {
        // Cria um campo fictício para passar ao construtor, usando reflexão
        Field dummyField = null;
        try {
            dummyField = UnsafeQualifiedDoubleFieldAccessorImpl.class.getDeclaredField("field");
        } catch (NoSuchFieldException e) {
            // Como o campo 'field' não existe, criamos um mock ou um campo fictício
            // Como não podemos criar um campo real, vamos usar reflexão para obter um campo existente
            // ou criar uma subclasse que simule o comportamento. Aqui, optamos por usar reflexão direta.
            // Como o método não usa o campo, podemos passar null para o construtor.
            dummyField = null;
        }
        // Instancia o objeto com o construtor padrão, passando null para o campo
        jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl accessor = 
            new jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl(dummyField, false);
        // Invoca o método getInt, que deve lançar IllegalArgumentException
        try {
            accessor.getInt(new Object());
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Teste passou, exceção esperada
        }
    }
}
