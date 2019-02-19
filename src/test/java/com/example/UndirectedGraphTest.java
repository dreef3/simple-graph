package com.example;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UndirectedGraphTest {
    @Test
    void canAddVertex() {
        UndirectedGraph<String> graph = new UndirectedGraph<>();
        graph.addVertex("1");
    }

    @Test
    void canAddEdgeForExistingVertices() {
        UndirectedGraph<String> graph = makeGraph();

        graph.addEdge("1", "2");
    }

    @Test
    void cantAddEdgeForUnknownVertex() {
        UndirectedGraph<String> graph = makeGraph();

        assertThatThrownBy(() -> graph.addEdge("1", "-1")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void emptyPathForSameStartAndEnd() {
        UndirectedGraph<String> graph = makeGraph();

        assertThat(graph.getPath("1", "1")).isEmpty();
    }

    @Test
    void returnsPathBetweenVertices() {
        UndirectedGraph<String> graph = makeGraph();
        graph.addEdge("1", "2");
        graph.addEdge("2", "3");

        assertThat(graph.getPath("1", "3")).containsExactly(new UndirectedEdge<>("1", "2"), new UndirectedEdge<>("2", "3"));
    }

    @Test
    void returnsReversePathBetweenVertices() {
        UndirectedGraph<String> graph = makeGraph();
        graph.addEdge("1", "2");
        graph.addEdge("2", "3");

        assertThat(graph.getPath("3", "1")).containsExactly(new UndirectedEdge<>("2", "3"), new UndirectedEdge<>("1", "2"));
    }

    @Test
    void returnsNullWhenNoPathExists() {
        UndirectedGraph<String> graph = makeGraph();
        graph.addEdge("1", "2");
        graph.addEdge("3", "4");

        assertThat(graph.getPath("3", "1")).isNull();
    }

    @Test
    void canTraverseVertices() {
        UndirectedGraph<String> graph = makeGraph();

        List<String> vertices = StreamSupport.stream(graph.spliterator(), false)
                .collect(Collectors.toList());

        assertThat(vertices).containsExactly("1", "2", "3", "4", "5");
    }

    private UndirectedGraph<String> makeGraph() {
        UndirectedGraph<String> graph = new UndirectedGraph<>();

        graph.addVertex("1");
        graph.addVertex("2");
        graph.addVertex("3");
        graph.addVertex("4");
        graph.addVertex("5");

        return graph;
    }
}
