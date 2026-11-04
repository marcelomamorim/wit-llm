/*
 * @test
 * @summary Verifies the String constructor of OpenFileInputStreamAction: null rejection and run() behavior with existing and missing files
 * @modules java.base/sun.security.action
 * @run main OpenFileInputStreamActionWitupTest
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import sun.security.action.OpenFileInputStreamAction;

public class OpenFileInputStreamActionWitupTest {
    public static void main(String[] args) throws Exception {
        try {
            new OpenFileInputStreamAction((String) null);
            throw new AssertionError("expected NullPointerException for null filename");
        } catch (NullPointerException expected) {
        }

        File existing = new File("witup_ofisa_existing.tmp");
        try (FileOutputStream out = new FileOutputStream(existing)) {
            out.write(42);
        }
        try {
            FileInputStream in = new OpenFileInputStreamAction(existing.getPath()).run();
            try {
                if (in.read() != 42) {
                    throw new AssertionError("unexpected content read from file");
                }
            } finally {
                in.close();
            }
        } finally {
            existing.delete();
        }

        File missing = new File("witup_ofisa_missing.tmp");
        missing.delete();
        try {
            new OpenFileInputStreamAction(missing.getPath()).run();
            throw new AssertionError("expected FileNotFoundException for missing file");
        } catch (FileNotFoundException expected) {
        }
    }
}
