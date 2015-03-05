package ch.epfl.imhof;

public final class Attributed<T> {
    private T value;
    private Attributes attributes;
    
    public Attributed(T value, Attributes attributes) {
        this.value = value;
        this.attributes = attributes;
    }
    
    public T value() {
        return this.value;
    }
    
    boolean hasAttribute(String attributeName) {
        return this.attributes.contains(attributeName);
    }
    
    String attributeValue(String attributeName) {
        return this.attributeValue(attributeName);
    }
    
    String attributeValue(String attributeName, String defaultValue) {
        return this.attributeValue(attributeName, defaultValue);
    }
    
    int attributeValue(String attributeName, int defaultValue) {
        return this.attributeValue(attributeName, defaultValue);
    }
}
