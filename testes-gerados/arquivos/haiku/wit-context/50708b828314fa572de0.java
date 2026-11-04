/*
 * @test
 * @summary Witup-derived checks for String.getBytes(Charset): null charset must throw NullPointerException and valid charsets must encode correctly
 * @run main StringWitupTest
 */
import java.nio.charset.Charset;

public class StringWitupTest {

    public static void main(String[] args) {
        Charset utf8 = Charset.forName("UTF-8");

        byte[] ascii = "abc".getBytes(utf8);
        if (ascii.length != 3 || ascii[0] != 97 || ascii[1] != 98 || ascii[2] != 99) {
            throw new RuntimeException("Unexpected UTF-8 bytes for ascii string");
        }

        byte[] accented = "\u00e9".getBytes(utf8);
        if (accented.length != 2 || (accented[0] & 0xFF) != 0xC3 || (accented[1] & 0xFF) != 0xA9) {
            throw new RuntimeException("Unexpected UTF-8 bytes for e-acute");
        }

        byte[] empty = "".getBytes(utf8);
        if (empty.length != 0) {
            throw new RuntimeException("Empty string should encode to zero bytes");
        }

        boolean thrown = false;
        try {
            "abc".getBytes((Charset) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getBytes(null Charset) must throw NullPointerException");
        }
    }
}
