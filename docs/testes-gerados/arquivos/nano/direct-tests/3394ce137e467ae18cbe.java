/* @test    @summary Test BufferedInputStream constructor with valid and invalid sizes    @run main BufferedInputStreamWitupTest */

import java.io.ByteArrayInputStream;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class BufferedInputStreamWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testInvalidConstructor();
    }

    static void testValidConstructor() {
        byte[] data = {1, 2, 3, 4, 5};
        InputStream in = new ByteArrayInputStream(data);
        int size = 1024;
        BufferedInputStream bis = new BufferedInputStream(in, size);
        assert bis != null : "BufferedInputStream should be created with valid size";
        // Read some bytes to verify stream works
        try {
            int byteRead = bis.read();
            assert byteRead != -1 : "Stream should have data";
        } catch (Exception e) {
            throw new AssertionError("Exception during read: " + e);
        }
    }

    static void testInvalidConstructor() {
        byte[] data = {1, 2, 3};
        InputStream in = new ByteArrayInputStream(data);
        boolean exceptionThrown = false;
        try {
            new BufferedInputStream(in, 0);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Constructor should throw IllegalArgumentException for size=0";
        exceptionThrown = false;
        try {
            new BufferedInputStream(in, -10);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Constructor should throw IllegalArgumentException for negative size";
    }
}
