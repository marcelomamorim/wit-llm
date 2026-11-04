/*
 * @test
 * @summary Basic observable behavior of Subject.toString()
 * @run main SubjectWitupTest
 */

import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.Subject;

public class SubjectWitupTest {

    static final class MarkerPrincipal implements Principal {
        private final String marker;
        MarkerPrincipal(String marker) { this.marker = marker; }
        public String getName() { return marker; }
        public String toString() { return marker; }
    }

    static final class MarkerCredential {
        private final String marker;
        MarkerCredential(String marker) { this.marker = marker; }
        public String toString() { return marker; }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptySubject() {
        Subject s = new Subject();
        String first = s.toString();
        check(first != null, "toString returned null for empty subject");
        check(!first.isEmpty(), "toString returned empty string for empty subject");
        check(first.equals(s.toString()), "toString not deterministic for empty subject");
    }

    static void testPrincipalAppears() {
        Set<Principal> principals = new HashSet<>();
        principals.add(new MarkerPrincipal("PRINCIPAL_MARKER_42"));
        Subject s = new Subject(false, principals,
                Collections.emptySet(), Collections.emptySet());
        String out = s.toString();
        check(out.contains("PRINCIPAL_MARKER_42"),
                "principal not present in toString: " + out);
    }

    static void testPublicCredentialAppears() {
        Set<Object> pub = new HashSet<>();
        pub.add(new MarkerCredential("PUB_MARKER_77"));
        Subject s = new Subject(false, Collections.emptySet(),
                pub, Collections.emptySet());
        String out = s.toString();
        check(out.contains("PUB_MARKER_77"),
                "public credential not present in toString: " + out);
    }

    static void testPrivateCredentialAppears() {
        Set<Object> priv = new HashSet<>();
        priv.add(new MarkerCredential("PRIV_MARKER_99"));
        Subject s = new Subject(false, Collections.emptySet(),
                Collections.emptySet(), priv);
        String out = s.toString();
        check(out.contains("PRIV_MARKER_99"),
                "private credential not present in toString: " + out);
    }

    static void testReadOnlyStillRenders() {
        Set<Principal> principals = new HashSet<>();
        principals.add(new MarkerPrincipal("RO_MARKER_13"));
        Subject s = new Subject(true, principals,
                Collections.emptySet(), Collections.emptySet());
        String out = s.toString();
        check(out != null && out.contains("RO_MARKER_13"),
                "read-only subject toString missing principal: " + out);
    }

    public static void main(String[] args) {
        testEmptySubject();
        testPrincipalAppears();
        testPublicCredentialAppears();
        testPrivateCredentialAppears();
        testReadOnlyStillRenders();
    }
}
