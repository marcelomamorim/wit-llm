/*
 * @test
 * @summary Checks public encode() on a default X509Key: either a documented InvalidKeyException or a defensive copy of the encoding
 * @modules java.base/sun.security.x509
 * @run main X509KeyWitupTest
 */

import java.security.InvalidKeyException;
import java.util.Arrays;
import sun.security.x509.X509Key;

public class X509KeyWitupTest {

    public static void main(String[] args) throws Exception {
        X509Key key = new X509Key();
        byte[] first;
        try {
            first = key.encode();
        } catch (InvalidKeyException e) {
            return;
        }
        if (first == null) {
            throw new RuntimeException("encode() returned null");
        }
        byte[] second = key.encode();
        if (first == second) {
            throw new RuntimeException("encode() must return a fresh array on each call");
        }
        if (!Arrays.equals(first, second)) {
            throw new RuntimeException("encode() must be stable across calls");
        }
        if (second.length > 0) {
            second[0] = (byte) ~second[0];
            byte[] third = key.encode();
            if (!Arrays.equals(first, third)) {
                throw new RuntimeException("mutating returned array must not change the key encoding");
            }
        }
    }
}
