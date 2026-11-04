/* @test    @summary Test BufferedInputStream constructor with valid and invalid sizes    @run main BufferedInputStreamWitupTest */

import java.io.ByteArrayInputStream;
import java.io.BufferedInputStream;

public class BufferedInputStreamWitupTest {

    public static void main(String[] args) {
        testValidSize();
        testZeroSize();
        testNegativeSize();
    }

    private static void testValidSize() {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try {
            BufferedInputStream bis = new BufferedInputStream(bais, 1024);
            if (bis == null) {
                throw new AssertionError("BufferedInputStream should not be null for valid size");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid size: " + e);
        }
    }

    private static void testZeroSize() {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try {
            new BufferedInputStream(bais, 0);
            throw new AssertionError("Expected IllegalArgumentException for size=0");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for size=0: " + e);
        }
    }

    private static void testNegativeSize() {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try {
            new BufferedInputStream(bais, -10);
            throw new AssertionError("Expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for negative size: " + e);
        }
    }
}
