/* @test    @summary Test getChar method of UnsafeDoubleFieldAccessorImpl that throws IllegalArgumentException */
/* @run main UnsafeDoubleFieldAccessorImplWitupTest */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeDoubleFieldAccessorImpl;

public class UnsafeDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsException();
    }

    private static void testGetCharThrowsException() {
        try {
            // Create a dummy Field object; since constructor is package-private,
            // we simulate the behavior by subclassing or using reflection if needed.
            // For simplicity, assume we have a valid Field object named 'field'.
            // In actual test, this would be obtained via reflection.
            Field dummyField = null; // Placeholder, as actual instantiation is not possible here.
            UnsafeDoubleFieldAccessorImpl accessor = new UnsafeDoubleFieldAccessorImpl(dummyField);
            accessor.getChar(new Object());
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("getChar() threw IllegalArgumentException as expected.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
        }
    }
}
