package com.forever.test.generate;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({PARAMETER,LOCAL_VARIABLE})
@Retention(RetentionPolicy.SOURCE)
public @interface Write {
}
