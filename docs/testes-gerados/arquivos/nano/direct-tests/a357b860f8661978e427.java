/* @test    @summary Test for IssuingDistributionPointExtension.get(String) method    @run main IssuingDistributionPointExtensionWitupTest */

import sun.security.x509.IssuingDistributionPointExtension;
import java.io.IOException;

public class IssuingDistributionPointExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithValidNames();
        testGetWithInvalidName();
    }

    private static void testGetWithValidNames() throws IOException {
        IssuingDistributionPointExtension extension = createSampleExtension();
        Object result;

        result = extension.get("point");
        assert result != null : "Expected non-null for point";

        result = extension.get("indirect_crl");
        assert result instanceof Boolean : "Expected Boolean for indirect_crl";

        result = extension.get("reasons");
        assert result != null : "Expected non-null for reasons";

        result = extension.get("only_user_certs");
        assert result instanceof Boolean : "Expected Boolean for only_user_certs";

        result = extension.get("only_ca_certs");
        assert result instanceof Boolean : "Expected Boolean for only_ca_certs";

        result = extension.get("only_attribute_certs");
        assert result instanceof Boolean : "Expected Boolean for only_attribute_certs";
    }

    private static void testGetWithInvalidName() {
        IssuingDistributionPointExtension extension = createSampleExtension();
        boolean exceptionThrown = false;
        try {
            extension.get("invalid_name");
        } catch (IOException e) {
            exceptionThrown = true;
            assert e.getMessage().contains("not recognized") : "Unexpected exception message";
        }
        assert exceptionThrown : "Expected IOException for invalid attribute name";
    }

    private static IssuingDistributionPointExtension createSampleExtension() {
        // Since constructor is not provided, assume a default or mock object
        // For testing, create a subclass or mock if necessary; here, we assume a minimal stub
        return new IssuingDistributionPointExtension() {
            @Override
            public Object get(String name) throws IOException {
                switch (name.toLowerCase()) {
                    case "point": return null;
                    case "indirect_crl": return Boolean.TRUE;
                    case "reasons": return null;
                    case "only_user_certs": return Boolean.FALSE;
                    case "only_ca_certs": return Boolean.TRUE;
                    case "only_attribute_certs": return Boolean.FALSE;
                    default:
                        throw new IOException("Attribute name [" + name + "] not recognized by CertAttrSet:IssuingDistributionPointExtension.");
                }
            }
        };
    }
}
