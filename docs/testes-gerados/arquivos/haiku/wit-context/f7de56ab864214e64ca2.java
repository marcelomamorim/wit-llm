/*
 * @test
 * @summary ResourceBundle.containsKey must reject null keys and report present and absent keys correctly
 * @run main ResourceBundleWitupTest
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class ResourceBundleWitupTest {

    static class TestBundle extends ResourceBundle {
        @Override
        protected Object handleGetObject(String key) {
            return "present".equals(key) ? "value" : null;
        }

        @Override
        public Enumeration<String> getKeys() {
            return Collections.enumeration(Arrays.asList("present"));
        }
    }

    public static void main(String[] args) {
        ResourceBundle rb = new TestBundle();

        if (!rb.containsKey("present")) {
            throw new RuntimeException("containsKey(\"present\") should return true");
        }
        if (rb.containsKey("absent")) {
            throw new RuntimeException("containsKey(\"absent\") should return false");
        }

        boolean npeThrown = false;
        try {
            rb.containsKey(null);
        } catch (NullPointerException expected) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("containsKey(null) should throw NullPointerException");
        }
    }
}
