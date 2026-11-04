/*
 * @test
 * @summary Witup generated checks for SubjectInfoAccessExtension.get(String)
 * @modules java.base/sun.security.x509
 * @run main SubjectInfoAccessExtensionWitupTest
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sun.security.x509.SubjectInfoAccessExtension;

public class SubjectInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        SubjectInfoAccessExtension ext =
                new SubjectInfoAccessExtension(new ArrayList<>());

        List<?> r = ext.get("descriptions");
        if (r == null) {
            throw new RuntimeException("get(\"descriptions\") returned null");
        }
        if (!r.isEmpty()) {
            throw new RuntimeException("expected empty list, got size " + r.size());
        }

        List<?> rUpper = ext.get("DESCRIPTIONS");
        if (rUpper == null || !rUpper.isEmpty()) {
            throw new RuntimeException("case-insensitive lookup failed");
        }

        try {
            ext.get("other");
            throw new RuntimeException("expected IOException for unknown name");
        } catch (IOException expected) {
            // expected behaviour
        }

        try {
            ext.get(null);
            throw new RuntimeException("expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected behaviour
        }
    }
}
