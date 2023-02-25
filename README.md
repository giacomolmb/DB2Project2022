# Database 2 Project
 Database 2 Project - Politecnico di Milano, A.A. 2021/22

### Authors

- [Valeria Detomas](https://github.com/valeriadetomas)
- [Giacomo Lombardo](https://github.com/giacomolmb)

## Description

Aim of this project is to develop a platform to manage package sales for an imaginary ICT company, TELCO.  
The architecture of the platform consists of a MySQL database and a backend server developed using Java Servlets. The database-server communication is managed through JPA ORM.  
In order to interact with the server, a simple frontend built using HTML, Thymeleaf, JavaScript and Bootstrap is also implemented.

## Run the project

To run this project, the following software is required:

- MySQL Server
- TomEE 9.x Server

In order to start the server and use the website: 

- Import in MySQL the database in the Dump folder, with the name `telco_db`
- Build the artifact `TelcoWEB_war_exploded`
- Configure TomEE server as the runtime environment
- Start the TomEE server
- With your browser, go to `http://localhost:8080/TelcoWEB_war_exploded/`
