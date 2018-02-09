# taxeBoisson 
JEE Project using JSF Framework 2.2, this project provides full featured tax management system for the use case of beverages.
### présentation de projet en français avec des exemple de capture d‘écran son valable sure [ce lien](https://drive.google.com/open?id=1InWZk5f3bUOSi2hxfjjQwYcJf4X7QIz3)

* SQL Dump included in default package 
* password for included users in SQL Dump is : azerty

## features:
1. Managing stores and all it's related information: owner, manager, localisation, activity (e.g:hotel, cafe ...)
2. managing taxes, taxes rate, and rates for delay.
3. Roll based security
4. Data versioning: admin can see all activities performed on sensitive data by other users ( add, modifie or delete ) with the possibility of rollback
5. Input validation
6. statistics

## Server & Database
* Server : Glassfish 4.1
* Database: MYSQL 5.7

## Technologies:
* ORM : JPA with TopLink as the implementation of choice
* Reporting: JasperRepports 6.3.1
* URL rewriting: Rewrite 3.4.1
* Front-end: Primefaces 5.3, BootsFaces 1.0.2
