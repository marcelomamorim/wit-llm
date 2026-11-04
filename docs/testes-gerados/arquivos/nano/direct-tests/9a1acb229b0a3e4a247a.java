/* @test    @summary Test constructor of JarInputStream with minimal InputStream.    @run main JarInputStreamWitupTest */

import java.io.*;
import java.util.jar.JarInputStream;

public class JarInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testConstructorWithNullInputStream();
        testConstructorWithEmptyInputStream();
    }

    private static void testConstructorWithNullInputStream() {
        try {
            new JarInputStream((InputStream) null);
            // Expecting a NullPointerException or IOException, depending on implementation
            System.out.println("testConstructorWithNullInputStream: Failed - no exception thrown");
        } catch (NullPointerException | IOException e) {
            System.out.println("testConstructorWithNullInputStream: Passed - caught expected exception: " + e.getClass().getSimpleName());
        } catch (Throwable t) {
            System.out.println("testConstructorWithNullInputStream: Failed - caught unexpected exception: " + t);
        }
    }

    private static void testConstructorWithEmptyInputStream() {
        InputStream emptyStream = new ByteArrayInputStream(new byte[0]);
        try {
            JarInputStream jis = new JarInputStream(emptyStream);
            // Since input is empty, getNextEntry() should return null
            if (jis.getNextEntry() == null) {
                System.out.println("testConstructorWithEmptyInputStream: Passed - no entries in empty stream");
            } else {
                System.out.println("testConstructorWithEmptyInputStream: Failed - unexpected entry in empty stream");
            }
        } catch (IOException e) {
            System.out.println("testConstructorWithEmptyInputStream: Failed - IOException: " + e);
        }
    }
}
