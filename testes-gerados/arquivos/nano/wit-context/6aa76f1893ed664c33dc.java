/* @test    @summary Test for EncryptedPrivateKeyInfo constructor with AlgorithmId and byte[] parameters    @run main EncryptedPrivateKeyInfoWitupTest */

import java.io.*;
import sun.security.pkcs.*;
import sun.security.util.*;

public class EncryptedPrivateKeyInfoWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithValidInputs();
        testConstructorWithNullEncoded();
    }

    static void testConstructorWithValidInputs() throws Exception {
        AlgorithmId algid = AlgorithmId.getInstance(new DerValue(new byte[] {0x06, 0x09, 0x2A, 0x86, 0x48, 0x86, 0xF7, 0x0D, 0x01}));
        byte[] encryptedData = {0x01, 0x02, 0x03};
        EncryptedPrivateKeyInfo info = new EncryptedPrivateKeyInfo(algid, encryptedData);
        assert info.getAlgorithm() != null : "Algorithm should not be null";
        assert java.util.Arrays.equals(info.getEncryptedData(), encryptedData) : "Encrypted data mismatch";
    }

    static void testConstructorWithNullEncoded() {
        try {
            new EncryptedPrivateKeyInfo((byte[])null);
            assert false : "Expected IllegalArgumentException for null encoded";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("encoding must not be null") : "Unexpected message: " + e.getMessage();
        } catch (Exception e) {
            assert false : "Unexpected exception: " + e;
        }
    }
}
