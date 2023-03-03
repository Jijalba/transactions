# Mendel Java Code Challenge

### Planning

Project created to solve the Backend Challenge for Mendel.

Day 1:
- Tuesday 28:
    - Requirement's analysis.
    - Technologies definition.
    - Setting environment.
  
Day 2:
- Wednesday 1:
    - Project Initialization.
    - Layers Creation.
    - Implementations.

Day 3:
- Thursday 2:
    - Controller functionalities
    - Tests
    - Documentation
    - Added docker

## In order to run this app:

### execute:
```
docker build -t "transactions" .
```
```
docker run -p 8080:8080 transactions:latest
```

For this challenge I followed the documentation of Java's good practices, and also used
Clean Architecture and followed SOLID principles, Repository and Builder patterns and all the 
knowledge and best practices that I could to avoid Code Smells.

Some special topics:
- I use the trailing "I" for interfaces to improve the legibility although is not a Java recommendation.
- Tried to avoid Primitive Obsession and used Transactions to enter Repositories, but not for the find Methods as 
they are a parameterized search.
- I used docker in a Cloud Server I own