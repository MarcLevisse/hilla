package dev.hilla.parser.core.basic;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

import dev.hilla.parser.core.AbstractPlugin;
import dev.hilla.parser.core.PluginConfiguration;
import dev.hilla.parser.models.FieldInfoModel;
import dev.hilla.parser.test.nodes.EndpointNode;
import dev.hilla.parser.test.nodes.FieldNode;
import dev.hilla.parser.test.nodes.MethodNode;
import dev.hilla.parser.core.Node;
import dev.hilla.parser.core.NodeDependencies;
import dev.hilla.parser.core.NodePath;

final class ReplacePlugin extends AbstractPlugin<PluginConfiguration> {
    @Nonnull
    @Override
    public NodeDependencies scan(@Nonnull NodeDependencies nodeDependencies) {
        var node = nodeDependencies.getNode();
        if (node instanceof EndpointNode) {
            return nodeDependencies.processChildNodes(this::removeBarMethod)
                    .appendChildNodes(getReplacementFields());
        } else {
            return nodeDependencies;
        }
    }

    @Override
    public void enter(NodePath<?> nodePath) {

    }

    @Override
    public void exit(NodePath<?> nodePath) {

    }

    @Nonnull
    private Stream<Node<?, ?>> removeBarMethod(
            @Nonnull Stream<Node<?, ?>> nodes) {
        return nodes.filter(node -> !((node instanceof MethodNode)
                && ((MethodNode) node).getSource().getName().equals("bar")));
    }

    @Nonnull
    private Stream<Node<?, ?>> getReplacementFields() {
        try {
            return Stream
                    .of(Sample.class.getDeclaredField("fieldFoo"),
                            Sample.class.getDeclaredField("fieldBar"))
                    .map(FieldInfoModel::of).map(FieldNode::of);
        } catch (NoSuchFieldException e) {
            return Stream.empty();
        }
    }

    static class Sample {
        private final String fieldBar = "bar";
        private final String fieldFoo = "foo";
    }
}
