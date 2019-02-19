package com.example;

import java.util.function.BiFunction;

public class DirectedGraph<V> extends UndirectedGraph<V> {
    public DirectedGraph() {
        super(DirectedEdge::new);
    }

    public DirectedGraph(BiFunction<V, V, Edge<V>> edgeSupplier) {
        super(edgeSupplier);
    }

    @Override
    protected final void addEdgeInternal(V start, V end, Edge<V> edge) {
        vertices.get(start).add(edge);
    }
}
