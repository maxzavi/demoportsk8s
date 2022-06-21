package pe.maxz.democontainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import pe.maxz.democontainer.repository.DemoRepository;

@SpringBootApplication
public class DemocontainerApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(DemocontainerApplication.class, args);
	}

	@Autowired
	private DemoRepository demoRepository;
	
	@Override
	public void run(String... args) throws Exception {

		while(true){
			demoRepository.execute();
			Thread.sleep(5000);
		}
	}

}
