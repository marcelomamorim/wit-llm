/*
 * @test
 * @summary Tests for java.io.InputStream.read(byte[])
 * @run main InputStreamWitupTest
 */

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testReadWithNullBuffer();
        testReadWithZeroLengthBuffer();
        testReadWithNegativeOffset();
        testReadWithOffsetExceedingBuffer();
        testReadWithValidBuffer();
    }

    static void testReadWithNullBuffer() {
        InputStream is = InputStream.nullInputStream();
        try {
            is.read(null);
            System.out.println("testReadWithNullBuffer: Failed - no exception");
        } catch (NullPointerException e) {
            System.out.println("testReadWithNullBuffer: Passed");
        } catch (Exception e) {
            System.out.println("testReadWithNullBuffer: Failed - wrong exception: " + e);
        }
    }

    static void testReadWithZeroLengthBuffer() throws IOException {
        InputStream is = InputStream.nullInputStream();
        byte[] buffer = new byte[10];
        int result = is.read(buffer, 0, 0);
        if (result == 0) {
            System.out.println("testReadWithZeroLengthBuffer: Passed");
        } else {
            System.out.println("testReadWithZeroLengthBuffer: Failed - result: " + result);
        }
    }

    static void testReadWithNegativeOffset() {
        InputStream is = InputStream.nullInputStream();
        byte[] buffer = new byte[10];
        try {
            is.read(buffer, -1, 5);
            System.out.println("testReadWithNegativeOffset: Failed - no exception");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("testReadWithNegativeOffset: Passed");
        } catch (Exception e) {
            System.out.println("testReadWithNegativeOffset: Failed - wrong exception: " + e);
        }
    }

    static void testReadWithOffsetExceedingBuffer() {
        InputStream is = InputStream.nullInputStream();
        byte[] buffer = new byte[10];
        try {
            is.read(buffer, 11, 1);
            System.out.println("testReadWithOffsetExceedingBuffer: Failed - no exception");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("testReadWithOffsetExceedingBuffer: Passed");
        } catch (Exception e) {
            System.out.println("testReadWithOffsetExceedingBuffer: Failed - wrong exception: " + e);
        }
    }

    static void testReadWithValidBuffer() throws IOException {
        InputStream is = InputStream.nullInputStream();
        byte[] buffer = new byte[10];
        int result = is.read(buffer, 0, buffer.length);
        if (result == -1) {
            System.out.println("testReadWithValidBuffer: Passed");
        } else {
            System.out.println("testReadWithValidBuffer: Failed - result: " + result);
        }
    }
}
