/* @test    @summary Testa o construtor ZipInputStream(InputStream in) com entrada nula e não nula.    @run main ZipInputStreamWitupTest */

import java.io.InputStream;
import java.io.IOException;
import java.io.NullPointerException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipInputStream;

public class ZipInputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullInput();
        testConstructorWithValidInput();
    }

    private static void testConstructorWithNullInput() {
        try {
            new ZipInputStream(null);
            System.err.println("Failed: NullPointerException expected for null InputStream");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected for null InputStream");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type for null InputStream: " + t);
        }
    }

    private static void testConstructorWithValidInput() {
        InputStream dummyStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };
        try {
            ZipInputStream zis = new ZipInputStream(dummyStream);
            if (zis != null) {
                System.out.println("Passed: ZipInputStream instantiated with valid InputStream");
            } else {
                System.err.println("Failed: ZipInputStream is null with valid InputStream");
            }
        } catch (Throwable t) {
            System.err.println("Failed: Exception thrown during valid ZipInputStream instantiation: " + t);
        }
    }
}
