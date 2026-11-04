/*
 * @test
 * @summary Verify Subject principals addAll null handling and basic set semantics
 * @run main SubjectWitupTest
 */

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.security.auth.Subject;

public class SubjectWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static final class TestPrincipal implements Principal {
        private final String name;

        TestPrincipal(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static void main(String[] args) {
        Subject nullCase = new Subject();
        boolean threw = false;
        try {
            nullCase.getPrincipals().addAll(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        check(threw, "addAll(null) must throw NullPointerException");
        check(nullCase.getPrincipals().isEmpty(), "principals must remain empty after failed addAll");

        Subject subject = new Subject();
        TestPrincipal a = new TestPrincipal("a");
        TestPrincipal b = new TestPrincipal("b");
        List<Principal> list = new ArrayList<>(Arrays.asList(a, b));

        boolean first = subject.getPrincipals().addAll(list);
        check(first, "addAll of new principals must return true");
        check(subject.getPrincipals().size() == 2, "two principals expected after addAll");
        check(subject.getPrincipals().contains(a), "principal a must be present");
        check(subject.getPrincipals().contains(b), "principal b must be present");

        boolean second = subject.getPrincipals().addAll(list);
        check(!second, "addAll of already present principals must return false");
        check(subject.getPrincipals().size() == 2, "size must stay 2 after duplicate addAll");

        Subject empty = new Subject();
        boolean emptyResult = empty.getPrincipals().addAll(new ArrayList<Principal>());
        check(!emptyResult, "addAll of empty collection must return false");
        check(empty.getPrincipals().isEmpty(), "principals must stay empty after empty addAll");
    }
}
