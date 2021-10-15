# camel-file-junit-example
## camel-blueprint-example
### We are covering below topics in this example:
- file
- direct
- blueprint
- junit

Please build and follow instructions:

** Build this application with `mvn clean install -DskipTests`

** We are not going to deploy this application. We will demonstrate junit with camel for unit testing.

** Run `FileProcessingRouteTest` as junit test case.

- we total have 2 routes
    1. File to direct
    2. Direct to file

### File to direct route
- we will replace the `file` consumer endpoint with direct so that we can easily send data to it.    
- we will mock the target endpoint so that after sending data to route, it should be sent to mock endpoint and not to actual endpoint


### Direct to file route
- we will send data to direct and will mock the target file component    
- after mocking, we will skip the further execution
