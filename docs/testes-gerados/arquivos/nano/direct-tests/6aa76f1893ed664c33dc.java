/* @test    @summary Test constructor EncryptedPrivateKeyInfo(AlgorithmId, byte[]) for sun.security.pkcs.EncryptedPrivateKeyInfo    @run main EncryptedPrivateKeyInfoWitupTest */

import java.io.*;
import sun.security.pkcs.*;
import sun.security.util.*;

public class EncryptedPrivateKeyInfoWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithValidInputs();
        testConstructorWithNullAlgorithmId();
        testConstructorWithEmptyEncryptedData();
    }

    static void testConstructorWithValidInputs() {
        AlgorithmId algId = new AlgorithmId(); // Assuming default constructor exists
        byte[] encryptedData = {1, 2, 3, 4};
        EncryptedPrivateKeyInfo info = new EncryptedPrivateKeyInfo(algId, encryptedData);
        assert info.getAlgorithm() != null : "Algorithm should not be null";
        assert java.util.Arrays.equals(info.getEncryptedData(), encryptedData) : "Encrypted data mismatch";
    }

    static void testConstructorWithNullAlgorithmId() {
        byte[] encryptedData = {5, 6, 7};
        try {
            new EncryptedPrivateKeyInfo(null, encryptedData);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testConstructorWithEmptyEncryptedData() {
        AlgorithmId algId = new AlgorithmId(); // Assuming default constructor exists
        byte[] encryptedData = {};
        EncryptedPrivateKeyInfo info = new EncryptedPrivateKeyInfo(algId, encryptedData);
        assert info.getEncryptedData() != null : "Encrypted data should not be null";
        assert info.getEncryptedData().length == 0 : "Encrypted data should be empty";
    }
}
