/* @test    @summary Test for Base64.getMimeEncoder(int, byte[]) handling null lineSeparator    @run main Base64WitupTest */

import java.util.Base64;
import java.util.Base64.Encoder;
import java.io.IOException;

public class Base64WitupTest {

    public static void main(String[] args) {
        testGetMimeEncoderNullLineSeparator();
        testGetMimeEncoderValidLineSeparator();
    }

    public static void testGetMimeEncoderNullLineSeparator() {
        try {
            Base64.getMimeEncoder(76, null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    public static void testGetMimeEncoderValidLineSeparator() {
        byte[] lineSep = { '\r', '\n' };
        try {
            Encoder encoder = Base64.getMimeEncoder(76, lineSep);
            if (encoder != null) {
                System.out.println("PASS: Encoder returned successfully");
            } else {
                System.out.println("FAIL: Encoder is null");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
