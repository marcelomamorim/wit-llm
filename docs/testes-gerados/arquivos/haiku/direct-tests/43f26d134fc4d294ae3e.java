/*
 * @test
 * @summary Verifies that LocalizedMessage.formatLocalized is deterministic and that varargs forms are equivalent
 * @modules java.base/sun.security.util
 * @run main LocalizedMessageWitupTest
 */

import sun.security.util.LocalizedMessage;

public class LocalizedMessageWitupTest {

    public static void main(String[] args) {
        LocalizedMessage msg = new LocalizedMessage("witup.test.key");

        String first = msg.formatLocalized();
        String second = msg.formatLocalized();
        if (first == null) {
            throw new RuntimeException("formatLocalized() returned null");
        }
        if (!first.equals(second)) {
            throw new RuntimeException("formatLocalized() is not deterministic: "
                    + first + " vs " + second);
        }

        Object[] none = new Object[0];
        String viaEmptyArray = msg.formatLocalized(none);
        if (!first.equals(viaEmptyArray)) {
            throw new RuntimeException("formatLocalized() with empty array differs: "
                    + first + " vs " + viaEmptyArray);
        }
    }
}
