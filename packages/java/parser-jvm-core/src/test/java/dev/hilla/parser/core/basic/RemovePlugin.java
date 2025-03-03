package dev.hilla.parser.core.basic;

import javax.annotation.Nonnull;

import java.util.stream.Stream;

import dev.hilla.parser.core.AbstractPlugin;
import dev.hilla.parser.core.NodePath;
import dev.hilla.parser.core.PluginConfiguration;
import dev.hilla.parser.core.Node;
import dev.hilla.parser.core.NodeDependencies;
import dev.hilla.parser.test.nodes.EntityNode;

final class RemovePlugin extends AbstractPlugin<PluginConfiguration> {
    @Nonnull
    @Override
    public NodeDependencies scan(@Nonnull NodeDependencies nodeDependencies) {
        return nodeDependencies.processRelatedNodes(this::removeBazEntity);
    }

    @Override
    public void enter(NodePath<?> nodePath) {

    }

    @Override
    public void exit(NodePath<?> nodePath) {

    }

    private Stream<Node<?, ?>> removeBazEntity(Stream<Node<?, ?>> nodes) {
        return nodes.filter(
                node -> !((node instanceof EntityNode) && ((EntityNode) node)
                        .getSource().getSimpleName().equals("Baz")));
    }
}
