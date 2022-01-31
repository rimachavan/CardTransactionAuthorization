# CardTransactionAuthorization

Card Transaction Authorization Application helps to authorize raw card transaction messages.

This is a multi-module maven project with below modules
* `cardtrans-authorization-api` : contains high level interfaces needed for transaction authorization
* `cardtrans-authorization-model` : contains data model classes
* `cardtrans-authorization-service` : services helps to process, validate and authorize transaction message

# Build 
* `mvn clean install` : Maven command to build this application.

# How to Run
* Build the application before running.

Assuming the user is at CardTransactionAutorization folder, execute below commands in order
1. `cd cardtrans-authorization-service` : Navigate to folder "cardtrans-authorization-service"
2. `mvn compile exec:java -Dexec.args="<FILE_NAME>" ` : Replace `<FILENAME>` with your path/filename. 

For example, if my file is present at location `/Users/rimachavan/test-file.txt` , then command looks like 
`mvn compile exec:java -Dexec.args="/Users/rimachavan/test-file.txt" `

# Alternative way to execute
You may reuse the test file present at `cardtrans-authorization-service/src/main/resources/CardTransTestFile.txt`. 

To reuse this file, execute below commands in order
1. Uncomment [these](https://github.com/rimachavan/CardTransactionAuthorization/blob/master/cardtrans-authorization-service/pom.xml#L54-L56) lines
2. `cd cardtrans-authorization-service` : Navigate to folder `cardtrans-authorization-service`.
3. `mvn compile exec:java` : Execute command.

# Assumptions
1. In order to make the code more extensible, a multi-maven project is created. if not needed, it could have been part a single module, with maven packaging as jar.
2. Assuming file size is small. Reading whole file in one shot and processing it. We could have used streaming or chunking approach for bigger data files.
3. 