/* @test    @summary Test SystemModuleFinders.release(ByteBuffer) method for correct behavior and null input handling    @run main SystemModuleFindersWitupTest */

import java.nio.ByteBuffer;
import java.util.Objects;

public class SystemModuleFindersWitupTest {

    public static void main(String[] args) {
        testReleaseWithValidByteBuffer();
        testReleaseWithNullByteBuffer();
    }

    private static void testReleaseWithValidByteBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            SystemModuleFinders.release(buffer);
            System.out.println("testReleaseWithValidByteBuffer passed");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid ByteBuffer", e);
        }
    }

    private static void testReleaseWithNullByteBuffer() {
        try {
            SystemModuleFinders.release(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("testReleaseWithNullByteBuffer passed");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
