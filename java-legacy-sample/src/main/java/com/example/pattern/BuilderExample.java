package com.example.pattern;

public class BuilderExample {

    private final int id;

    private final String name;
    private final String value;

    private BuilderExample(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.value = builder.value;
    }

    public static class Builder {
        private final int id;

        private String name;
        private String value;

        public Builder(int id) {
            this.id = id;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public BuilderExample build() {
            return new BuilderExample(this);
        }

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "BuilderExample{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
