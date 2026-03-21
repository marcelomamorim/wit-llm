package br.unb.cic.witupllmautogen.analysis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.unb.cic.witup.analysis.SymKind;
import br.unb.cic.witup.graph.node.WITUpNode;
import br.unb.cic.witupllmautogen.analysis.fixture.NoThrowFixture;
import br.unb.cic.witupllmautogen.analysis.fixture.ThrowingFixture;
import br.unb.cic.witupllmautogen.analysis.model.AnalysisReport;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import sootup.codepropertygraph.propertygraph.nodes.PropertyGraphNode;

class WitupAnalysisExtractorTest {

  private static final String TEST_CLASSES_PATH =
      Path.of("target", "test-classes").toAbsolutePath().toString();

  @Test
  void shouldExtractThrowPathsFromFixtureClass() {
    WitupAnalysisExtractor extractor = new WitupAnalysisExtractor();

    AnalysisReport report = extractor.analyse(TEST_CLASSES_PATH, ThrowingFixture.class.getName());

    assertEquals(TEST_CLASSES_PATH, report.classPath());
    assertEquals(ThrowingFixture.class.getName(), report.className());
    assertNotNull(report.generatedAt());
    assertTrue(report.generatedAt().isBefore(Instant.now().plusSeconds(1)));
    assertTrue(report.analysedMethods() > 0);
    assertTrue(report.analysedThrowPaths() > 0);
    assertFalse(report.throwPaths().isEmpty());
    assertTrue(
        report.throwPaths().stream()
            .allMatch(path -> path.throwExpression() != null && !path.throwExpression().isBlank()));
  }

  @Test
  void shouldReturnEmptyThrowPathsWhenClassHasNoThrowStatements() {
    WitupAnalysisExtractor extractor = new WitupAnalysisExtractor();

    AnalysisReport report = extractor.analyse(TEST_CLASSES_PATH, NoThrowFixture.class.getName());

    assertEquals(0, report.analysedThrowPaths());
    assertTrue(report.throwPaths().isEmpty());
  }

  @Test
  void shouldMapKindNameIncludingNullFallback() throws Exception {
    Method method = WitupAnalysisExtractor.class.getDeclaredMethod("toKindName", SymKind.class);
    method.setAccessible(true);

    String nullKind = (String) method.invoke(null, new Object[] {null});
    String regularKind = (String) method.invoke(null, SymKind.OTHER);

    assertEquals("UNKNOWN", nullKind);
    assertEquals("OTHER", regularKind);
  }

  @Test
  void shouldFallbackToNodeToStringWhenNodeIsNotThrowStatementNode() throws Exception {
    Method method =
        WitupAnalysisExtractor.class.getDeclaredMethod("extractThrowExpression", WITUpNode.class);
    method.setAccessible(true);

    String throwExpression =
        (String) method.invoke(null, new DummyWitupNode(new DummyPropertyGraphNode("fallback-node")));

    assertEquals("fallback-node", throwExpression);
  }

  private static final class DummyWitupNode extends WITUpNode {
    private DummyWitupNode(final PropertyGraphNode node) {
      super(node);
    }
  }

  private static final class DummyPropertyGraphNode extends PropertyGraphNode {
    private final String label;

    private DummyPropertyGraphNode(final String label) {
      this.label = label;
    }

    @Override
    public boolean equals(final Object other) {
      if (this == other) {
        return true;
      }
      if (!(other instanceof DummyPropertyGraphNode that)) {
        return false;
      }
      return Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
      return Objects.hash(label);
    }

    @Override
    public String toString() {
      return label;
    }
  }
}
