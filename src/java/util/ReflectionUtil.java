package util;

import processing.validation.TargetKey;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class ReflectionUtil {
    public static void callValueAnnotatedMethod(Object thisRef, String value) {
        try {
            Method[] methods = thisRef.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(ValueAnnotation.class)) {
                    String methodValue = method.getAnnotation(ValueAnnotation.class).value();
                    if (methodValue.equals(value)) {
                        method.invoke(thisRef);
                        return;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            System.out.println("ReflectionUtil - callValueAnnotatedMethod : IllegalAccessException");
        } catch (InvocationTargetException e) {
            System.out.println("ReflectionUtil - callValueAnnotatedMethod : InvocationTargetException");
        }
    }

    public static void callTargetAnnotatedMethod(Object thisRef, TargetKey key, String value) {
        try {
            Method[] methods = thisRef.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(TargetKeyReciever.class)) {
                    TargetKey methodValue = method.getAnnotation(TargetKeyReciever.class).value();
                    if (methodValue.equals(key)) {
                        method.invoke(thisRef, value);
                        return;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            System.out.println("ReflectionUtil - callValueAnnotatedMethod : IllegalAccessException");
        } catch (InvocationTargetException e) {
            System.out.println("ReflectionUtil - callValueAnnotatedMethod : InvocationTargetException");
        }
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
}
