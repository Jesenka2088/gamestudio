package sk.tsystems.gamestudio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class GameStudioServer {
	public static void main(String[] args) {
        SpringApplication.run(GameStudioServer.class, args);
    }	
}
