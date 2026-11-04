/* @test    @summary Test getShort method of UnsafeStaticObjectFieldAccessorImpl class    @run main UnsafeStaticObjectFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetShortReturnsExpected();
    }

    private static void testGetShortReturnsExpected() throws Exception {
        // Create a dummy subclass to access the method
        class Dummy extends jdk.internal.reflect.UnsafeStaticObjectFieldAccessorImpl {
            Dummy(Field field) {
                super(field);
            }
        }

        // Obtain a Field object for a static short field
        // Since the actual class does not have such a field, we create a dummy class for testing
        class DummyClass {
            public static short testField = 123;
        }

        Field field = DummyClass.class.getDeclaredField("testField");
        // Instantiate the accessor with the field
        Dummy accessor = new Dummy(field);

        // Call getShort and verify the returned value
        short result = accessor.getShort(null);
        if (result != 123) {
            throw new AssertionError("Expected 123, but got " + result);
        }
    }
}
