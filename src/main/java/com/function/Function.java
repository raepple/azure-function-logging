package com.function;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;

// Import log4j classes.
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Level;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {

    private static final Logger logger = LogManager.getLogger(Function.class);
    private static final Level AUDIT = Level.getLevel("AUDIT");

    @FunctionName("logtest")
    public HttpResponseMessage run(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.GET, HttpMethod.POST},
                authLevel = AuthorizationLevel.ANONYMOUS)
                HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        String invocationId = context.getInvocationId();

        // Setting invocation id to Log4j context for correlation of execution context logs and log4j logs in AI
        ThreadContext.put("InvocationId", invocationId);

        // Parse query parameter
        final String query = request.getQueryParameters().get("name");
        final String name = request.getBody().orElse(query);
   
        context.getLogger().info("Info log event from execution context logger, InvocationID: " + invocationId);
        
        if (name == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a name on the query string or in the request body").build();
        } else {
            logger.error("Error log event from Log4j. Name: " + name + ", InvocationID: " + invocationId);
            logger.trace("Trace log event from Log4j. Name: " + name + ", InvocationID: " + invocationId);
            logger.log(AUDIT, "Audit log event from Log4j. Name: " + name + ", InvocationID: " + invocationId);           
            return request.createResponseBuilder(HttpStatus.OK).body("Hello, " + name + " (InvocationID: " + invocationId + ")").build();
        }
    }
}
