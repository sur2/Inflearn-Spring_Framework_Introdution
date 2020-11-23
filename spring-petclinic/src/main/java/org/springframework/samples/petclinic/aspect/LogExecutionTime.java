package org.springframework.samples.petclinic.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 적용 범위
@Retention(RetentionPolicy.RUNTIME) // 유지
public @interface LogExecutionTime {

}
