/*
 * @test
 * @summary Test for BufferedInputStream.read() method to observe exception behavior.
 * @run main BufferedInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;

public class BufferedInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testReadWithNullBuffer();
        testReadWithClosedStream();
        testReadNormal();
    }

    static void testReadWithNullBuffer() {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(bais);
        try {
            // Simulate null buf by reflection or forcibly nulling buf (not possible directly), so test indirectly
            // Instead, test behavior when stream is closed
            bis.close();
            bis.read();
            System.out.println("testReadWithNullBuffer: Failed - no exception thrown");
        } catch (IOException e) {
            System.out.println("testReadWithNullBuffer: Passed - IOException caught as expected");
        } catch (Exception e) {
            System.out.println("testReadWithNullBuffer: Failed - Unexpected exception: " + e);
        }
    }

    static void testReadWithClosedStream() {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(bais);
        try {
            bis.close();
            int result = bis.read();
            System.out.println("testReadWithClosedStream: Failed - no exception, result: " + result);
        } catch (IOException e) {
            System.out.println("testReadWithClosedStream: Passed - IOException caught as expected");
        } catch (Exception e) {
            System.out.println("testReadWithClosedStream: Failed - Unexpected exception: " + e);
        }
    }

    static void testReadNormal() {
        byte[] data = {10, 20, 30};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(bais);
        try {
            int firstByte = bis.read();
            int secondByte = bis.read();
            int thirdByte = bis.read();
            int eof = bis.read();
            if (firstByte == 10 && secondByte == 20 && thirdByte == 30 && eof == -1) {
                System.out.println("testReadNormal: Passed");
            } else {
                System.out.println("testReadNormal: Failed - unexpected bytes: " + firstByte + ", " + secondByte + ", " + thirdByte + ", eof: " + eof);
            }
        } catch (IOException e) {
            System.out.println("testReadNormal: Failed - IOException: " + e);
        }
    }
}
