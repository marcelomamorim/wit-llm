/* @test    @summary Test for BufferedReader constructor with null Reader and invalid buffer size    @run main BufferedReaderWitupTest */

import java.io.BufferedReader;
import java.io.StringReader;

public class BufferedReaderWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullReader();
        testConstructorWithZeroBufferSize();
        testConstructorWithNegativeBufferSize();
    }

    private static void testConstructorWithNullReader() {
        try {
            new BufferedReader(null, 1024);
            System.out.println("FAIL: Expected NullPointerException for null Reader");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null Reader");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for null Reader: " + e);
        }
    }

    private static void testConstructorWithZeroBufferSize() {
        try {
            new BufferedReader(new StringReader("test"), 0);
            System.out.println("FAIL: Expected IllegalArgumentException for buffer size 0");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException thrown as expected for buffer size 0");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for buffer size 0: " + e);
        }
    }

    private static void testConstructorWithNegativeBufferSize() {
        try {
            new BufferedReader(new StringReader("test"), -10);
            System.out.println("FAIL: Expected IllegalArgumentException for negative buffer size");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException thrown as expected for negative buffer size");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for negative buffer size: " + e);
        }
    }
}
