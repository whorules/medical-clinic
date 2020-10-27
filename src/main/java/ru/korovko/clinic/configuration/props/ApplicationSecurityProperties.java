package ru.korovko.clinic.configuration.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class ApplicationSecurityProperties {

    private String jwtSecret;
    private int jwtTimeToLive;
    private String jwtIssuer;
    private String jwtAudience;
}
