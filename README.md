[Project home page](http://cs108.epfl.ch)

## Useful links

### Java

- [How java import works](http://stackoverflow.com/a/12620773)
- [Linking to an external URL in Javadoc?](http://stackoverflow.com/a/10683345)
- [Is ArrayList.size() method cached?](http://stackoverflow.com/questions/2856835/is-arraylist-size-method-cached)
- [javadoc: writing links to methods](http://stackoverflow.com/questions/5915992/javadoc-writing-links-to-methods)

### Eclipse

- [How can I get Eclipse to show .* files?](http://stackoverflow.com/a/98634)
- [How can I edit the markdown font colors in Eclipse Luna?](http://stackoverflow.com/a/28504207>)
- [How to turn off the Eclipse code formatter for certain sections of Java code?](http://stackoverflow.com/a/3353765)
- [Default @author for Javadoc](http://www.javahotchocolate.com/notes/eclipse.html#author)

## Coding styles

### Java doc

Exemple from “[How to Write Doc Comments for the Javadoc Tool](http://www.oracle.com/technetwork/articles/java/index-137868.html)”:

```java
/**
 * Returns an Image object that can then be painted on the screen. 
 * The url argument must specify an absolute {@link URL}. The name
 * argument is a specifier that is relative to the url argument. 
 * <p>
 * This method always returns immediately, whether or not the 
 * image exists. When this applet attempts to draw the image on
 * the screen, the data will be loaded. The graphics primitives 
 * that draw the image will incrementally paint on the screen. 
 *
 * @param  url  an absolute URL giving the base location of the image
 * @param  name the location of the image, relative to the url argument
 * @return      the image at the specified URL
 * @see         Image
 */
 public Image getImage(URL url, String name) {
        try {
            return getImage(new URL(url, name));
        } catch (MalformedURLException e) {
            return null;
        }
 }
```

- A Javadoc comment block must be present for all public classes and methods.
- A description must be present.
- Description starts with a capital letter and ends with a dot.
- First verb in the description takes an “s”.
- `@param` and `@return` declarations are written in small letters without a dot at the end.
- `@author` declaration is written in the following format: `FirstName LastName (SciperNumber)`.
- There is no extra star at the beginning or at the end of the comment block.
- Methods or class names are written with the correct case and linked with the `{@link ...}` declaration.