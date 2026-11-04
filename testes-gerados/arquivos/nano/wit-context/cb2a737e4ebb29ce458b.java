/* @test    @summary Test write(int) method of ChunkedOutputStream for exception paths and normal behavior    @run main ChunkedOutputStreamWitupTest */

import java.io.*;

public class ChunkedOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteWithNullOutputStream();
        testWriteWithValidOutputStream();
    }

    private static void testWriteWithNullOutputStream() {
        try {
            ChunkedOutputStream cos = new ChunkedOutputStream(null);
            cos.write(65);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testWriteWithValidOutputStream() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ChunkedOutputStream cos = new ChunkedOutputStream(new PrintStream(baos));
            cos.write(65); // ASCII 'A'
            cos.flush();
            byte[] outputBytes = baos.toByteArray();
            String outputStr = new String(outputBytes, "US-ASCII");
            if (outputStr.contains("A")) {
                System.out.println("PASS: Write method outputs data correctly");
            } else {
                System.out.println("FAIL: Output does not contain expected data");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception during valid write: " + e);
        }
    }
}
