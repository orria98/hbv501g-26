# hbv501g-26

Recipe collection program with ingredient price calculation.

To run, run RecipesApplication.java and then go to http://localhost:8080 in your browser. Note that in this case, upper case letters are *not* recognized

You can also run the application class and then run `curl http://localhost:8080` in the terminal to run the commandlineRunner method in the application.

When the program is run, a `mydatabase.db` file is made, if it doesn't exist already. It's stored locally.


## Current possibilities

### Get all ingredients
Skilar öllum hráefnum í gagnagrunni.

**Dæmi:**\
http://localhost:8080/GetAllIngredients


### Get ingredient [?name=\<name>]
Skilar einu hráefni ef eitthvað finnst. Leitarorð þarf að vera nákvæmlega nafn hráefnisins.

**Dæmi:**\
http://localhost:8080/GetIngredient \
http://localhost:8080/GetIngredient?name=hveiti


### Init Ingredients
Setur nokkur gildi inn í Ingredients töfluna, ef hún er tóm.

**Dæmi:**\
http://localhost:8080/InitIngredients



### Get ingredients by id (?id=\<id>)
Nær í eitt hráefni eftir id

**Dæmi:**\
http://localhost:8080/GetIngredientByID?id=2



## Possible problems
* Capitalization of search parameters
* White screen in browser: no results found



## Helpful links: 
https://spring.io/guides/gs/spring-boot
https://spring.io/guides/gs/accessing-data-mysql

sqlite uppsetning: https://www.baeldung.com/spring-boot-sqlite
https://www.blackslate.io/articles/integrate-sqlite-with-spring-boot

Query: https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

