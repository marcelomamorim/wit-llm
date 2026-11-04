/*
 * @test
 * @summary Test jdk.internal.module.SystemModuleFinders.release(ByteBuffer) for NPE on null and normal release
 * @run main SystemModuleFindersWitupTest
 */

import java.nio.ByteBuffer;
import java.util.Objects;
import jdk.internal.module.SystemModuleFinders;

public class SystemModuleFindersWitupTest {

    public static void main(String[] args) {
        testReleaseWithNull();
        testReleaseWithValidByteBuffer();
        System.out.println("All tests passed.");
    }

    private static void testReleaseWithNull() {
        SystemModuleFinders finder = new SystemModuleFinders();
        try {
            finder.release(null);
            throw new AssertionError("Expected NullPointerException for null ByteBuffer");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testReleaseWithValidByteBuffer() {
        SystemModuleFinders finder = new SystemModuleFinders();
        ByteBuffer bb = ByteBuffer.allocate(1);
        // The release method calls ImageReader.releaseByteBuffer(bb) internally,
        // which is native/internal. We test that no exception is thrown for valid input.
        finder.release(bb);
    }
}
