/*
 * @test
 * @summary Verifies ResourceBundle.clearCache(ClassLoader) rejects null and keeps bundles loadable afterwards
 * @run main ResourceBundleWitupTest
 */

import java.util.ListResourceBundle;
import java.util.ResourceBundle;

public class ResourceBundleWitupTest {

    public static class Bundle extends ListResourceBundle {
        @Override
        protected Object[][] getContents() {
            return new Object[][] { { "k", "v" } };
        }
    }

    public static void main(String[] args) throws Exception {
        boolean npe = false;
        try {
            ResourceBundle.clearCache(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new AssertionError("clearCache(null) must throw NullPointerException");
        }

        ClassLoader cl = ResourceBundleWitupTest.class.getClassLoader();
        ResourceBundle before = ResourceBundle.getBundle("ResourceBundleWitupTest$Bundle");
        if (!"v".equals(before.getString("k"))) {
            throw new AssertionError("unexpected value before clearCache");
        }

        ResourceBundle.clearCache(cl);

        ResourceBundle after = ResourceBundle.getBundle("ResourceBundleWitupTest$Bundle");
        if (!"v".equals(after.getString("k"))) {
            throw new AssertionError("bundle not loadable after clearCache");
        }

        ResourceBundle.clearCache(ClassLoader.getSystemClassLoader());
    }
}
