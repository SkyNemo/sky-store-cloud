package cn.edu.kmust.store;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
@EnableTurbine
@EnableHystrixDashboard
@EnableHystrix
public class AdminServer {

	public static void main(String[] args) {
		SpringApplication.run(AdminServer.class, args);
	}
}
