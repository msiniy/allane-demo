# allane-demo
Spring Boot + Angular demo app

## Prerequisites
 - JDK 11
 - docker and docker-compose that supports docker-compose file of version 3.1

## How to build
 - clone the repo
 - run `./gradlew bootJar`
 - launch dockerized MariaDB instance: `docker-compose up`
 - launch the result JAR: `java -jar ./build/libs/demo-0.0.1-SNAPSHOT.jar`

The server will start on port `9085`, so go to http://localhost:9085

## Design decisions:
 - While the contract form has some icons assuming the removal of related `Customer` and `Vehicle`, it isn't possible. 
 It looks a bit strange to have a contract without customer or vehicle.
 - It isn't possible to change Brand or Model of existing Vehicle. The reason is the same, this possibility doesn't reflect well in my head.

In a real world, such scenarios shall be discussed with PM's or BA's. 

## Technical decisions:
 - Persistence layer is based on `JPA` + `Spring Data JPA`, it's a quite popular solution, with own advantages and disadvantages.
 - `Lombok` is used to generate boilerplate code.
 - As there are only a few entities, mapping from `Entities` to `DTOs` is done manually, instead of library-based solution (Apache Dozer or MapStruct).
 - `Bootstrap` is used to build page layouts and styles.

