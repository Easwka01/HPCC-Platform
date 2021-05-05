## Selenium UI unit tests for Medical Discovery portal.

#### Usage

1. Download and run a driver instance for the browser you wish to perform the tests in.  A link to a Google Chrome driver for windows is included below.
2. Run the tests via Maven via using the command line `mvn test [-P <profile>]`

Where *profile* is one of **local**, **qa**, or **dev**, and will connect to the nominal instance to perform the tests.  If no -P option is specified, **dev** is the default.    

Please note currently (as of 3/29/2016), the MAE user used for these tests only exists in dev.  Therefore only **dev**, and **local** (if pointed to the dev database) are likely to work.


3. Individual JUnit results will be present in `target/surefire-reports` to look at.  To aggregate these results into a report, execute `mvn post-integration-test`.  This will generate an HTML report, and email it to pre-defined recipients.
4. Please edit the appropriate profile pom.xml to change the recipients of the email. 

##### Eclipse and JUnit

These tests can also be run from within eclipse, using the Junit runner.  To do so, you must force the tests to load properties outside of the maven profile mechanism.  This can be done by specifying the `prop.override` environment parameter and pointing to the desired property file to load.

E.g.:

`-Dprop.override=C://path/to/dev.properties"`

`-Dprop.override=${file_prompt:"Choose a properties file to load"}`

`-Dprop.override=${project_loc}/src/test/resources/local.properties`
  
JVM parameters are added in the Run Configuration section.

5. Use Explicit wait  wherever there is a chance for an action to cause a page refresh . 

E.g.: Selection of entrypoint,dropdowns causes an Page Refresh
replace "driver.findElement(By.id("btnSubmit")).click();"     by 
"wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSubmit"))).click();" which will wait until the element is visible and then performs click operation .

#### Other links 

##### A bridge from selenium tests to the chrome web browser
- https://sites.google.com/a/chromium.org/chromedriver/


##### There's also 
- https://github.com/SeleniumHQ/selenium/wiki/FirefoxDriver
- https://github.com/SeleniumHQ/selenium/wiki/InternetExplorerDriver

(Untested)
Adding for Test.
Adding for Test1.




