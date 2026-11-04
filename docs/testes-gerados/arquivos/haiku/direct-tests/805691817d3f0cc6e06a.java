/*
 * @test
 * @summary Verify X500Name.encode produces a DER SEQUENCE that round-trips and is deterministic
 * @modules java.base/sun.security.x509
 *          java.base/sun.security.util
 * @run main X500NameWitupTest
 */

import java.util.Arrays;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.X500Name;

public class X500NameWitupTest {

    public static void main(String[] args) throws Exception {
        X500Name name = new X500Name("CN=Duke, OU=JavaSoft, O=Sun, C=US");

        DerOutputStream out = new DerOutputStream();
        name.encode(out);
        byte[] enc = out.toByteArray();

        if (enc.length == 0) {
            throw new RuntimeException("encode produced no bytes");
        }
        if (enc[0] != DerValue.tag_Sequence) {
            throw new RuntimeException("outer tag is not SEQUENCE: " + enc[0]);
        }

        DerValue value = new DerValue(enc);
        if (value.getTag() != DerValue.tag_Sequence) {
            throw new RuntimeException("decoded tag is not SEQUENCE");
        }

        X500Name back = new X500Name(value);
        if (!name.equals(back)) {
            throw new RuntimeException("round-trip produced a different name");
        }

        DerOutputStream out2 = new DerOutputStream();
        back.encode(out2);
        if (!Arrays.equals(enc, out2.toByteArray())) {
            throw new RuntimeException("re-encoding round-tripped name changed bytes");
        }

        DerOutputStream out3 = new DerOutputStream();
        name.encode(out3);
        if (!Arrays.equals(enc, out3.toByteArray())) {
            throw new RuntimeException("encoding is not deterministic");
        }
    }
}
