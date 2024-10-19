# hbv501g-26

Recipe collection program with ingredient price calculation.

Enjoy

# About the program

<!-- To run, run RecipesApplication.java and then go to http://localhost:8080 in your browser.  -->

When the program is run, a `mydatabase.db` file is made, if it doesn't exist already. It's stored locally.


## Endpoint summary
### Assignment 2
**Get All Ingredients:** http://localhost:8080/ingredient/all \
**Get Ingredient by ID:**  http://localhost:8080/ingredient/id/1

### Assignment 3

#### Recipe: 
**Get all:** http://localhost:8080/recipe/all



#### User
**Get pantry:** http://localhost:8080/user/id/1/pantry\
**Delete pantry item:** http://localhost:8080/user/pantry/delete?uid=1&iid=1\
**Add ingredient to pantry:** http://localhost:8080/user/pantry/add?uid=1&iid=1&unit=G&qty=100


#### Ingredient


### Others: 
**Get Ingredient by title:** http://localhost:8080/ingredient/title/hveiti \
**Initalize some ingredients:** http://localhost:8080/ingredient/init

## Current possibilities

### ingredient/all
Skilar öllum hráefnum í gagnagrunni.

**Dæmi:**\
http://localhost:8080/ingredient/all


### ingredient/title/\<title>
Skilar einu hráefni ef eitthvað finnst. Leitarorð þarf að vera nákvæmlega nafn hráefnisins.

**Dæmi:**\
http://localhost:8080/ingredient/title/hveiti


### ingredient/init
Setur nokkur gildi inn í Ingredients töfluna, ef hún er tóm.

**Dæmi:**\
http://localhost:8080/ingredient/init



### ingredient/id/\<id>
Nær í eitt hráefni eftir id

**Dæmi:**\
http://localhost:8080/ingredient/id/1





## Possible problems
* Capitalization of search parameters (should be fixed)
* White screen in browser: no results found
* Getting multiple results but expecting one
* New information not showing in json: make sure there's a getter for the variable
* Recursively getting users for ingredients and ingredients for users (use @JsonIgnore or @JsonIncludeProperties)



## Helpful links: 
https://spring.io/guides/gs/spring-boot

https://spring.io/guides/gs/accessing-data-mysql

https://spring.io/guides/gs/rest-service

sqlite uppsetning: https://www.baeldung.com/spring-boot-sqlite\
https://www.blackslate.io/articles/integrate-sqlite-with-spring-boot

Query: https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html


Error page: https://www.baeldung.com/spring-boot-custom-error-page

Element collection, safn af embeddable hlutum: https://en.wikibooks.org/wiki/Java_Persistence/ElementCollection



