package br.dev.mhc.consultaibge;

import br.dev.mhc.consultaibge.services.GenerateSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ConsultaIbgeApplication implements CommandLineRunner {

	private final GenerateSqlService generateSqlService;

	@Autowired
    public ConsultaIbgeApplication(GenerateSqlService generateSqlService) {
        this.generateSqlService = generateSqlService;
    }

    public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ConsultaIbgeApplication.class, args);
		int exitCode = SpringApplication.exit(context);
		System.exit(exitCode);
	}

	@Override
	public void run(String... args) throws Exception {

		generateSqlService.generateSqlInsert();

	}
}
