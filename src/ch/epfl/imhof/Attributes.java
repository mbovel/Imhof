package ch.epfl.imhof;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public final class Attributes {
    private final Map<String, String> attributes;

    public Attributes(Map<String, String> attributes) {
        this.attributes = Collections.unmodifiableMap(new HashMap<>(attributes));
    }
    
    public Boolean isEmpty() {
        return this.attributes.isEmpty();
    }
    
    public Boolean contains(String s) {
        return this.attributes.containsKey(s);
    }
    
    public String get(String s) {
        return this.attributes.get(s);
    }
    
    public String get(String key, String defaultValue) {
        return this.attributes.getOrDefault(key, defaultValue);
    }
    
    public int get(String key, int defaultValue) {
        if(!this.attributes.containsKey(key))
            return defaultValue;
        
        int i;
        
        try {
            i = Integer.parseInt(this.attributes.get(key));
        }
        catch(NumberFormatException e) {
            return defaultValue;
        }
        
        return i;
    }
    
    public Attributes keepOnlyKeys(Set<String> keysToKeep) {
        Builder builder = new Builder();
        
        for(String key : keysToKeep) {
            builder.put(key, this.attributes.get(key));
        }
        
        return builder.build();
    }
    
    public static class Builder {
        private Map<String, String> attributes;
        
        public void put(String key, String value) {
            this.attributes.put(key, value);
        }
        
        public Attributes build() {
            return new Attributes(this.attributes);
        }
    }
}
