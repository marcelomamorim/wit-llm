/* @test    @summary Test for OpenFileInputStreamAction constructor with null filename to observe NullPointerException behavior    @run main OpenFileInputStreamActionWitupTest */

import java.io.*;
import sun.security.action.OpenFileInputStreamAction;

public class OpenFileInputStreamActionWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullFilename();
    }

    public static void testConstructorWithNullFilename() {
        try {
            new OpenFileInputStreamAction((String) null);
            System.out.println("FAIL: Expected NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException was thrown as expected.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception type: " + e);
        }
    }
}
