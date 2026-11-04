/*
 * @test
 * @summary Verify SubjectInfoAccessExtension.encode(OutputStream) produces a DER SEQUENCE, is deterministic across repeated calls, and reflects the access descriptions
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main SubjectInfoAccessExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AccessDescription;
import sun.security.x509.SubjectInfoAccessExtension;
import sun.security.x509.URIName;

public class SubjectInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        String uri = "http://example.com/ca.crt";
        List<AccessDescription> list = new ArrayList<>();
        list.add(new AccessDescription(
                new ObjectIdentifier("1.3.6.1.5.5.7.48.2"), new URIName(uri)));
        SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(list);

        if (ext.isCritical()) {
            throw new RuntimeException("extension must be non-critical");
        }

        ByteArrayOutputStream out1 = new ByteArrayOutputStream();
        ext.encode(out1);
        byte[] b1 = out1.toByteArray();
        if (b1.length == 0 || b1[0] != 0x30) {
            throw new RuntimeException("encoding is not a DER SEQUENCE");
        }
        String asText = new String(b1, StandardCharsets.ISO_8859_1);
        if (!asText.contains(uri)) {
            throw new RuntimeException("encoded value does not contain access location");
        }

        ByteArrayOutputStream out2 = new ByteArrayOutputStream();
        ext.encode(out2);
        if (!Arrays.equals(b1, out2.toByteArray())) {
            throw new RuntimeException("repeated encode produced different bytes");
        }

        List<AccessDescription> other = new ArrayList<>();
        other.add(new AccessDescription(
                new ObjectIdentifier("1.3.6.1.5.5.7.48.2"),
                new URIName("http://example.org/other.crt")));
        SubjectInfoAccessExtension ext2 = new SubjectInfoAccessExtension(other);
        ByteArrayOutputStream out3 = new ByteArrayOutputStream();
        ext2.encode(out3);
        if (Arrays.equals(b1, out3.toByteArray())) {
            throw new RuntimeException("different access locations encoded identically");
        }
    }
}
