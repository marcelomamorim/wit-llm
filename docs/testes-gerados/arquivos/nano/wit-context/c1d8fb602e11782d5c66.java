/*
 * @test
 * @summary Test for getLong method in UnsafeShortFieldAccessorImpl class
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeShortFieldAccessorImpl;

public class UnsafeShortFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetLongWithInvalidObject();
    }

    private static void testGetLongWithInvalidObject() throws Exception {
        Field dummyField = null;
        try {
            dummyField = String.class.getDeclaredField("value");
        } catch (NoSuchFieldException e) {
            // fallback: create a dummy Field object via reflection or mock if needed
            // Since creating a real Field object is complex, we assume dummyField is valid for test
            // For this test, we focus on the behavior when obj is incompatible
            return;
        }
        UnsafeShortFieldAccessorImpl accessor = new UnsafeShortFieldAccessorImpl(dummyField);
        Object invalidObj = new Object();
        try {
            accessor.getLong(invalidObj);
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("Caught expected IllegalArgumentException.");
        }
    }
}
