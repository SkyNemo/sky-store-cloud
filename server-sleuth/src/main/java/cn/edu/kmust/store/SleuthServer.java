package cn.edu.kmust.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;


@EnableZipkinServer
@SpringBootApplication
public class SleuthServer {

	public static void main(String[] args) {
		SpringApplication.run(SleuthServer.class, args);
	}
}
