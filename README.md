# beyond-notes
Beyond Notes is a simple web API to perform CRUD operations for notes, leveraging RESTful technologies.  
  
  
## Project Limitations
Certain factors are beyond the limit of this exercise: distribution management  
  
Also, Maven may have issues pulling dependencies behind firewalls. If one exists, consider configuring a proxy to your local Maven. Refer to the documents [here](https://maven.apache.org/guides/mini/guide-proxies.html).  
  
### Distribution Management
This project is too small to warrant a distribution management system. However, projects would have a repository(s) for Maven Snapshots and Releases to store the artifacts for potential software to release.  
  
  
## Technology Stack
*Majority at least; peek at the **pom.xml** or unzip the **.jar** to get the full stack*
> H2 Database  
> Jacoco  
> Junit4  
> Log4j2  
> Lombok  
> Maven  
> Spring Boot  
> Spring Boot Actuator  
> Spring Data JPA  
> Spring Framework  
> Tomcat  
  
  
## Required pieces to run
*Works using the latest versions*  
Git [[setup reference](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)]  
Java SE 1.8 [[download](http://www.oracle.com/technetwork/java/javase/downloads/index.html)]  
Maven [[setup reference](https://maven.apache.org/install.html)]  
  
**optional** IDE or text editor to read project  
  
### Note
The Lombok library may affect how developers view the source code because it generates boilerplate code at compile time. Please ensure to install Lombok on your IDE. For more information, please refer to their [site](https://projectlombok.org/).  
  
The project uses the port 10101. If there is a port conflict, redefine it in the **application.yml** file.  
  
  
## Run project via command line
In the directory of your choice, pull the project down and run the application locally.
> ```shell
> git clone https://github.com/gitKirkChan/beyond-notes.git
> cd beyond-notes/notes-api
> mvn clean install
> java -jar target/notes-api-1.0.0-SNAPSHOT.jar
> ```
  
### Run *prod* configurations
There are two application configurations available: *dev* and *prod*. The default Spring profile is *dev*. To launch the application with *prod*, modify the **java** command like so:
> ```shell
> java -jar -Dspring.profiles.active=prod target/notes-api-1.0.0-SNAPSHOT.jar
> ```
  
  
## Run project via IDE
Within `src/main/java`, run **RunApp.java** under `com.kchan.project.beyond.notes`.
  

## Basic API Summary
The application has a context path of */api*.
  
  
**Purpose** | **Path** | **Method** | **Parameter** | **Required** | **Return**
--- | --- | --- | --- | --- | --- 
Create new note | `/notes/` | **POST** | **Note** | yes | **Note** created
Find note | `/notes/{id}` | **GET** | id | yes | **Note** (throwable **NotFoundException**)
Find notes; query search | `/notes` | **GET** | query | no | List<**Note**>
Update note | `/notes` | **PUT** | **Note** | no | Status of update (can create new note)
Delete note | `/notes/{id}` | **DELETE** | id | yes | Status of delete (throwable **NotFoundException**)
  
  
## Jacoco Report
The project configurations include a Jacoco report when a Maven build finishes. The results are in the `target/jacoco-ut` directory. There are several formats available. Typically, I refer to the **index.html**.  
  
Some classes, such as the **RunApp.java**, are excluded from Jacoco because they skew reasonable coverage of the application code. Repository classes, implementing the Spring Data Repository, will not appear on the report but are tested with standard JUnit's. These exclusions are defined in the **pom.xml**.
  
  
## Database
This application leverages Spring Data JPA and H2 database for portablility and demonstration purposes. Maven handles and pulls down the dependencies. Spring Data Repositories remove boilerplate code with the data access layer, especially for CRUD operations, and generates the implementation based on the JPA objects, the repository interface used and repository keywords used in custom queries. It also supports H2 DBMS by default, configured as an embedded database. Thus, there are no additional installations needed after the Maven install.  
  
Also, there is no persistence of data after the Spring application stops running. 
