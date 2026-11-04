/*
 * @test
 * @summary Verifies System.getLogger(String, ResourceBundle) null checks and returned logger name
 * @run main SystemWitupTest
 */

import java.util.Collections;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class SystemWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        ResourceBundle rb = new ResourceBundle() {
            @Override
            protected Object handleGetObject(String key) {
                return null;
            }

            @Override
            public Enumeration<String> getKeys() {
                return Collections.emptyEnumeration();
            }
        };

        boolean npeForNullBundle = false;
        try {
            System.getLogger("test.logger", null);
        } catch (NullPointerException e) {
            npeForNullBundle = true;
        }
        check(npeForNullBundle, "expected NullPointerException for null bundle");

        boolean npeForNullName = false;
        try {
            System.getLogger(null, rb);
        } catch (NullPointerException e) {
            npeForNullName = true;
        }
        check(npeForNullName, "expected NullPointerException for null name");

        System.Logger logger = System.getLogger("test.logger", rb);
        check(logger != null, "getLogger returned null");
        check("test.logger".equals(logger.getName()),
              "unexpected logger name: " + logger.getName());
    }
}
