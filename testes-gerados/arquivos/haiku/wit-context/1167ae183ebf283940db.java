/*
 * @test
 * @summary Checks public encode(OutputStream) behavior of SubjectInfoAccessExtension
 * @modules java.base/sun.security.x509
 * @run main SubjectInfoAccessExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sun.security.x509.AccessDescription;
import sun.security.x509.SubjectInfoAccessExtension;

public class SubjectInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        List<AccessDescription> list = new ArrayList<>();
        SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(list);

        ByteArrayOutputStream out1 = new ByteArrayOutputStream();
        ext.encode(out1);
        byte[] b1 = out1.toByteArray();
        check(b1.length > 0, "encoded output must not be empty");
        check(b1[0] == 0x30, "encoded extension must start with a DER SEQUENCE tag");

        ByteArrayOutputStream out2 = new ByteArrayOutputStream();
        ext.encode(out2);
        check(Arrays.equals(b1, out2.toByteArray()),
              "repeated encode on the same instance must be deterministic");

        boolean npe = false;
        try {
            ext.encode(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "encode(null) must fail with NullPointerException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
