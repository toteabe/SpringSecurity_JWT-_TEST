package com.app.config.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
  * Retention: Indica que la anotación estará disponible en tiempo de ejecución
  * Target: Indica que la anotación se puede aplicar a un método
  * PreAuthorize: Indica que la anotación es de tipo PreAuthorize
  * value: Indica que la anotación necesita un valor
  * String[]: Indica que el valor es un array de String
  *
  * OBSERVACIONES:
  *  Debe ser una anotación de tipo PreAuthorize y Debes agregar un bean
  * en la clase SecurityConfig para que funcione correctamente
  * en concreto el PrePostTemplateDefaults.
 * */
/*Demasiada verbosidad, usar lo que ya existe. ya sabes que puedes hacer esto
* */

//@Retention(RetentionPolicy.RUNTIME)
//@Target(ElementType.METHOD)
// @PreAuthorize("hasAnyRole('{value}')")
//public @interface roleNeed {
//    String[] value();
//}
