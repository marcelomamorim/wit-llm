/*
 * @test
 * @summary Checks observable behavior of X509Key.encode(): it either throws InvalidKeyException or returns a defensive copy of a stable encoding.
 * @modules java.base/sun.security.x509
 * @run main X509KeyWitupTest
 */

import sun.security.x509.X509Key;
import java.security.InvalidKeyException;
import java.util.Arrays;

public class X509KeyWitupTest {

    public static void main(String[] args) throws Exception {
        X509Key key = new X509Key();
        byte[] first;
        try {
            first = key.encode();
        } catch (InvalidKeyException e) {
            // An uninitialized default key cannot be encoded; this is an acceptable observable outcome.
            return;
        }

        if (first == null) {
            throw new RuntimeException("encode() returned null");
        }

        byte[] second = key.encode();
        if (!Arrays.equals(first, second)) {
            throw new RuntimeException("encode() returned different content on repeated calls");
        }
        if (first == second) {
            throw new RuntimeException("encode() must return a copy, not the same array instance");
        }

        if (first.length > 0) {
            byte saved = first[0];
            first[0] = (byte) (saved ^ 0xFF);
            byte[] third = key.encode();
            if (third[0] != saved) {
                throw new RuntimeException("Mutating returned array affected subsequent encode() result");
            }
        }
    }
}
