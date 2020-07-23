# Using Log4j2 in an Azure Function
This sample code shows how to integrate logging with the Java logging framework [Log4j2](https://logging.apache.org/log4j/2.0) in an Azure Function using the Java SDK and how to integrate the trace log in Azure [Application Insights](https://docs.microsoft.com/en-us/azure/azure-monitor/app/app-insights-overview)
## Run the sample
* Create an Application Insights resource in your subscription
* Copy the instrumentation key in the `src/main/resources/log4j2.xml` file
* Optional: Change the values for resourceGroup, appServicePlanName and region in the pom.xml file
* Run the command `mvn clean package azure-function:deploy`