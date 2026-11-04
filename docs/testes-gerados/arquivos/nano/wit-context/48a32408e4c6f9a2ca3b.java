/*
 * @test
 * @summary Tests for ZipInputStream.skip method, covering exception paths and normal behavior.
 * @run main ZipInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;

public class ZipInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testSkipNegative();
        testSkipWhenClosed();
        testSkipNormal();
    }

    private static void testSkipNegative() {
        byte[] data = {0};
        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(data))) {
            long result = zis.skip(-1);
            System.out.println("testSkipNegative: Failed - no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testSkipNegative: Passed - caught IllegalArgumentException");
        } catch (Exception e) {
            System.out.println("testSkipNegative: Failed - caught unexpected exception: " + e);
        }
    }

    private static void testSkipWhenClosed() {
        byte[] data = {0};
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(data));
        try {
            zis.close();
            long result = zis.skip(10);
            System.out.println("testSkipWhenClosed: Failed - no exception thrown");
        } catch (IOException e) {
            System.out.println("testSkipWhenClosed: Passed - caught IOException");
        } catch (Exception e) {
            System.out.println("testSkipWhenClosed: Failed - caught unexpected exception: " + e);
        }
    }

    private static void testSkipNormal() {
        byte[] data = {1, 2, 3, 4, 5};
        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(data))) {
            long skipped = zis.skip(2);
            if (skipped >= 0) {
                System.out.println("testSkipNormal: Passed - skipped " + skipped);
            } else {
                System.out.println("testSkipNormal: Failed - negative skipped value");
            }
        } catch (Exception e) {
            System.out.println("testSkipNormal: Failed - exception: " + e);
        }
    }
}
