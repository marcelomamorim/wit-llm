/*
 * @test
 * @summary Witup-derived checks for System.getLogger(String, ResourceBundle) null guards and basic success path
 * @run main SystemWitupTest
 */

import java.util.Collections;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class SystemWitupTest {

    static class EmptyBundle extends ResourceBundle {
        @Override
        protected Object handleGetObject(String key) {
            return null;
        }

        @Override
        public Enumeration<String> getKeys() {
            return Collections.emptyEnumeration();
        }
    }

    static void expectNPE(Runnable r, String what) {
        try {
            r.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("Expected NullPointerException: " + what);
    }

    public static void main(String[] args) {
        ResourceBundle bundle = new EmptyBundle();

        expectNPE(() -> System.getLogger("witup.test", null),
                  "bundle == null with non-null name");

        expectNPE(() -> System.getLogger(null, bundle),
                  "name == null with non-null bundle");

        expectNPE(() -> System.getLogger(null, null),
                  "both name and bundle null");

        System.Logger logger = System.getLogger("witup.test", bundle);
        if (logger == null) {
            throw new AssertionError("getLogger returned null for valid arguments");
        }
    }
}
