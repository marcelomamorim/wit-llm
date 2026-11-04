/*
 * @test
 * @summary Test for ObjectOutputStream constructor with valid OutputStream
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidOutputStream();
        testConstructorWithNullOutputStream();
    }

    public static void testConstructorWithValidOutputStream() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            if (oos != null) {
                System.out.println("PASS: Constructor with valid OutputStream");
                oos.close();
            } else {
                System.out.println("FAIL: Constructor returned null");
            }
        } catch (IOException e) {
            System.out.println("FAIL: IOException thrown - " + e.getMessage());
        }
    }

    public static void testConstructorWithNullOutputStream() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream((java.io.OutputStream) null);
            System.out.println("FAIL: Expected NullPointerException, but constructor succeeded");
            oos.close();
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (IOException e) {
            System.out.println("FAIL: Unexpected IOException - " + e.getMessage());
        }
    }
}
