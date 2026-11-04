/*
 * @test
 * @summary Test for ZipOutputStream.setLevel(int level) method, focusing on exception path for invalid levels.
 * @run main ZipOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.util.zip.ZipOutputStream;
import java.io.IOException;

public class ZipOutputStreamWitupTest {

    public static void main(String[] args) {
        testSetLevelInvalidHigh();
        testSetLevelInvalidLow();
        testSetLevelValid();
    }

    static void testSetLevelInvalidHigh() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        try {
            zos.setLevel(10); // invalid, should throw IllegalArgumentException
            System.err.println("Failed: No exception for level 10");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Caught expected IllegalArgumentException for level 10");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for level 10: " + e);
        }
    }

    static void testSetLevelInvalidLow() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        try {
            zos.setLevel(-2); // invalid, should throw IllegalArgumentException
            System.err.println("Failed: No exception for level -2");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Caught expected IllegalArgumentException for level -2");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for level -2: " + e);
        }
    }

    static void testSetLevelValid() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        try {
            zos.setLevel(5); // valid, should not throw
            System.out.println("Passed: No exception for level 5");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for level 5: " + e);
        }
    }
}
