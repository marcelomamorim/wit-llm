/*
 * @test
 * @summary WITUP-derived checks for NormalizerBase.setText(CharacterIterator): valid iterators are accepted and a null iterator is rejected
 * @modules java.base/sun.text.normalizer
 * @run main NormalizerBaseWitupTest
 */
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import sun.text.normalizer.NormalizerBase;

public class NormalizerBaseWitupTest {
    public static void main(String[] args) {
        NormalizerBase nb = new NormalizerBase("abc", null, 0);
        nb.setText(new StringCharacterIterator("xyz"));
        nb.setText(new StringCharacterIterator(""));
        boolean rejected = false;
        try {
            nb.setText((CharacterIterator) null);
        } catch (IllegalArgumentException | NullPointerException e) {
            rejected = true;
        }
        if (!rejected) {
            throw new RuntimeException("null CharacterIterator was accepted by setText");
        }
    }
}
