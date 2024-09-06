package rookies.MultiPath.core.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;

public class TemporaryAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public TemporaryAuthenticationToken() {
        super(null, null, Collections.emptyList());
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }
}
