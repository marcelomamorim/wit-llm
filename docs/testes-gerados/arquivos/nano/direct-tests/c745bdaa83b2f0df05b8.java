/* @test    @summary Test constructor of sun.security.x509.OtherName for valid and null parameters    @run main sun.security.x509.OtherNameWitupTest */

import java.io.IOException;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.OtherName;

public class OtherNameWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorValid();
        testConstructorNullOid();
        testConstructorNullValue();
    }

    static void testConstructorValid() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
        byte[] value = {0x01, 0x02, 0x03};
        OtherName oname = new OtherName(oid, value);
        assert oname.getOID().equals(oid) : "OID mismatch";
        assert java.util.Arrays.equals(oname.getNameValue(), value) : "Value mismatch";
        System.out.println("testConstructorValid passed");
    }

    static void testConstructorNullOid() {
        try {
            new OtherName(null, new byte[]{0x01});
            assert false : "Expected NullPointerException for null OID";
        } catch (NullPointerException e) {
            assert e.getMessage() == null || e.getMessage().contains("parameters may not be null") : "Unexpected message";
            System.out.println("testConstructorNullOid passed");
        } catch (Exception e) {
            assert false : "Unexpected exception: " + e;
        }
    }

    static void testConstructorNullValue() {
        try {
            ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
            new OtherName(oid, null);
            assert false : "Expected NullPointerException for null value";
        } catch (NullPointerException e) {
            assert e.getMessage() == null || e.getMessage().contains("parameters may not be null") : "Unexpected message";
            System.out.println("testConstructorNullValue passed");
        } catch (Exception e) {
            assert false : "Unexpected exception: " + e;
        }
    }
}
