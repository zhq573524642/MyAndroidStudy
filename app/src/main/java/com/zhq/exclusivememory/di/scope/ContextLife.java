package com.zhq.exclusivememory.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;


import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/18.
 */
@Qualifier
@Documented
@Retention(RUNTIME)
public @interface ContextLife {
   String value() default "Application";
}
