package cn.edu.kmust.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import zipkin.server.EnableZipkinServer;


@SpringBootApplication
@EnableDiscoveryClient
@EnableZipkinServer
public class SleuthServer {

	public static void main(String[] args) {
		SpringApplication.run(SleuthServer.class, args);
	}
}
