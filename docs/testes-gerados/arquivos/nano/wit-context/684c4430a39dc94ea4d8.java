/*
 * @test
 * @summary Tests for sun.security.util.IOUtils.readNBytes
 * @run main IOUtilsWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOUtilsWitupTest {

    public static void main(String[] args) {
        testReadNBytesNegativeLength();
        testReadNBytesNormal();
    }

    private static void testReadNBytesNegativeLength() {
        InputStream is = new ByteArrayInputStream(new byte[]{1, 2, 3});
        try {
            byte[] result = sun.security.util.IOUtils.readNBytes(is, -1);
            System.err.println("Failed: Expected IOException for negative length, but got result: " + result);
        } catch (IOException e) {
            System.out.println("Passed: IOException thrown for negative length as expected.");
        }
    }

    private static void testReadNBytesNormal() {
        byte[] data = {10, 20, 30, 40, 50};
        InputStream is = new ByteArrayInputStream(data);
        try {
            byte[] result = sun.security.util.IOUtils.readNBytes(is, 3);
            if (result.length == 3 && result[0] == 10 && result[1] == 20 && result[2] == 30) {
                System.out.println("Passed: readNBytes returned correct bytes.");
            } else {
                System.err.println("Failed: readNBytes returned incorrect bytes: " + java.util.Arrays.toString(result));
            }
        } catch (IOException e) {
            System.err.println("Failed: Unexpected IOException: " + e);
        }
        // Test reading more bytes than available
        is = new ByteArrayInputStream(new byte[]{1, 2});
        try {
            byte[] result = sun.security.util.IOUtils.readNBytes(is, 5);
            if (result.length == 2 && result[0] == 1 && result[1] == 2) {
                System.out.println("Passed: readNBytes returned available bytes when requested more.");
            } else {
                System.err.println("Failed: readNBytes returned incorrect bytes when stream exhausted: " + java.util.Arrays.toString(result));
            }
        } catch (IOException e) {
            System.err.println("Failed: Unexpected IOException: " + e);
        }
    }
}
