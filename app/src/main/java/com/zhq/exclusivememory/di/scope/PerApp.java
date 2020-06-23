package com.zhq.exclusivememory.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/18.
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface PerApp {
}
