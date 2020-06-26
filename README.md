# json-resourcebundle
## TL;DR
Registering a custom [`ResourceBundle.Control`](https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.Control.html) extension forces JSON parser jars to be present in [extension](https://docs.oracle.com/javase/6/docs/technotes/guides/extensions/spec.html) directory. This repo provides basic skeleton/raw code to overcome the limitation and support json bundle, natively using SPI and extension mechanism. If you are ok with putting 3rd party parser jars in `java.ext.dir` too (or putting your extension in default ext directory), then you don't need to look further in this git.
## Load your json bundle.
java by default provides way to load a ResourceBundle using [`ResourceBundle.getBundle(..)`](https://docs.oracle.com/javase/7/docs/api/java/util/ResourceBundle.html#getBundle(java.lang.String)) api. The default implementation supports a java and properties file ResourceBundle.

A typical usage of API, is explained in java doc: 
```
ResourceBundle myResources =
      ResourceBundle.getBundle("MyResources", currentLocale);
            
```
or

```
ResourceBundle myResources =
      ResourceBundle.getBundle("com.aminur.resourcebundle.muffinapp.bundle");           
```
ResourceBundle myResources =
      ResourceBundle.getBundle("MyResources", currentLocale);

The API supports `java|properties` bundle by default. And java also provides way to control other bundles. e.g `json` or [`xliff`](https://docs.oasis-open.org/xliff/v1.2/xliff-profile-java/xliff-profile-java-v1.2-cd02.html).

A very well documented tutorial to register own [`ResourceBundle.Control`](https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.Control.html) and to take over discovery and creation of bundle is exist [here](https://docs.oracle.com/javase/tutorial/i18n/serviceproviders/resourcebundlecontrolprovider.html).

However the problem I faced with this approach is that to register the `ResourceBundle.Control` the `SPI` implementation jar has to be in `java.ext.dir` dir. To quote the [tutorial](https://docs.oracle.com/javase/tutorial/i18n/serviceproviders/resourcebundlecontrolprovider.html#run-rbcptest):
>When you install a Java extension, you typically put the JAR file of the extension in the lib/ext directory of your JRE. However, this command specifies the directory that contains Java extensions with the system property `java.ext.dirs`.

So far so good. Steps are :
1. You register a service implementing [`java.util.spi.ResourceBundleControlProvider`](https://docs.oracle.com/javase/8/docs/api/java/util/spi/ResourceBundleControlProvider.html), 
2. return a custom implementation of  [`ResourceBundle.Control`](https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.Control.html)
3. override ```
          public ResourceBundle newBundle(String baseName, Locale locale,
                                    String format,
                                    ClassLoader loader,
                                    boolean reload)
          ```
4. Place the jar in ext directory, or change the ext directory and your bundle is loaded.

### :no_entry_sign: *Problem*

Problem is in step 3. To create a new JsonBundle, we will have to parse Json file. Most of the JSON Parser (GSON/Jackson/..) are third party libary and this approach will force those 3rd party libraries to be present in ext folder. Reason for this is the ext/dir is loaded using `extension classloader` unlike your system classloader. And hence the depdendent API for parsing JSON has to be there in extension directory. I tried to solve it using another SPI that provides parsed bundle.
