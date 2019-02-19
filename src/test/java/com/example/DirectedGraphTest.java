package com.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DirectedGraphTest {
    @Test
    void doesNotReturnReversePath() {
        DirectedGraph<String> graph = new DirectedGraph<>();
        graph.addVertex("1");
        graph.addVertex("2");
        graph.addVertex("3");
        graph.addEdge("1", "2");
        graph.addEdge("2", "3");

        assertThat(graph.getPath("3", "1")).isNull();
    }
}
