/*
 * @test
 * @summary Verifies X500Name.emit(DerOutputStream) produces deterministic DER output that round-trips through public APIs
 * @modules java.base/sun.security.x509
 * @modules java.base/sun.security.util
 * @run main X500NameWitupTest
 */

import java.util.Arrays;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.X500Name;

public class X500NameWitupTest {

    public static void main(String[] args) throws Exception {
        X500Name name = new X500Name("CN=Test User, OU=Unit, O=Example, C=US");

        DerOutputStream first = new DerOutputStream();
        name.emit(first);
        byte[] b1 = first.toByteArray();

        if (b1.length == 0 || b1[0] != 0x30) {
            throw new RuntimeException("emit output must start with a DER SEQUENCE tag");
        }

        DerOutputStream second = new DerOutputStream();
        name.emit(second);
        byte[] b2 = second.toByteArray();

        if (!Arrays.equals(b1, b2)) {
            throw new RuntimeException("emit output must be deterministic for the same name");
        }

        X500Name parsed = new X500Name(new DerValue(b1));
        if (!parsed.toString().equals(name.toString())) {
            throw new RuntimeException("emitted DER must round-trip to an equivalent name");
        }
    }
}
