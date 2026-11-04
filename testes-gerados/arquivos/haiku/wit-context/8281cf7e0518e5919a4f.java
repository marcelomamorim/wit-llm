/*
 * @test
 * @summary Witup-generated checks for jdk.internal.misc.VM.getSavedProperty
 * @modules java.base/jdk.internal.misc
 * @run main VMWitupTest
 */

import jdk.internal.misc.VM;

public class VMWitupTest {

    public static void main(String[] args) {
        String missing = VM.getSavedProperty("witup.no.such.saved.property.key");
        if (missing != null) {
            throw new RuntimeException("Expected null for unknown key, got: " + missing);
        }

        String again = VM.getSavedProperty("witup.no.such.saved.property.key");
        if (again != null) {
            throw new RuntimeException("Expected null on repeated lookup, got: " + again);
        }

        String nullKeyValue;
        try {
            nullKeyValue = VM.getSavedProperty(null);
        } catch (NullPointerException e) {
            nullKeyValue = null;
        }
        if (nullKeyValue != null) {
            throw new RuntimeException("Expected null result for null key, got: " + nullKeyValue);
        }
    }
}
