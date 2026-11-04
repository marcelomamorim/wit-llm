/* @test    @summary Test the skip method of BufferedInputStream for various scenarios    @run main BufferedInputStreamWitupTest */

import java.io.ByteArrayInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;

public class BufferedInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testSkipWithAvailableBytes();
        testSkipWithNoAvailableBytes();
        testSkipWithNegativeAndZero();
        testSkipAfterMark();
        System.out.println("All tests passed.");
    }

    static void testSkipWithAvailableBytes() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(bais, 3);
        long skipped = bis.skip(2);
        assert skipped == 2 : "Expected to skip 2 bytes, but skipped " + skipped;
        int remaining = bis.read();
        assert remaining == 3 : "Expected next byte to be 3, but got " + remaining;
        bis.close();
    }

    static void testSkipWithNoAvailableBytes() throws IOException {
        byte[] data = {10, 20};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(bais, 2);
        // Skip all bytes
        long skipped = bis.skip(2);
        assert skipped == 2 : "Expected to skip 2 bytes, but skipped " + skipped;
        // Now no more data
        skipped = bis.skip(5);
        assert skipped == 0 : "Expected to skip 0 bytes, but skipped " + skipped;
        bis.close();
    }

    static void testSkipWithNegativeAndZero() throws IOException {
        byte[] data = {5, 6, 7};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(bais);
        long skippedZero = bis.skip(0);
        assert skippedZero == 0 : "Skipping zero should return 0, but got " + skippedZero;
        long skippedNegative = bis.skip(-10);
        assert skippedNegative == 0 : "Skipping negative should return 0, but got " + skippedNegative;
        bis.close();
    }

    static void testSkipAfterMark() throws IOException {
        byte[] data = {8, 9, 10, 11};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(bais);
        bis.mark(4);
        long skipped = bis.skip(2);
        assert skipped == 2 : "Expected to skip 2 bytes, but skipped " + skipped;
        int firstByte = bis.read();
        assert firstByte == 10 : "Expected byte 10 after skip, but got " + firstByte;
        bis.reset();
        int resetByte = bis.read();
        assert resetByte == 8 : "After reset, expected byte 8, but got " + resetByte;
        bis.close();
    }
}
