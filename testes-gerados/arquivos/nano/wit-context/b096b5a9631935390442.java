/*
 * @test
 * @summary Tests for java.io.PushbackInputStream.read()
 * @run main PushbackInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class PushbackInputStreamWitupTest {

    public static void main(String[] args) {
        testReadWithNullStream();
        testReadNormal();
    }

    private static void testReadWithNullStream() {
        PushbackInputStream pbi = null;
        try {
            pbi = new PushbackInputStream(null, 1);
            pbi.read();
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (IOException e) {
            System.out.println("FAIL: Unexpected IOException: " + e);
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testReadNormal() {
        byte[] data = {10, 20, 30};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        PushbackInputStream pbi = new PushbackInputStream(bais, 2);
        try {
            int byte1 = pbi.read();
            int byte2 = pbi.read();
            int byte3 = pbi.read();
            int byte4 = pbi.read();
            if (byte1 == 10 && byte2 == 20 && byte3 == 30 && byte4 == -1) {
                System.out.println("PASS: Read bytes correctly");
            } else {
                System.out.println("FAIL: Unexpected bytes read: " + byte1 + "," + byte2 + "," + byte3 + "," + byte4);
            }
        } catch (IOException e) {
            System.out.println("FAIL: IOException during read: " + e);
        }
    }
}
