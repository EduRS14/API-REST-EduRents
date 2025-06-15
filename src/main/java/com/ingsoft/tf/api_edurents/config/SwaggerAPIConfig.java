package com.ingsoft.tf.api_edurents.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class SwaggerAPIConfig {
    //Param del archivo applicationpropperties
    @Value("${api_edurents.openapi.dev-url}")
    private String devUrl;
    @Bean
    public OpenAPI myOpenAPI(){
        //Definir el servidor de desarrollo
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Development Server");
        //Informacion de contacto
        Contact contact = new Contact();
        contact.setName("ISW Corp");
        contact.setEmail("ingsw@gmail.com");
        contact.setUrl("ingsw.com");
        License mitLicense = new License().name("MIT License").url("opensource.org/licenses/MIT");
        //Informacion general de la API
        Info info = new Info()
                .title("API Task Management - EduRents Project")
                .version("1.0")
                .contact(contact)
                .description("API Restful de gestión de usuarios, productos e intercambios para el proyecto EduRents.")
                .termsOfService("ingsw.com/terms")
                //.termsOfService("github.com/ingsw/taskmanagement-api/terms")
                .license(mitLicense);
        return new OpenAPI()
                .info(info)
                .addServersItem(devServer);
    }
}
