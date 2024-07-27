
package com.app.config.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.authorization.method.MethodAuthorizationDeniedHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SecurityErrorHandler implements MethodAuthorizationDeniedHandler {
/*
* Por defecto, cuando un usuario no tiene permisos para acceder a un recurso, se lanza una excepción
* por lo que se puede manejar la excepción y devolver un mensaje personalizado
* por motivos que desconozco no se puede devolver un objeto, por lo que se debe devolver un String
* convertido a un objeto JSON.
*  y solo funciona si usamos anotaciones en los permisos de los métodos
*  con @PreAuthorize("hasRole('ROLE')") o @PreAuthorize("hasAnyRole('ADMIN ','DEVELOPER')") etc
* con el metodo  el requestMarchers no me funciona y no se porque
* por lo que uso las anotaciones
* */
    @Override
    public Object handleDeniedInvocation(MethodInvocation methodInvocation, AuthorizationResult authorizationResult) {

        log.info("\n\n\n");
        /*
        * Loguear el método que se intentó acceder
        * y si el usuario tiene permisos para acceder
        * */
        log.info(String.format("Method info -> %s", methodInvocation.toString()));
        log.info(String.format("User is authorized? %s", authorizationResult.isGranted()));
        // Devolver un mensaje personalizado
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode jsonNode = mapper.createObjectNode();
        jsonNode.put("code", 401);
        jsonNode.put("message", "Not authorized");
        //Convertir el objeto a un String
        try {
            return mapper.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JsonProcessingException", e);
        }
    }
}
