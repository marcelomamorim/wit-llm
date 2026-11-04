/*
 * @test
 * @summary Verify PrintWriter(File, Charset) rejects a null charset and encodes output with the given charset
 * @run main PrintWriterWitupTest
 */
import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class PrintWriterWitupTest {
    public static void main(String[] args) throws Exception {
        File f = new File("PrintWriterWitupTest.out");
        f.delete();
        try {
            try {
                new PrintWriter(f, null);
                throw new RuntimeException("expected NullPointerException for null charset");
            } catch (NullPointerException expected) {
            }
            if (f.exists()) {
                throw new RuntimeException("file must not be created when charset is null");
            }

            PrintWriter pw = new PrintWriter(f, StandardCharsets.ISO_8859_1);
            pw.print("h\u00e9");
            pw.close();
            byte[] b = Files.readAllBytes(f.toPath());
            if (b.length != 2 || b[0] != 0x68 || (b[1] & 0xFF) != 0xE9) {
                throw new RuntimeException("unexpected ISO-8859-1 bytes");
            }
            f.delete();

            pw = new PrintWriter(f, StandardCharsets.UTF_8);
            pw.print("\u00e9");
            pw.close();
            b = Files.readAllBytes(f.toPath());
            if (b.length != 2 || (b[0] & 0xFF) != 0xC3 || (b[1] & 0xFF) != 0xA9) {
                throw new RuntimeException("unexpected UTF-8 bytes");
            }
        } finally {
            f.delete();
        }
    }
}
