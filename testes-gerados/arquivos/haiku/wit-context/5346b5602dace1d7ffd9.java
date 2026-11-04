/*
 * @test
 * @summary Witup-derived smoke tests for PKCS10Attributes public constructors and DER encoding
 * @modules java.base/sun.security.pkcs10
 * @run main PKCS10AttributesWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.pkcs10.PKCS10Attributes;

public class PKCS10AttributesWitupTest {

    public static void main(String[] args) throws Exception {
        testDefaultConstructorEncodes();
        testEmptyArrayConstructorEncodes();
    }

    static void testDefaultConstructorEncodes() throws IOException {
        PKCS10Attributes attrs = new PKCS10Attributes();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        attrs.encode(out);
        if (out.size() == 0) {
            throw new RuntimeException("Empty attributes produced no DER output");
        }
    }

    static void testEmptyArrayConstructorEncodes() throws IOException {
        PKCS10Attributes attrs = new PKCS10Attributes(new sun.security.pkcs10.PKCS10Attribute[0]);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        attrs.derEncode(out);
        if (out.size() == 0) {
            throw new RuntimeException("Empty array constructor produced no DER output");
        }
    }
}
