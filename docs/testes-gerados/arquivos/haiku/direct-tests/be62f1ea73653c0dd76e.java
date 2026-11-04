/*
 * @test
 * @summary Checks Textifier.visitLineNumber output: LINENUMBER line, label name and label reuse
 * @modules java.base/jdk.internal.org.objectweb.asm
 *          java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        singleLineNumberIsPrinted();
        sameLabelGetsSameName();
        differentLabelsGetDifferentNames();
        multipleCallsAccumulate();
    }

    static void singleLineNumberIsPrinted() {
        Textifier t = new Textifier();
        Label l = new Label();
        t.visitLineNumber(42, l);
        String out = dump(t);
        check(out.contains("LINENUMBER 42 "), "missing LINENUMBER 42 in: " + out);
        String name = nameAfter(out, "LINENUMBER 42 ");
        check(name.length() > 0, "empty label name in: " + out);
    }

    static void sameLabelGetsSameName() {
        Textifier t = new Textifier();
        Label a = new Label();
        t.visitLineNumber(7, a);
        t.visitLineNumber(8, a);
        String out = dump(t);
        String n1 = nameAfter(out, "LINENUMBER 7 ");
        String n2 = nameAfter(out, "LINENUMBER 8 ");
        check(n1.equals(n2), "same label printed with different names: " + n1 + " vs " + n2);
    }

    static void differentLabelsGetDifferentNames() {
        Textifier t = new Textifier();
        Label a = new Label();
        Label b = new Label();
        t.visitLineNumber(7, a);
        t.visitLineNumber(9, b);
        String out = dump(t);
        String na = nameAfter(out, "LINENUMBER 7 ");
        String nb = nameAfter(out, "LINENUMBER 9 ");
        check(!na.equals(nb), "different labels printed with same name: " + na);
    }

    static void multipleCallsAccumulate() {
        Textifier t = new Textifier();
        Label a = new Label();
        t.visitLineNumber(1, a);
        t.visitLineNumber(2, a);
        t.visitLineNumber(3, a);
        String out = dump(t);
        check(out.contains("LINENUMBER 1 "), "missing line 1 in: " + out);
        check(out.contains("LINENUMBER 2 "), "missing line 2 in: " + out);
        check(out.contains("LINENUMBER 3 "), "missing line 3 in: " + out);
    }

    static String dump(Textifier t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.print(pw);
        pw.flush();
        return sw.toString();
    }

    static String nameAfter(String s, String prefix) {
        int i = s.indexOf(prefix);
        check(i >= 0, "prefix not found: " + prefix + " in: " + s);
        int start = i + prefix.length();
        int end = s.indexOf('\n', start);
        check(end > start, "no line end after prefix in: " + s);
        return s.substring(start, end).trim();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
