# hbv501g-26

Recipe collection program with ingredient price calculation.

To run, run RecipesApplication.java and then go to http://localhost:8080 in your browser. 

You can also run the application class and then run `curl http://localhost:8080` in the terminal to run the commandlineRunner method in the application.

When the program is run, a `mydatabase.db` file is made, if it doesn't exist already. It's stored locally.


## Endpoint summary
### Assignment 2
**Get All Ingredients:** http://localhost:8080/Ingredient/all \
**Get Ingredient by ID:**  http://localhost:8080/Ingredient/id/1


### Others: 
**Get Ingredient by Name:** http://localhost:8080/Ingredient/name/hveiti \
**Initalize some ingredients:** http://localhost:8080/Ingredient/init

## Current possibilities

### Ingredient/all
Skilar öllum hráefnum í gagnagrunni.

**Dæmi:**\
http://localhost:8080/Ingredient/all


### Ingredient/name/\<name>
Skilar einu hráefni ef eitthvað finnst. Leitarorð þarf að vera nákvæmlega nafn hráefnisins.

**Dæmi:**\
http://localhost:8080/Ingredient/name/hveiti


### Ingredient/init
Setur nokkur gildi inn í Ingredients töfluna, ef hún er tóm.

**Dæmi:**\
http://localhost:8080/Ingredient/init



### Ingredient/id/\<id>
Nær í eitt hráefni eftir id

**Dæmi:**\
http://localhost:8080/Ingredient/id/1





## Possible problems
* Capitalization of search parameters (should be fixed)
* White screen in browser: no results found
* Getting multiple results but expecting one
* New information not showing in json: make sure there's a getter for the variable



## Helpful links: 
https://spring.io/guides/gs/spring-boot

https://spring.io/guides/gs/accessing-data-mysql

https://spring.io/guides/gs/rest-service

sqlite uppsetning: https://www.baeldung.com/spring-boot-sqlite\
https://www.blackslate.io/articles/integrate-sqlite-with-spring-boot

Query: https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html


Error page: https://www.baeldung.com/spring-boot-custom-error-page
