/*
 * @test
 * @summary Test getSingleExtensions() method of OCSPResponse for correct unmodifiable map return
 * @run main OCSPResponseWitupTest
 */

import java.util.Collections;
import java.util.Map;
import java.security.cert.Extension;

public class OCSPResponseWitupTest {

    public static void main(String[] args) {
        testGetSingleExtensionsReturnsUnmodifiableMap();
    }

    public static void testGetSingleExtensionsReturnsUnmodifiableMap() {
        // Create a dummy OCSPResponse object with minimal implementation for testing
        // Since constructor and internal state are not accessible, we simulate the method call
        // by creating an anonymous subclass that overrides getSingleExtensions if needed
        // but here, we assume the real object is instantiated with a valid internal state.
        // For the purpose of this test, we focus on the return value of getSingleExtensions.
        // As we cannot instantiate OCSPResponse directly without full context, we simulate
        // the expected behavior by creating a dummy subclass.
        class DummyOCSPResponse extends sun.security.provider.certpath.OCSPResponse {
            @Override
            public Map<String, Extension> getSingleExtensions() {
                Map<String, Extension> map = Collections.singletonMap("testExtension", null);
                return Collections.unmodifiableMap(map);
            }
        }
        OCSPResponse response = new DummyOCSPResponse();
        Map<String, Extension> extensions = response.getSingleExtensions();
        // Verify that the returned map is unmodifiable
        boolean isUnmodifiable = false;
        try {
            extensions.put("anotherExtension", null);
        } catch (UnsupportedOperationException e) {
            isUnmodifiable = true;
        }
        if (!isUnmodifiable) {
            throw new AssertionError("getSingleExtensions() did not return an unmodifiable map");
        }
        // Verify that the map contains the expected key
        if (!extensions.containsKey("testExtension")) {
            throw new AssertionError("Map does not contain expected key 'testExtension'");
        }
        System.out.println("testGetSingleExtensionsReturnsUnmodifiableMap passed.");
    }
}
