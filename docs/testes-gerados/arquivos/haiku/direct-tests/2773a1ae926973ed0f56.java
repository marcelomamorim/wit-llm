/*
 * @test
 * @summary Verifies GetPropertyAction.run() returns the system property value, the default value when the property is absent, null when no default is given, and propagates NullPointerException for a null key.
 * @modules java.base/sun.security.action
 * @run main GetPropertyActionWitupTest
 */

import sun.security.action.GetPropertyAction;

public class GetPropertyActionWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        String present = "witup.getprop.present";
        String absent = "witup.getprop.absent";
        String absentNoDefault = "witup.getprop.absent.nodefault";
        String empty = "witup.getprop.empty";

        try {
            System.setProperty(present, "valor");
            System.clearProperty(absent);
            System.clearProperty(absentNoDefault);
            System.setProperty(empty, "");

            String r1 = new GetPropertyAction(present, "padrao").run();
            check("valor".equals(r1), "Expected property value, got: " + r1);

            String r2 = new GetPropertyAction(absent, "padrao").run();
            check("padrao".equals(r2), "Expected default value, got: " + r2);

            String r3 = new GetPropertyAction(absentNoDefault).run();
            check(r3 == null, "Expected null for absent property without default, got: " + r3);

            String r4 = new GetPropertyAction(empty, "padrao").run();
            check("".equals(r4), "Expected empty string to be returned over default, got: " + r4);

            String r5 = new GetPropertyAction(present).run();
            check("valor".equals(r5), "Expected property value without default, got: " + r5);

            boolean npe = false;
            try {
                new GetPropertyAction(null).run();
            } catch (NullPointerException e) {
                npe = true;
            }
            check(npe, "Expected NullPointerException for null property name");
        } finally {
            System.clearProperty(present);
            System.clearProperty(absent);
            System.clearProperty(absentNoDefault);
            System.clearProperty(empty);
        }
    }
}
