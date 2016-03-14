# HotSpice

A sample food ordering app using Spring Boot and AngularJS stack.JHipster is a Yeoman generator used to scaffold this project. [https://jhipster.github.io](https://jhipster.github.io).
### Installation dependencies ###
The following dependencies are necessary :
- Java 8
- Mongo
- bower
- maven 3
- Node 

### Building and starting server ###
Run in development:

    mvn
Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

    
Currently there are 4 users with following roles are created.Username and password are given below.

        user/user -normal user
        
        chef/admin - chef role
        
        operator/admin - operator role
        
        admin/admin -admin role



### Testing ###
Server side integration tests are located in 'src/test/java' and can be run with 

    mvn clean test
    
Unit tests are run by [Karma][] and written with [Jasmine][]. They're located in `src/test/javascript` and can be run with:

    grunt test
    
    

