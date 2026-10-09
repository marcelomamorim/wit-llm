/*
 * @test
 * @summary Teste do método seek para diferentes valores de pos
 * @run main RandomAccessFileWitupTest_Pbfcaff30e23e
 */

import java.io.RandomAccessFile;
import java.io.IOException;

public class RandomAccessFileWitupTest_Pbfcaff30e23e {

    public static void main(String[] args) {
        testSeekNegative();
        testSeekZero();
        testSeekPositive();
    }

    static void testSeekNegative() {
        try {
            RandomAccessFile raf = new RandomAccessFile("testfile.tmp", "rw");
            try {
                raf.seek(-1);
                System.err.println("Failed: Expected IOException for negative seek");
            } catch (IOException e) {
                System.out.println("Passed: Caught expected IOException for negative seek");
            } finally {
                raf.close();
            }
        } catch (IOException e) {
            System.err.println("Error initializing RandomAccessFile: " + e);
        }
    }

    static void testSeekZero() {
        try {
            RandomAccessFile raf = new RandomAccessFile("testfile.tmp", "rw");
            try {
                raf.seek(0);
                System.out.println("Passed: Seek to zero succeeded");
            } catch (IOException e) {
                System.err.println("Failed: Unexpected IOException on seek(0): " + e);
            } finally {
                raf.close();
            }
        } catch (IOException e) {
            System.err.println("Error initializing RandomAccessFile: " + e);
        }
    }

    static void testSeekPositive() {
        try {
            RandomAccessFile raf = new RandomAccessFile("testfile.tmp", "rw");
            try {
                raf.seek(10);
                System.out.println("Passed: Seek to positive offset succeeded");
            } catch (IOException e) {
                System.err.println("Failed: Unexpected IOException on seek(10): " + e);
            } finally {
                raf.close();
            }
        } catch (IOException e) {
            System.err.println("Error initializing RandomAccessFile: " + e);
        }
    }
}
