package dev.hilla.parser.plugins.nonnull.superclassmethods;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

import dev.hilla.parser.core.ParserConfig;
import dev.hilla.parser.plugins.backbone.BackbonePlugin;
import dev.hilla.parser.plugins.nonnull.AnnotationMatcher;
import dev.hilla.parser.plugins.nonnull.NonnullPlugin;
import dev.hilla.parser.plugins.nonnull.NonnullPluginConfig;
import dev.hilla.parser.plugins.nonnull.test.helpers.TestHelper;
import org.junit.jupiter.api.Test;

public class SuperClassMethodsTest {
    private final TestHelper helper = new TestHelper(getClass());

    @Test
    public void should_CorrectlyHandleSuperClassMethods()
            throws IOException, URISyntaxException {
        var plugin = new NonnullPlugin();
        plugin.setConfiguration(new NonnullPluginConfig(Set
                .of(new AnnotationMatcher(Nonnull.class.getName(), false, 0)),
                null));

        var config = new ParserConfig.Builder()
                .classPath(Set.of(helper.getTargetDir().toString()))
                .endpointAnnotation(Endpoint.class.getName())
                .endpointExposedAnnotation(EndpointExposed.class.getName())
                .addPlugin(new BackbonePlugin()).addPlugin(plugin).finish();

        helper.executeParserWithConfig(config);
    }
}
