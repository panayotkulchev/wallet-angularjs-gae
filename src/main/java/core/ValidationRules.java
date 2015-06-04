package core;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Panayot Kulchev on 15-5-8
 * e-mail: panayotkulchev@gmail.com
 * happy codding ...
 */
@BindingAnnotation
@Target({FIELD, PARAMETER, METHOD})
@Retention(RUNTIME)
public @interface ValidationRules {
}
