/*
 * @test
 * @summary Tests ObjectOutputStream.useProtocolVersion accepted versions, rejected versions and non-empty stream state
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ObjectOutputStreamWitupTest {

    static class Sink extends OutputStream {
        @Override
        public void write(int b) {
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testAcceptedVersions() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new Sink());
        oos.useProtocolVersion(1);
        oos.useProtocolVersion(2);
        oos.close();
    }

    static void testRejectedVersions() throws IOException {
        int[] bad = {0, 3, -1, 42};
        for (int v : bad) {
            ObjectOutputStream oos = new ObjectOutputStream(new Sink());
            boolean thrown = false;
            try {
                oos.useProtocolVersion(v);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            check(thrown, "expected IllegalArgumentException for version " + v);
        }
    }

    static void testNonEmptyStreamRejected() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new Sink());
        oos.writeObject("payload");
        boolean thrown = false;
        try {
            oos.useProtocolVersion(1);
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalStateException on non-empty stream");
    }

    static void testNonEmptyStreamCheckedBeforeVersion() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new Sink());
        oos.writeObject("payload");
        boolean thrown = false;
        try {
            oos.useProtocolVersion(3);
        } catch (IllegalStateException e) {
            thrown = true;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("version check ran before state check");
        }
        check(thrown, "expected IllegalStateException for invalid version on non-empty stream");
    }

    public static void main(String[] args) throws Exception {
        testAcceptedVersions();
        testRejectedVersions();
        testNonEmptyStreamRejected();
        testNonEmptyStreamCheckedBeforeVersion();
    }
}
