/*
 * @test
 * @summary Tests for sun.security.x509.IssuingDistributionPointExtension.get(String)
 * @run main IssuingDistributionPointExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.IssuingDistributionPointExtension;

public class IssuingDistributionPointExtensionWitupTest {

    public static void main(String[] args) {
        testGetMethodRecognizedNames();
        testGetMethodUnrecognizedName();
    }

    private static void testGetMethodRecognizedNames() {
        try {
            IssuingDistributionPointExtension ext = createSampleExtension();
            String[] recognizedNames = {
                "point",
                "indirect_crl",
                "reasons",
                "only_user_certs",
                "only_ca_certs",
                "only_attribute_certs"
            };
            for (String name : recognizedNames) {
                Object result = ext.get(name);
                if (name.equalsIgnoreCase("indirect_crl")) {
                    if (!(result instanceof Boolean)) {
                        throw new AssertionError("Expected Boolean for " + name);
                    }
                } else {
                    if (result == null) {
                        throw new AssertionError("Result null for " + name);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in recognized names test", e);
        }
    }

    private static void testGetMethodUnrecognizedName() {
        try {
            IssuingDistributionPointExtension ext = createSampleExtension();
            String unrecognizedName = "unknown_attribute";
            boolean thrown = false;
            try {
                ext.get(unrecognizedName);
            } catch (IOException e) {
                thrown = true;
                if (!e.getMessage().contains(unrecognizedName)) {
                    throw new AssertionError("Exception message does not contain attribute name") ;
                }
            }
            if (!thrown) {
                throw new AssertionError("Expected IOException for unrecognized attribute") ;
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in unrecognized name test", e);
        }
    }

    private static IssuingDistributionPointExtension createSampleExtension() throws IOException {
        // Create a dummy instance with minimal setup for testing
        // Since constructor requires specific parameters, use reflection or dummy subclass
        // For simplicity, assume a constructor exists that can be called with dummy data
        // But as per context, only public constructors are available, so we simulate with a subclass
        return new DummyIssuingDistributionPointExtension();
    }

    private static class DummyIssuingDistributionPointExtension extends IssuingDistributionPointExtension {
        public DummyIssuingDistributionPointExtension() throws IOException {
            super("true", null); // Using the second constructor with critical=true and null value
        }
        @Override
        public Object get(String name) throws IOException {
            // Use the real method implementation
            return super.get(name);
        }
    }
}
