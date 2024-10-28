
# HMS Project

## Prerequisites

- **Java 22 or newer**: This project uses the Java Foreign Function API, so you need at least Java 22.
- **jfiglet Library**: Ensure that `jfiglet.jar` is placed in the `libs` directory at the root of the project.

## Compiling & Running

To compile the project, run the following command:
```bash
javac -p libs -sourcepath src/main/java -d target src/main/java/com/group6/hms/app/*.java
```
After compiling, use the following command to run the application:
```bash
java -p libs;target -m hms/com.group6.hms.app.HMS
```
