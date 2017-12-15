# Distributed-File-System
<br>Distributed-File-System<br>
Task 3<br><br>

Components Implemented:<br>

  1)Login Authentication: Kerberos<br>
  2)Distributed Transparent File Access (Client)<br>
  3)Directory Service<br>
  4)Locking<br>
  5)Caching<br><br>

Dependencies: <br><br>

  1) Java version:1.8 jdk<br>
  2) Eclipse IDE Version: Oxygen.1 Release (4.7.1)<br>
  3) MySql Workbench<br>
  4) RestFul Jars<br>
  5) Server: Apache Tomcat 9.0<br>



PROCESS:<br><br>

Security : 3 Key Mutual Identification process. 

The client logins a webpage through his Username and password. Username and Password is encrypted and verified by the Authentication Server with the database.The Authentication server sends back the encrypted token consisting of username and Key1, along with key 2 to the client, if Authentication of the user is successful.

Client then sends an request for the file location to the directory server(DS) with the filename,username and the encypted token sent by Authetication Server.

Directory Service Authenticates the token with the Authentication Server (AS).Only when the token is verified by the AS, it sends the location of the file to the Client encrypted with key1.

If the client sends a request for READ , the request goes to the File System Server (FSS) and for Write operation, the request first goes to the Lock Service.
Everytime a client request other components, the token sent to the FSS, Lock Server and DS is verfied with the Authetication Server.

The read request sent by the client to the FSS is encrypted with key1 and token which is encrypted with key2. 
After verfication of the token, the FSS fetches the file from the provided location and sends a response file to the client to read.

In write request, the request goes to the lock server with filename,username which is encrypted with Key1 and token encrypyted with key2(Given by the AS to the client). The lock server checks the status of the file if it is already locked or locks the file if it is written for the first time.
When the lock server sends a success status, it then requests the FSS to write the file.

When the file is done writing by some client and and a lock release request is given to the Lock Server, first it checks the status and verifies the token with the Authetication Server. Then eventually,it sends the response to the client.


DETAILS<br><br>

  Name: Sankalp Gupta<br>
  Student ID: 17302431<br>
  Email ID: guptasa@tcd.ie<br>
