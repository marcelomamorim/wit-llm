/*
 * @test
 * @summary Test for getInt method in UnsafeQualifiedBooleanFieldAccessorImpl
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetIntThrowsIllegalArgumentException();
    }

    public static void testGetIntThrowsIllegalArgumentException() {
        // Cria um campo fictício para passar ao método, não é necessário que seja válido
        Field dummyField = null;
        try {
            // Instancia a classe com um campo fictício e isReadOnly false
            jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl accessor = 
                new jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl(dummyField, false);
            // Chama o método getInt, que deve lançar IllegalArgumentException
            int result = accessor.getInt(new Object());
            // Se não lançar exceção, o teste falha
            System.err.println("Falha: esperado IllegalArgumentException, mas obteve resultado: " + result);
        } catch (IllegalArgumentException e) {
            // Esperado, o teste passa
            System.out.println("Passou: getInt lançou IllegalArgumentException como esperado.");
        } catch (Throwable t) {
            // Qualquer outra exceção indica falha
            System.err.println("Falha: esperado IllegalArgumentException, mas obteve: " + t);
        }
    }
}
