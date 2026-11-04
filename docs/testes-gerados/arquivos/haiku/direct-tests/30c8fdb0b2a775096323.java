/*
 * @test
 * @summary Verifies BasicConstraintsExtension.set argument validation and name matching
 * @modules java.base/sun.security.x509
 * @run main BasicConstraintsExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.BasicConstraintsExtension;

public class BasicConstraintsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testValidIsCaAccepted();
        testValidPathLenAccepted();
        testIsCaNameIsCaseInsensitive();
        testIsCaWithIntegerRejected();
        testPathLenWithBooleanRejected();
        testUnknownNameRejected();
        testNullValueForIsCaRejected();
    }

    static BasicConstraintsExtension newExt() throws IOException {
        return new BasicConstraintsExtension(Boolean.TRUE, true, 1);
    }

    static void testValidIsCaAccepted() throws Exception {
        BasicConstraintsExtension ext = newExt();
        ext.set(BasicConstraintsExtension.IS_CA, Boolean.FALSE);
        ext.set(BasicConstraintsExtension.IS_CA, Boolean.TRUE);
    }

    static void testValidPathLenAccepted() throws Exception {
        BasicConstraintsExtension ext = newExt();
        ext.set(BasicConstraintsExtension.PATH_LEN, Integer.valueOf(3));
        ext.set(BasicConstraintsExtension.PATH_LEN, Integer.valueOf(-1));
    }

    static void testIsCaseInsensitiveName() throws Exception {
        BasicConstraintsExtension ext = newExt();
        ext.set("IS_CA", Boolean.TRUE);
        ext.set("Path_Len", Integer.valueOf(0));
    }

    static void testIsCaNameIsCaseInsensitive() throws Exception {
        testIsCaseInsensitiveName();
    }

    static void testIsCaWithIntegerRejected() throws Exception {
        BasicConstraintsExtension ext = newExt();
        try {
            ext.set(BasicConstraintsExtension.IS_CA, Integer.valueOf(1));
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for non-Boolean is_ca");
    }

    static void testPathLenWithBooleanRejected() throws Exception {
        BasicConstraintsExtension ext = newExt();
        try {
            ext.set(BasicConstraintsExtension.PATH_LEN, Boolean.TRUE);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for non-Integer path_len");
    }

    static void testUnknownNameRejected() throws Exception {
        BasicConstraintsExtension ext = newExt();
        try {
            ext.set("unknown_attribute", Boolean.TRUE);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for unrecognized name");
    }

    static void testNullValueForIsCaRejected() throws Exception {
        BasicConstraintsExtension ext = newExt();
        try {
            ext.set(BasicConstraintsExtension.IS_CA, null);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for null is_ca value");
    }
}
