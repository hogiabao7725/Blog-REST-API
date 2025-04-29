package com.hgb7725.blog.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot Blog App REST APIs",
                description = "Spring Boot Blog App REST APIs Documentation",
                version = "v1.0",
                contact = @Contact(
                        name = "Ho Gia Bao",
                        email = "hgb@gmail.com",
                        url = "https://hgb7725.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://hgb7725.com/license"
                )
        ),
        // External Documentations: Github, ...
        externalDocs = @ExternalDocumentation(
                description = "Spring Boot Blog App REST APIs",
                url = "https://github.com/hogiabao7725/Blog-REST-API"
        ),
        // Define common Tags for the entire API documentation here
        tags = {
                @Tag(
                        name = "CRUD REST APIs for Category Resource",
                        description = "Endpoints for creating, retrieving, updating, and deleting blog categories."
                ),
                @Tag(
                        name = "CRUD REST APIs for Post Resource",
                        description = "Endpoints for managing blog posts, including creation, retrieval, updating, and deletion of posts."
                ),
                @Tag(
                        name = "CRUD REST APIs for Comment Resource",
                        description = "Endpoints for handling comments on blog posts, such as adding, fetching, updating, and removing comments."
                ),
                @Tag(
                        name = "CRUD REST APIs for Auth Resource",
                        description = "Endpoints for user authentication, including registration, login, and role-based access control."
                )
        }
)
@SecurityScheme(
        name = "Bear Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenAPIConfig {
}
