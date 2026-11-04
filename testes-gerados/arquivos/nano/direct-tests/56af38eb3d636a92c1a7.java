/* @test    @summary Test getByte method of UnsafeCharacterFieldAccessorImpl class    @run main UnsafeCharacterFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetByteThrowsException();
    }

    public static void testGetByteThrowsException() {
        class Dummy {
            public byte dummyMethod() { return 0; }
        }
        try {
            Field field = Dummy.class.getDeclaredField("dummyMethod");
            // Simulate the method call as the actual method is not accessible, so we create an instance of the class
            Dummy dummy = new Dummy();
            jdk.internal.reflect.UnsafeCharacterFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeCharacterFieldAccessorImpl(field);
            byte result = accessor.getByte(dummy);
            // Since the method getByte always throws, reaching this line indicates a failure
            System.err.println("Expected exception was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
            System.out.println("testGetByteThrowsException passed.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
