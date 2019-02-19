package com.example;

import java.util.Objects;

public abstract class Edge<V> {
    final V start;
    final V end;

    public Edge(V start, V end) {
        this.start = start;
        this.end = end;
    }

    public V getStart() {
        return start;
    }

    public V getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge<?> edge = (Edge<?>) o;
        return Objects.equals(start, edge.start) &&
                Objects.equals(end, edge.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }

    public abstract V end(V start);
}
