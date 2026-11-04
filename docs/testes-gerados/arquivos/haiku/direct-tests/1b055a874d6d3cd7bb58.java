/*
 * @test
 * @summary GeneralNames.add appends non-null names, returns this, and rejects null with NullPointerException
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main GeneralNamesWitupTest
 */

import sun.security.util.DerValue;
import sun.security.x509.GeneralName;
import sun.security.x509.GeneralNames;

public class GeneralNamesWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] enc = {0x30, 0x07, (byte) 0x82, 0x05, 'a', '.', 'c', 'o', 'm'};
        GeneralNames source = new GeneralNames(new DerValue(enc));
        GeneralName name = source.get(0);

        GeneralNames names = new GeneralNames();
        check(names.isEmpty());
        check(names.size() == 0);

        GeneralNames ret = names.add(name);
        check(ret == names);
        check(names.size() == 1);
        check(!names.isEmpty());
        check(names.get(0) == name);

        names.add(name);
        check(names.size() == 2);
        check(names.names().size() == 2);

        try {
            names.add(null);
            throw new RuntimeException("add(null) did not throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(names.size() == 2);
    }

    static void check(boolean condition) {
        if (!condition) {
            throw new RuntimeException("Check failed");
        }
    }
}
