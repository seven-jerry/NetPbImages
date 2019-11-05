package util;

import processing.validation.TargetKey;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.stream.Stream;

public class ReflectionUtil {
    public static void callValueAnnotatedMethod(Object thisRef, final String value) {
        Method[] methods = thisRef.getClass().getDeclaredMethods();
        Stream.of(methods).filter(m -> m.isAnnotationPresent(ValueAnnotation.class))
                .filter(m -> m.getAnnotation(ValueAnnotation.class).value().equals(value))
                .forEach(m -> invokeMethod(thisRef, m));
    }

    public static void callTargetAnnotatedMethod(Object thisRef, TargetKey key) {
        Method[] methods = thisRef.getClass().getDeclaredMethods();
        Stream.of(methods).filter(m -> m.isAnnotationPresent(TargetKeyReciever.class))
                .filter(m -> m.getAnnotation(TargetKeyReciever.class).value().equals(key))
                .forEach(m -> invokeMethod(thisRef, m));
    }


    public static Object createNewInstance(String className) {
        try {
            Objects.requireNonNull(className);
            Class<?> characterAccessClass = Class.forName(className);
            return characterAccessClass.newInstance();
        } catch (NullPointerException ex) {
            System.out.println("ReflectionUtil - createNewInstance : NullPointerException");
        } catch (IllegalArgumentException ex) {
            System.out.println("ReflectionUtil - createNewInstance : IllegalArgumentException");
        } catch (ClassNotFoundException ex) {
            System.out.println("ReflectionUtil - createNewInstance : Class <" + className + "> not found ");
        } catch (IllegalAccessException e) {
            System.out.println("ReflectionUtil - createNewInstance : IllegalAccessException");
        } catch (InstantiationException e) {
            System.out.println("ReflectionUtil - createNewInstance : InstantiationException");
        }
        return null;
    }

    private static void invokeMethod(Object thisRef, Method method) {
        try {
            method.invoke(thisRef);
        } catch (IllegalAccessException e) {
            System.out.println("ReflectionUtil - callValueAnnotatedMethod : IllegalAccessException");
        } catch (InvocationTargetException e) {
            System.out.println("ReflectionUtil - callValueAnnotatedMethod : InvocationTargetException");
        }
    }
}
