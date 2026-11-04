/*
 * @test
 * @summary Verifies Pattern.compile(String,int) rejects unknown flag bits and accepts valid flag combinations
 * @run main PatternWitupTest
 */
import java.util.regex.Pattern;

public class PatternWitupTest {
    public static void main(String[] args) {
        checkValidFlagsAccepted();
        checkLiteralFlagObservable();
        checkUnknownFlagRejected(0x80000000);
        checkUnknownFlagRejected(Pattern.CASE_INSENSITIVE | 0x80000000);
        checkUnknownFlagRejected(1 << 20);
    }

    static void checkValidFlagsAccepted() {
        Pattern p = Pattern.compile("abc", Pattern.CASE_INSENSITIVE | Pattern.COMMENTS);
        if (!p.matcher("ABC").matches()) {
            throw new RuntimeException("CASE_INSENSITIVE|COMMENTS did not match ABC");
        }
    }

    static void checkLiteralFlagObservable() {
        Pattern p = Pattern.compile("a.b", Pattern.LITERAL);
        if (!p.matcher("a.b").matches()) {
            throw new RuntimeException("LITERAL pattern did not match literal text");
        }
        if (p.matcher("axb").matches()) {
            throw new RuntimeException("LITERAL pattern treated dot as metacharacter");
        }
    }

    static void checkUnknownFlagRejected(int flags) {
        try {
            Pattern.compile("a", flags);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for flags " + flags);
    }
}
