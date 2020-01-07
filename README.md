# Demo project  

This is a demo project to build cross platform native apps with GraalVM. 

Helidon is leveraged to host a simple Http server that will be used by the simple JSON editor.

## Build

With JAVA_HOME pointing to GraalVM 19.3.x for JDK 11, run the following command:

```bash
mvn clean build
```

## Native image with GraalVM
Run the maven command to build a native image

```
mvn package -Pnative-image
```

Start the native application:

```
./target/myeditor
```

## License
Apache License 2.0
