package ru.ashihmin.weatherdemo.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import kotlin.reflect.KClass

//import java.lang.annotation.Target

@Suppress("DEPRECATED_JAVA_ANNOTATION")
@Documented
@Target(AnnotationTarget.FUNCTION)
//@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)
