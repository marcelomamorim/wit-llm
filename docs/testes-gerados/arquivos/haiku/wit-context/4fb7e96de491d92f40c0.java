/*
 * @test
 * @summary Tests ObjectOutputStream.useProtocolVersion accepts known versions on a pristine stream, rejects unknown versions, and rejects a non-empty stream
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testAcceptsKnownVersionsOnPristineStream();
        testRejectsUnknownVersions();
        testRejectsNonEmptyStream();
    }

    static void testAcceptsKnownVersionsOnPristineStream() throws IOException {
        int[] versions = {1, 2};
        for (int v : versions) {
            ObjectOutputStream oos = new ObjectOutputStream(new ByteArrayOutputStream());
            oos.useProtocolVersion(v);
            oos.close();
        }
    }

    static void testRejectsUnknownVersions() throws IOException {
        int[] bad = {0, 3, -1, 99};
        for (int v : bad) {
            ObjectOutputStream oos = new ObjectOutputStream(new ByteArrayOutputStream());
            try {
                oos.useProtocolVersion(v);
                throw new AssertionError("no IllegalArgumentException for version " + v);
            } catch (IllegalArgumentException expected) {
            }
            oos.close();
        }
    }

    static void testRejectsNonEmptyStream() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject("handle");
        try {
            oos.useProtocolVersion(1);
            throw new AssertionError("no IllegalStateException for non-empty stream");
        } catch (IllegalStateException expected) {
        }
        oos.close();
    }
}
