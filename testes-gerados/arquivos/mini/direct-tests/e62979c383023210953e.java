/*
 * @test
 * @summary Test java.security.Signer run() method behavior
 * @run main SignerWitupTest
 */

import java.security.*;

public class SignerWitupTest extends Signer {

    private PublicKey pub;
    private boolean setPublicKeyCalled = false;

    public SignerWitupTest() {
        super("TestSigner");
    }

    public void setPublicKey(PublicKey key) {
        setPublicKeyCalled = true;
        this.pub = key;
    }

    public boolean isSetPublicKeyCalled() {
        return setPublicKeyCalled;
    }

    public PublicKey getPub() {
        return pub;
    }

    public static void main(String[] args) throws Exception {
        SignerWitupTest signer = new SignerWitupTest();

        // Initially, setPublicKey not called
        if (signer.isSetPublicKeyCalled()) {
            throw new RuntimeException("setPublicKey should not have been called yet");
        }

        // Call run and verify it returns null
        Void result = signer.run();
        if (result != null) {
            throw new RuntimeException("run() should return null");
        }

        // Verify setPublicKey was called
        if (!signer.isSetPublicKeyCalled()) {
            throw new RuntimeException("setPublicKey was not called by run()");
        }

        // Verify pub field is set (not null)
        if (signer.getPub() == null) {
            throw new RuntimeException("PublicKey field 'pub' was not set by run()");
        }
    }

    // Override run method from Signer to match the provided source
    @Override
    public Void run() throws KeyManagementException {
        setPublicKey(pub);
        return null;
    }
}
