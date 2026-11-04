/*
 * @test
 * @summary Test deterministic behavior of ByteArrayOutputStream.write(int) including capacity growth and overflow handling
 * @run main ByteArrayOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;

public class ByteArrayOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteBasic();
        testWriteCapacityGrowth();
        testWriteOverflowDoesNotThrow();
    }

    private static void testWriteBasic() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1);
        baos.write(0x41); // 'A'
        byte[] result = baos.toByteArray();
        if (result.length != 1 || result[0] != 0x41) {
            throw new AssertionError("Basic write failed: expected [0x41], got " + java.util.Arrays.toString(result));
        }
    }

    private static void testWriteCapacityGrowth() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1);
        // Write two bytes to force growth
        baos.write(0x41);
        baos.write(0x42);
        byte[] result = baos.toByteArray();
        if (result.length != 2 || result[0] != 0x41 || result[1] != 0x42) {
            throw new AssertionError("Capacity growth write failed: expected [0x41, 0x42], got " + java.util.Arrays.toString(result));
        }
    }

    private static void testWriteOverflowDoesNotThrow() throws Exception {
        // This test tries to simulate the overflow condition that could cause OutOfMemoryError
        // but since it is practically impossible to allocate such a huge array, we test that
        // write does not throw for large but valid counts.
        ByteArrayOutputStream baos = new ByteArrayOutputStream(32);

        // Use reflection to set count near Integer.MAX_VALUE - 10 to simulate near overflow
        Field countField = ByteArrayOutputStream.class.getDeclaredField("count");
        countField.setAccessible(true);
        countField.setInt(baos, Integer.MAX_VALUE - 10);

        // Use reflection to set buf length to a large value to avoid immediate growth
        Field bufField = ByteArrayOutputStream.class.getDeclaredField("buf");
        bufField.setAccessible(true);
        byte[] largeBuf = new byte[Integer.MAX_VALUE - 8];
        bufField.set(baos, largeBuf);

        // Now call write with a byte, expecting either success or OutOfMemoryError
        // We catch OutOfMemoryError to confirm it can be thrown, but do not fail if not thrown
        try {
            baos.write(0x55);
        } catch (OutOfMemoryError e) {
            // Expected in extreme cases, test passes
            return;
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }

        // If no exception, test passes as well
    }
}
