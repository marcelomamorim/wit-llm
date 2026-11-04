/*
 * @test
 * @summary Test that getShort(Object) always throws IllegalArgumentException
 * @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest
 */

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        UnsafeQualifiedDoubleFieldAccessorImplWitupTest test = new UnsafeQualifiedDoubleFieldAccessorImplWitupTest();
        test.testGetShortThrows();
    }

    private void testGetShortThrows() {
        jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl(null, false);
        try {
            accessor.getShort(new Object());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // expected exception
        }
    }
}
