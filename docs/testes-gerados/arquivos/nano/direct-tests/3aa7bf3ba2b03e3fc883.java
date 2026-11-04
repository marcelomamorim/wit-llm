/* @test    @summary Test DataOutputStream.write(int b) method for correct byte writing and count increment.    @run main DataOutputStreamWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DataOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testWriteByteIncrementsCount();
        testWriteMultipleBytes();
        testWriteWithEmptyStream();
    }

    static void testWriteByteIncrementsCount() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        int initialCount = getWrittenCount(dos);
        dos.write(0x5A);
        int newCount = getWrittenCount(dos);
        assert newCount == initialCount + 1 : "Count should increment by 1 after write";
        byte[] data = baos.toByteArray();
        assert data.length == 1 : "One byte should be written";
        assert data[0] == 0x5A : "Byte written should match input";
    }

    static void testWriteMultipleBytes() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        byte[] bytes = {0x10, 0x20, 0x30};
        int initialCount = getWrittenCount(dos);
        dos.write(bytes, 0, bytes.length);
        int newCount = getWrittenCount(dos);
        assert newCount == initialCount + bytes.length : "Count should increment by number of bytes written";
        byte[] data = baos.toByteArray();
        assert data.length == bytes.length : "All bytes should be written";
        for (int i = 0; i < bytes.length; i++) {
            assert data[i] == bytes[i] : "Byte at position " + i + " should match";
        }
    }

    static void testWriteWithEmptyStream() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        int initialCount = getWrittenCount(dos);
        dos.write(new byte[0], 0, 0);
        int newCount = getWrittenCount(dos);
        assert newCount == initialCount : "Count should not change when writing zero bytes";
        byte[] data = baos.toByteArray();
        assert data.length == 0 : "No data should be written";
    }

    static int getWrittenCount(DataOutputStream dos) {
        try {
            java.lang.reflect.Field field = DataOutputStream.class.getDeclaredField("written");
            field.setAccessible(true);
            return field.getInt(dos);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to access 'written' field", e);
        }
    }
}
