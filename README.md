# CountriesApi
Generates a valid auth token. All the API endpoints are protected by auth. Fetches detailed information about a specific country by its name. Retrieves a list of all countries names based on filters (population/area/language) and sorting(asc/desc), having support for pagination. Returns the data as JSON responses. Implements global except handling 


                       <---------------------Steps How To Run--------------------------->
               requirements -> Ide(eclipse preffered), postman for checking api, lombok jar file installed in ide
1. Open the master branch of repository and download the zip file.
2. Extract the zip file in a folder and now open an ide(eclipse).
3. Now find import option from top corner and select existing maven project then browse the foder and finish.
4. It will take some time to build if does not build it manually from above.
5. After getting built you will get maven dependencies in resources.
6. Now go src/main/java and open com.restcountries.assignment.
7. And you will RestCountriesApplication.java file there, open it by double click and run this file from above.
8. Or simply you can run this project by right clicking on project and click on run then run as java application then select com.restcountries.assignment package.
9. In console you would find code is compiling.
10. One import thing project is using lombok for annotation so you should have lombok jar file installed in your ide otherwise it wouldn't work. If you don't have download and install it, you can take help of google.
11. According to project reqirement it has two endpoints
