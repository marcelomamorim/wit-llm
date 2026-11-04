/*
 * @test
 * @summary X500Name.emit(DerOutputStream) encodes the name so it round-trips through DerValue, is deterministic and appends to existing stream content
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
        X500Name name = new X500Name("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");

        DerOutputStream out = new DerOutputStream();
        name.emit(out);
        byte[] enc = out.toByteArray();

        if (enc.length == 0 || enc[0] != 0x30) {
            throw new RuntimeException("emit did not produce a SEQUENCE");
        }

        X500Name decoded = new X500Name(new DerValue(enc));
        if (!name.equals(decoded)) {
            throw new RuntimeException("round-trip mismatch");
        }

        DerOutputStream out2 = new DerOutputStream();
        name.emit(out2);
        if (!Arrays.equals(enc, out2.toByteArray())) {
            throw new RuntimeException("emit is not deterministic");
        }

        DerOutputStream prefixed = new DerOutputStream();
        prefixed.write(5);
        name.emit(prefixed);
        byte[] all = prefixed.toByteArray();
        if (all[0] != 5) {
            throw new RuntimeException("existing prefix byte was not preserved");
        }
        if (!Arrays.equals(enc, Arrays.copyOfRange(all, 1, all.length))) {
            throw new RuntimeException("emit did not append the same encoding");
        }
    }
}
