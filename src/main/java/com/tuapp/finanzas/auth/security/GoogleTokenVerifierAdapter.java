package com.tuapp.finanzas.auth.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.tuapp.finanzas.auth.domain.exception.TokenGoogleInvalidoException;
import com.tuapp.finanzas.auth.domain.port.out.GoogleTokenVerifierPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class GoogleTokenVerifierAdapter implements GoogleTokenVerifierPort {

    private final GoogleIdTokenVerifier verifier;

    public GoogleTokenVerifierAdapter(
            @Value("${google.oauth.client-id}") String clientId
    ) {
        this.verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance()
        )
                .setAudience(Collections.singletonList(clientId))
                .build();
    }

    @Override
    public GooglePayload verificar(String idToken) {
        try {
            GoogleIdToken token = verifier.verify(idToken);
            if (token == null) {
                throw new TokenGoogleInvalidoException();
            }
            GoogleIdToken.Payload payload = token.getPayload();

            String sub = payload.getSubject();
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String picture = (String) payload.get("picture");

            if (email == null || email.isBlank()) {
                throw new TokenGoogleInvalidoException();
            }
            return new GooglePayload(sub, email, name, picture);
        } catch (TokenGoogleInvalidoException e) {
            throw e;
        } catch (Exception e) {
            throw new TokenGoogleInvalidoException();
        }
    }
}
