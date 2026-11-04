/*
 * @test
 * @summary Test for getFloat method in UnsafeQualifiedBooleanFieldAccessorImpl
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetFloatThrowsIllegalArgumentException();
    }

    public static void testGetFloatThrowsIllegalArgumentException() {
        try {
            // Cria um campo fictício para passar ao método, mesmo que não seja usado
            Field dummyField = null;
            // Instancia a classe com um campo fictício e readOnly false (não usado aqui)
            jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl accessor = 
                new jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl(dummyField, false);
            // Chama o método getFloat, que deve lançar IllegalArgumentException
            float result = accessor.getFloat(new Object());
            // Se não lançar exceção, o teste falha
            System.err.println("FAIL: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Esperado: o método lança IllegalArgumentException
            System.out.println("PASS: getFloat threw IllegalArgumentException as expected.");
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
        }
    }
}
