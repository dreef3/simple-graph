package com.example;

public class UndirectedEdge<V> extends Edge<V> {

    public UndirectedEdge(V start, V end) {
        super(start, end);
    }

    @Override
    public V end(V start) {
        if (this.start.equals(start)) {
            return this.end;
        }

        return this.start;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
