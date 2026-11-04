/*
 * @test
 * @summary Witup generated tests for DerValue.toDerInputStream tag validation
 * @modules java.base/sun.security.util
 * @run main DerValueWitupTest
 */
import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;

public class DerValueWitupTest {
    public static void main(String[] args) throws Exception {
        testSequenceAccepted();
        testSetAccepted();
        testIntegerRejected();
        testOctetStringRejected();
        testNullRejected();
    }

    static void testSequenceAccepted() throws IOException {
        DerValue v = new DerValue(new byte[]{0x30, 0x00});
        DerInputStream in = v.toDerInputStream();
        if (in == null) {
            throw new RuntimeException("null DerInputStream for SEQUENCE");
        }
    }

    static void testSetAccepted() throws IOException {
        DerValue v = new DerValue(DerValue.tag_Set, new byte[0]);
        DerInputStream in = v.toDerInputStream();
        if (in == null) {
            throw new RuntimeException("null DerInputStream for SET");
        }
    }

    static void testIntegerRejected() throws IOException {
        DerValue v = new DerValue(new byte[]{0x02, 0x01, 0x05});
        expectIOException(v, "INTEGER");
    }

    static void testOctetStringRejected() throws IOException {
        DerValue v = new DerValue(new byte[]{0x04, 0x01, 0x00});
        expectIOException(v, "OCTET STRING");
    }

    static void testNullRejected() throws IOException {
        DerValue v = new DerValue(new byte[]{0x05, 0x00});
        expectIOException(v, "NULL");
    }

    static void expectIOException(DerValue v, String name) {
        try {
            v.toDerInputStream();
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("toDerInputStream accepted tag " + name);
    }
}
