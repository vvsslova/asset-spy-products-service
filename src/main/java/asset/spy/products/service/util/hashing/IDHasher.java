package asset.spy.products.service.util.hashing;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class IDHasher {
    private static Long PASSWORD;
    private final Environment environment;

    @PostConstruct
    public void init() {
        PASSWORD = Long.valueOf(
                Objects.requireNonNull(environment.getProperty("hasher_password")));
    }

    public static Long hashId(long id) {
        return id ^ PASSWORD;
    }
}