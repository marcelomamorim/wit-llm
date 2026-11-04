/* @test    @summary Test BufferedInputStream constructor with null InputStream    @run main BufferedInputStreamWitupTest */

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

public class BufferedInputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullInputStream();
        testConstructorWithValidInputStream();
    }

    static void testConstructorWithNullInputStream() {
        try {
            new BufferedInputStream(null);
            System.out.println("FAIL: Expected NullPointerException for null InputStream");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testConstructorWithValidInputStream() {
        InputStream is = new ByteArrayInputStream(new byte[] {1, 2, 3});
        try {
            BufferedInputStream bis = new BufferedInputStream(is);
            if (bis != null) {
                System.out.println("PASS: BufferedInputStream created successfully with valid InputStream");
            } else {
                System.out.println("FAIL: BufferedInputStream is null");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception during creation: " + e);
        }
    }
}
