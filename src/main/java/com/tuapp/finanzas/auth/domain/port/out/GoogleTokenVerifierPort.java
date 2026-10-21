package com.tuapp.finanzas.auth.domain.port.out;

/**
 * Puerto de salida para verificar un idToken emitido por Google Sign-In.
 * La implementación vivirá en la capa de infraestructura y dependerá
 * de la Google API Client Library.
 */
public interface GoogleTokenVerifierPort {

    /**
     * Verifica la firma, el audience y la expiración del idToken.
     *
     * @param idToken token entregado por el cliente Flutter
     * @return payload verificado
     * @throws com.tuapp.finanzas.auth.domain.exception.TokenGoogleInvalidoException
     *         si la verificación falla o el audience no coincide
     */
    GooglePayload verificar(String idToken);

    record GooglePayload(String sub, String email, String name, String picture) {}
}
