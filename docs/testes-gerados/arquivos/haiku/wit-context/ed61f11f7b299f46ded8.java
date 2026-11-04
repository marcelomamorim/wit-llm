/*
 * @test
 * @summary PrintStream(File, Charset) must reject a null charset and encode output using the given charset
 * @run main PrintStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;

public class PrintStreamWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static byte[] readAll(File file) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = new FileInputStream(file);
        try {
            byte[] buf = new byte[64];
            int n;
            while ((n = in.read(buf)) != -1) {
                out.write(buf, 0, n);
            }
        } finally {
            in.close();
        }
        return out.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        File file = File.createTempFile("printstream_witup", ".txt");
        file.deleteOnExit();

        Charset utf8 = Charset.forName("UTF-8");
        Charset latin1 = Charset.forName("ISO-8859-1");

        boolean npeForNullCharset = false;
        try {
            PrintStream ps = new PrintStream(file, (Charset) null);
            ps.close();
        } catch (NullPointerException e) {
            npeForNullCharset = true;
        }
        check(npeForNullCharset, "expected NullPointerException for null charset");

        boolean npeForNullFile = false;
        try {
            PrintStream ps = new PrintStream((File) null, utf8);
            ps.close();
        } catch (NullPointerException e) {
            npeForNullFile = true;
        }
        check(npeForNullFile, "expected NullPointerException for null file");

        PrintStream ps = new PrintStream(file, latin1);
        ps.print("\u00e9");
        ps.close();
        byte[] latin1Bytes = readAll(file);
        check(latin1Bytes.length == 1, "expected 1 byte for ISO-8859-1 output, got " + latin1Bytes.length);
        check(latin1Bytes[0] == (byte) 0xE9, "expected ISO-8859-1 byte 0xE9");

        ps = new PrintStream(file, utf8);
        ps.print("\u00e9");
        ps.close();
        byte[] utf8Bytes = readAll(file);
        check(utf8Bytes.length == 2, "expected 2 bytes for UTF-8 output, got " + utf8Bytes.length);
        check(utf8Bytes[0] == (byte) 0xC3 && utf8Bytes[1] == (byte) 0xA9, "expected UTF-8 bytes C3 A9");
    }
}
