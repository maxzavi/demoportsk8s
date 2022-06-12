# Api Demo

Create project using *Spring Initializr* with Spring Web, and Spring Boot DevTools

Create controller RandomController:
```java
package com.example.demoapi.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/random/api/v1")
public class RandomController {

    @GetMapping("/")
    public String getValue(){
        double doubleValue = Math.random();
        int result = (int)(doubleValue*10000);
        String hostName = "";
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            hostName = inetAddress.getHostName();
        } catch (UnknownHostException e) {
            hostName="N/A";
        }
        return String.format("Number: %d, from %s", result, hostName);
    }
    
}
```

Add dockerfile, using FROM eclipse-temurin:17-jre-alpine

```docker
FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
COPY target/demoapi-0.0.1-SNAPSHOT.jar demoapi.jar
EXPOSE 8080
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar demoapi.jar
```

Docker build/push:
```cmd
docker build -t mzavaletav/demoapi:1.1 .
docker push mzavaletav/demoapi:1.1
```

Deploy in k8s, using port mapping as **Node Port** and **Cluster IP**

Use deployClusterIP.yaml for Cluster ip

```cmd
kubectl -n prueba apply -f deployClusterIP.yaml
```

Nodeport, use deployNodePort.yaml

```cmd
kubectl -n prueba apply -f deployNodePort.yaml
```


1. Test without port mapping



# Client demo 

Create project using *Spring Initializr* whitout starters, and implements CommandLineRunner

Add dependency okhttp, client for http in pom.xml

```xml
		<!-- https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp -->
		<dependency>
			<groupId>com.squareup.okhttp3</groupId>
			<artifactId>okhttp</artifactId>
			<version>4.9.3</version>
		</dependency>
```

Use looger **slf4j**

```java
	private Logger _logger = LoggerFactory.getLogger(DemoclientApplication.class); 
```

```java
package com.example.democlient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@SpringBootApplication
public class DemoclientApplication implements CommandLineRunner {

	private Logger _logger = LoggerFactory.getLogger(DemoclientApplication.class); 
	public static void main(String[] args) {
		SpringApplication.run(DemoclientApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		int sleepTimeSeconds =5;

		_logger.info(String.format("Start sleep: %d", sleepTimeSeconds));
		while(true){
			String url="";

			if (args.length==1) url= args[0];
			else url= System.getenv("URL");
			_logger.info(String.format("Url: %s",url));
	
			try {
				OkHttpClient client = new OkHttpClient().newBuilder().build();
				Request request = new Request.Builder()
					  .url( url+ "/random/api/v1/")
					  .method("GET", null)
					  .build();
				Response response = client.newCall(request).execute();
				_logger.info(String.format("Response: %s", response.body().string()));
					
			} catch (Exception e) {
				_logger.error(String.format("Error: %s", e.getMessage()));
			}
			Thread.sleep(sleepTimeSeconds*1000);
		}
	}

}
```

Generate package pass test:

```cmd
mvn package -DskipTests
```
Docker build/push:
```cmd
docker build -t mzavaletav/democlient:1.1 .
docker push mzavaletav/democlient:1.1
```

use kubectl for deploy.yaml in k8s, asign ip in env URL and deploy using kubectl

```cmd
kubectl -n prueba apply -f deploy-client.yaml
```
