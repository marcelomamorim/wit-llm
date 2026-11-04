/*
 * @test
 * @summary Test javax.crypto.Mac.update(byte[]) for IllegalStateException when not initialized
 * @run main MacWitupTest
 */

import javax.crypto.Mac;
import javax.crypto.MacSpi;
import java.security.Provider;

public class MacWitupTest {

    // Minimal MacSpi stub that does nothing
    static class DummyMacSpi extends MacSpi {
        protected void engineUpdate(byte input) { }
        protected void engineUpdate(byte[] input, int offset, int len) { }
        protected byte[] engineDoFinal() { return new byte[0]; }
        protected void engineReset() { }
        protected int engineGetMacLength() { return 0; }
        protected void engineInit(java.security.Key key, java.security.spec.AlgorithmParameterSpec params) { }
    }

    public static void main(String[] args) throws Exception {
        // Test 1: update with uninitialized Mac should throw IllegalStateException
        Mac mac = Mac.getInstance("HmacSHA256");
        boolean threw = false;
        try {
            mac.update(new byte[] {1, 2, 3});
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException when updating uninitialized Mac");
        }

        // Test 2: update with null input after init should not throw
        mac.init(new javax.crypto.spec.SecretKeySpec(new byte[16], "HmacSHA256"));
        mac.update((byte[]) null); // should not throw

        // Test 3: update with valid input after init should not throw
        mac.update(new byte[] {10, 20, 30});

        // Test 4: create Mac with DummyMacSpi and test update throws if not initialized
        Mac mac2 = new Mac(new DummyMacSpi(), new Provider("TestProvider", 1.0, "Test") {}, "Dummy");
        threw = false;
        try {
            mac2.update(new byte[] {1});
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException when updating uninitialized Mac with DummyMacSpi");
        }

        System.out.println("All tests passed.");
    }
}
