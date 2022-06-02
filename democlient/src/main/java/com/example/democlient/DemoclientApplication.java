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
