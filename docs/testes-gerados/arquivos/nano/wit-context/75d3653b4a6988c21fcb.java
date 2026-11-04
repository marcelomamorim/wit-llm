/*
 * @test
 * @summary Test for RandomAccessFile.seek(long) method, including exception path for negative offset
 * @run main RandomAccessFileWitupTest
 */

import java.io.RandomAccessFile;
import java.io.IOException;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) {
        testSeekWithNegativeOffset();
        testSeekWithValidOffset();
    }

    private static void testSeekWithNegativeOffset() {
        try {
            RandomAccessFile raf = new RandomAccessFile("testfile.txt", "rw");
            try {
                raf.seek(-10L);
                System.out.println("FAIL: Expected IOException for negative offset not thrown.");
            } catch (IOException e) {
                System.out.println("PASS: Caught expected IOException for negative offset.");
            } finally {
                raf.close();
            }
        } catch (IOException e) {
            System.out.println("ERROR: Failed to open file for testSeekWithNegativeOffset: " + e);
        }
    }

    private static void testSeekWithValidOffset() {
        try {
            RandomAccessFile raf = new RandomAccessFile("testfile.txt", "rw");
            try {
                raf.seek(0L);
                System.out.println("PASS: seek(0) executed without exception.");
            } catch (IOException e) {
                System.out.println("FAIL: Unexpected IOException for seek(0): " + e);
            } finally {
                raf.close();
            }
        } catch (IOException e) {
            System.out.println("ERROR: Failed to open file for testSeekWithValidOffset: " + e);
        }
    }
}
