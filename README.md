
# HMS Project

The Hospital Management System (HMS) project is designed to streamline and manage hospital operations. 
This system handles essential functions such as patient management, appointment scheduling, staff administration,
and other critical hospital workflows. This project leverages modern Java capabilities to enhance flexibility, 
performance, and modularity.


<!-- TOC -->
* [HMS Project](#hms-project)
  * [Prerequisites](#prerequisites)
  * [Resources](#resources)
  * [Compiling & Running](#compiling--running)
  * [Team Members](#team-members)
<!-- TOC -->
## Prerequisites

- **Java 22 or newer**: This project uses the Java **Foreign Function API**, so you need at least Java 22.
- **jfiglet Library**: Ensure that `jfiglet.jar` is placed in the `libs` directory at the root of the project.

## Resources

| Resource Name    | Link                      |
|------------------|---------------------------|
| Report           | [View Report]()           |
| Slides           | [View Slides]()           |
| UML Diagrams             | [View UML Diagrams]()     |
| Test Case Videos | [View Test Case Videos]() |


## Compiling & Running

To compile the project, run the following command:
```bash
javac -p libs -sourcepath src/main/java -d target src/main/java/com/group6/hms/app/*.java
```

To populate the application with fake data for testing, use the following command:
```bash
java -p libs;target -m hms/com.group6.hms.app.Database
```


After compiling, use the following command to run the application:
```bash
java -p libs;target -m hms/com.group6.hms.app.HMS
```

## Team Members

| Name        |
|-------------|
| Peh Zi Heng |
|   Cheo Ler Min          |
|    Rochelle Tan Tyen Xyn         |
|     Eileen Lim Jing Wen        |
|     Lim Li Ping Joey        |
