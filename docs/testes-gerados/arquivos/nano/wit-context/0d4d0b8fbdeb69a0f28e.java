/*
 * @test
 * @summary Test for getChar method in UnsafeQualifiedObjectFieldAccessorImpl
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsIllegalArgumentException();
    }

    private static void testGetCharThrowsIllegalArgumentException() {
        // Cria um campo fictício para passar ao método, pois o método não usa o valor do campo
        Field dummyField = null;
        try {
            // Como o método getChar não usa o valor de 'field', podemos passar null
            jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl accessor = 
                new jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl(dummyField, false);
            Object obj = new Object();
            // Espera que o método lance IllegalArgumentException
            boolean exceptionThrown = false;
            try {
                accessor.getChar(obj);
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                System.err.println("Failed: getChar did not throw IllegalArgumentException as expected.");
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Unexpected exception during test: " + e);
            System.exit(1);
        }
    }
}
