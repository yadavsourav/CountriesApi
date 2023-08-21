# CountriesApi
Generates a valid auth token. All the API endpoints are protected by auth. Fetches detailed information about a specific country by its name. Retrieves a list of all countries names based on filters (population/area/language) and sorting(asc/desc), having support for pagination. Returns the data as JSON responses. Implements global except handling 


                       <---------------------Steps How To Run--------------------------->
               requirements -> Ide(eclipse preffered), postman for checking api, lombok jar file installed in ide
1. Open the master branch of repository and download the zip file.
2. Extract the zip file in a folder and now open an ide(eclipse).
3. Now find import option from top corner and in import select existing maven project then browse the folder and finish.
4. It will take some time to build if does not, build it manually from above.
5. After getting built you will get maven dependencies in resources.
6. Now go src/main/java and open com.restcountries.assignment.
7. And you will find RestCountriesApplication.java file there, open it by double click and run this file from above.
8. Or simply you can run this project by right clicking on project and click on run option then run as java application option.From there select com.restcountries.assignment package.
9. In console you would find code is compiling.
10. One important thing project is using lombok for annotation so you should have lombok jar file installed in your ide otherwise it wouldn't work. If you don't have download and install it, you can take help of google.
11. As this project is secured by authentication token, so first we have to generate an authentication token by using 
    credentials which are hardcoded in AppConfig.java in AppConfig package.
12. Now open postman and make a post request on http://localhost:8080/auth/login. Click on body option and select raw.
13. In body put raw data-
   {
    "email":"user1",
    "password":"123"
          }
         or
    {
    "email":"user2",
    "password":"234"
          }
    any one of the above credentials
14. Open the headers option beside of body option that you chose earlier and put the 10th key Content-Type and its value 
    application/json.
15. Now you are ready and send the request. You will get userName and jwtToken in response. Now copy the userName value
    without double quotes. And go the headers option again, create 11th key Authorization and put the userName value as its 
    value without any manipulation.
16. Change request mode to Get, now we are ready to use or test our endpoints.
17. According to project reqirement it has two endpoints-
    (1st) -> http://localhost:8080/api/name/{countryname} ->  It will give detailed information of a specific country to user
             example -> http://localhost:8080/api
    /name/india
    (2nd) -> http://localhost:8080/api/countries
             In this api you have multiple filters that you can apply, so open the params option in postman and add the key 
             value pairs which you want to apply.
             valid keys-
             1. page                //(which page you wanna open, by default it is 0)
             2. size                //(how many entries you want in each page, by default it is 10)
             3. population>=        //(It will filter all countries having population greater than provided value, you can 
                                        also leave it null)
             4. area>=              //(same as above)
             5. language            // provide a language if you want to filter on basis of language
             6. sortBy              // you can sort your data either on basis of area or popualation, by default it is 
                                       sortedBy area
             7. sortOrder            // asc or desc
                                  example ->  http://localhost:8080/api/countries?                    
                                  sortBy=population&sortOrder=desc&population>==10000000&language=english&page=0&size=7
19. If you would try to access endpoints without authorization your access would be denied. On console
