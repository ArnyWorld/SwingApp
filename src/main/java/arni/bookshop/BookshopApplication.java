package arni.bookshop;

import arni.bookshop.view.BookForm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

@SpringBootApplication
public class BookshopApplication {

	public static void main(String[] args) {

		//SpringApplication.run(BookshopApplication.class, args);
		ConfigurableApplicationContext contextSpring = new SpringApplicationBuilder(BookshopApplication.class)
				.headless(false)
				.web(WebApplicationType.NONE)
				.run(args);
		//Ejecutamos el código para cargar e formulario
		EventQueue.invokeLater(()->{
			//Obtenemos el objeto form a través de Spring
			BookForm bookForm = contextSpring.getBean(BookForm.class);
			bookForm.setVisible(true);
		});
	}

}
