package world.haymon.spring_architecture;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		//SpringApplication.run(Application.class, args);

		SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);

		builder.bannerMode(Banner.Mode.OFF);
		builder.profiles("dev");
		builder.run(args);

		// Context Initialized
		ConfigurableApplicationContext context = builder.context();
		ConfigurableEnvironment environment = context.getEnvironment();

		// context.getBean("productRepository");

		String property = environment.getProperty("spring.application.name");
		System.out.println("Name of App: " + property);

		ValueExemplo bean = context.getBean(ValueExemplo.class);
		bean.print();
	}
}
