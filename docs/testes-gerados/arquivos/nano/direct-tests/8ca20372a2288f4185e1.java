/* @test    @summary Test getAndAddCharAcquire method for correct addition behavior    @run main UnsafeWitupTest */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testGetAndAddCharAcquire();
    }

    private static void testGetAndAddCharAcquire() {
        Unsafe unsafe = Unsafe.getUnsafe();
        String testString = "abc";
        try {
            // Obtain the offset of the first character in the string
            long offset = unsafe.objectFieldOffset(String.class.getDeclaredField("value"));
            // Read the original value
            char originalChar = unsafe.getChar(testString, offset);
            // Add delta to the character
            char delta = 'A';
            char resultChar = unsafe.getAndAddCharAcquire(testString, offset, delta);
            // Verify that the returned value is the original
            if (resultChar != originalChar) {
                throw new AssertionError("Expected original char: " + originalChar + ", but got: " + resultChar);
            }
            // Verify that the character was incremented by delta
            char expectedChar = (char) (originalChar + delta);
            char newChar = unsafe.getChar(testString, offset);
            if (newChar != expectedChar) {
                throw new AssertionError("Expected new char: " + expectedChar + ", but got: " + newChar);
            }
        } catch (NoSuchFieldException e) {
            throw new AssertionError("Failed to access String.value field", e);
        }
    }
}
