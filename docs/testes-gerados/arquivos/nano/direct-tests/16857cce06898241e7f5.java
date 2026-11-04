/* @test    @summary Test ByteArrayOutputStream.write(int b) method for correct byte writing and capacity growth    @run main ByteArrayOutputStreamWitupTest */

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class ByteArrayOutputStreamWitupTest {

    public static void main(String[] args) {
        testWriteSingleByte();
        testWriteMultipleBytes();
        testWriteWithCapacityGrowth();
    }

    private static void testWriteSingleByte() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(65); // ASCII 'A'
        byte[] result = baos.toByteArray();
        assert result.length == 1 : "Expected length 1, got " + result.length;
        assert result[0] == 65 : "Expected byte 65, got " + result[0];
    }

    private static void testWriteMultipleBytes() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(2);
        baos.write(66); // 'B'
        baos.write(67); // 'C'
        byte[] result = baos.toByteArray();
        assert result.length == 2 : "Expected length 2, got " + result.length;
        assert result[0] == 66 : "Expected first byte 66, got " + result[0];
        assert result[1] == 67 : "Expected second byte 67, got " + result[1];
    }

    private static void testWriteWithCapacityGrowth() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1);
        baos.write(68); // 'D'
        baos.write(69); // 'E' - should trigger capacity growth
        byte[] result = baos.toByteArray();
        assert result.length == 2 : "Expected length 2 after growth, got " + result.length;
        assert result[0] == 68 : "Expected first byte 68, got " + result[0];
        assert result[1] == 69 : "Expected second byte 69, got " + result[1];
    }
}
