package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by DUKE SINGH on 3/28/2018.
 * <p>
 * <p>@Qualifier annotation is used to qualify the dependency. @Qualifier is used to distinguish
 * between objects of the same type but with different instances.
 * <p>
 * <p>For example, a class can ask both, an Application Context and an Activity Context. But both
 * these Objects will be of type Context. So, for Dagger2 to figure out which variable is to be
 * provided with what, we have to explicitly specify the identifier for it.
 * <p>
 * <p>In our case, we have ActivityContext and ApplicationContext so that the Context object being
 * injected can refer to the respectiveContext type.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityContext {
}
