# Simple-Rest-Assured-Demo

Demo to make and test Rest Assured Test cases and to build and run it with Docker.
Also a demo to visualize test cases with [Allure Report](https://allurereport.org/docs/)

---

## Getting the test report represented with a UI with Docker

This project uses Docker Compose to automate API testing with Maven and publish Allure reports using the Allure Docker Service.  
The setup contains three services:

- **tests** — runs Maven tests inside a container and uploads Allure results  
- **allure-service** — receives results and generates Allure reports  
- **allure-ui** — displays the generated reports in a browser  

Shared volumes allow test results and reports to move between containers.

---

## Build and run the Docker container


```bash
docker-compose up --build
```

Your application will now be available at:

[http://localhost:5252](http://localhost:5252)

---

## Getting the test report represented with a UI when running the application locally.

### Run unit tests
```bash
mvn clean test .
```
- This mvn test command also runs the test case and shows also the printed statements in the test case in 
the terminal

### Make the Allure Report UI
```bash
mvn allure:report
```
### Serve the Allure reports
```bash
mvn allure:serve
```
- Allure Report will display on a random port but in the terminal the URL where it should show up




