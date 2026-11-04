/*
 * @test
 * @summary Test for getChar method in UnsafeQualifiedBooleanFieldAccessorImpl
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetCharThrowsIllegalArgumentException();
    }

    public static void testGetCharThrowsIllegalArgumentException() throws Exception {
        // Cria um campo fictício para passar ao método, não é necessário que seja válido,
        // pois o método lança a exceção imediatamente.
        Field dummyField = null;
        boolean isReadOnly = false;
        jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl accessor = 
            new jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl(dummyField, isReadOnly);
        try {
            accessor.getChar(new Object());
            System.out.println("FAIL: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException was correctly thrown.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }
}
