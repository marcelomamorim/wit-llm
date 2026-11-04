/*
 * @test
 * @summary Verify SignerInfo.encode(DerOutputStream) rejects a null output stream and null SignerInfo fields
 * @modules java.base/sun.security.pkcs
 * @run main SignerInfoWitupTest
 */

import sun.security.pkcs.SignerInfo;

public class SignerInfoWitupTest {

    public static void main(String[] args) throws Exception {
        SignerInfo info = new SignerInfo(null, null, null, null, null);
        boolean thrown = false;
        try {
            info.encode(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("encode(null) with null fields should throw NullPointerException");
        }

        SignerInfo info7 = new SignerInfo(null, null, null, (sun.security.pkcs.PKCS9Attributes) null,
                null, null, null);
        thrown = false;
        try {
            info7.encode(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("encode(null) with null fields (7-arg constructor) should throw NullPointerException");
        }
    }
}
