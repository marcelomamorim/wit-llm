/*
 * @test
 * @summary Verify AVA constructor rejects null arguments and accepts valid ones
 * @modules java.base/sun.security.util java.base/sun.security.x509
 * @run main AVAWitupTest
 */

import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AVA;

public class AVAWitupTest {

    public static void main(String[] args) throws Exception {
        ObjectIdentifier oid = new ObjectIdentifier("2.5.4.3");
        DerValue val = new DerValue(DerValue.tag_PrintableString,
                new byte[] {'a', 'b', 'c'});

        AVA ava = new AVA(oid, val);
        if (ava == null) {
            throw new RuntimeException("AVA construction returned null");
        }

        AVA ava2 = new AVA(oid, val);
        if (ava2 == ava) {
            throw new RuntimeException("Expected distinct AVA instances");
        }

        expectNPE(() -> new AVA(null, val));
        expectNPE(() -> new AVA(oid, null));
        expectNPE(() -> new AVA(null, null));
    }

    static void expectNPE(Runnable r) {
        try {
            r.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException");
    }
}
