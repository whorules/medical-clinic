package ru.korovko.clinic.configuration.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.security")
@Getter
@Setter
public class ApplicationSecurityProperties {

    private String jwtSecret;
    private int jwtTimeToLive;
}
