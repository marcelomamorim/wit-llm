/*
 * @test
 * @summary Test Identity.setPublicKey behavior
 * @run main IdentityWitupTest
 */

import java.security.*;
import java.util.Vector;

public class IdentityWitupTest extends Identity {

    private boolean checkCalled = false;

    public IdentityWitupTest() {
        super("testIdentity");
    }

    // Override check to simulate permission check
    protected void check(String action) throws KeyManagementException {
        checkCalled = true;
        if (!"setIdentityPublicKey".equals(action)) {
            throw new KeyManagementException("Invalid action: " + action);
        }
    }

    public static void main(String[] args) throws Exception {
        IdentityWitupTest identity = new IdentityWitupTest();

        // Test 1: setPublicKey sets the key and resets certificates
        identity.checkCalled = false;
        PublicKey dummyKey = new DummyPublicKey();
        identity.setPublicKey(dummyKey);

        if (!identity.checkCalled) {
            throw new RuntimeException("check method was not called");
        }

        if (identity.publicKey != dummyKey) {
            throw new RuntimeException("publicKey was not set correctly");
        }

        if (identity.certificates == null) {
            throw new RuntimeException("certificates vector is null after setPublicKey");
        }

        if (!(identity.certificates instanceof Vector)) {
            throw new RuntimeException("certificates is not a Vector instance");
        }

        if (!identity.certificates.isEmpty()) {
            throw new RuntimeException("certificates vector should be empty after setPublicKey");
        }

        // Test 2: setPublicKey throws KeyManagementException if check throws
        IdentityWitupTest identity2 = new IdentityWitupTest() {
            @Override
            protected void check(String action) throws KeyManagementException {
                throw new KeyManagementException("forced failure");
            }
        };

        boolean threw = false;
        try {
            identity2.setPublicKey(dummyKey);
        } catch (KeyManagementException e) {
            threw = true;
            if (!"forced failure".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }

        if (!threw) {
            throw new RuntimeException("Expected KeyManagementException was not thrown");
        }
    }

    // Dummy PublicKey implementation for testing
    private static class DummyPublicKey implements PublicKey {
        private static final long serialVersionUID = 1L;

        public String getAlgorithm() {
            return "DummyAlgorithm";
        }

        public String getFormat() {
            return "DummyFormat";
        }

        public byte[] getEncoded() {
            return new byte[0];
        }
    }
}
