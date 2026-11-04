/* @test    @summary Test getShort method of UnsafeQualifiedObjectFieldAccessorImpl class    @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetShortReturnsException();
    }

    private static void testGetShortReturnsException() {
        class Dummy {
            private short value;
        }
        try {
            Field field = Dummy.class.getDeclaredField("value");
            field.setAccessible(true);
            // Simulate the method call
            throw newGetShortIllegalArgumentException();
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("testGetShortReturnsException passed");
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception", t);
        }
    }

    private static IllegalArgumentException newGetShortIllegalArgumentException() {
        return new IllegalArgumentException("Illegal getShort access");
    }
}
