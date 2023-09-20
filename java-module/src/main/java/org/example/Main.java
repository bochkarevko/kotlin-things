package org.example;

public class Main {
    public static void main(String[] args) {
        var fromKotlin = new KotlinClass();

        // we can access javaField "directly"
        System.out.println(fromKotlin.javaField);
        // we can access kotlinProperty only via getter or setter
        System.out.println(fromKotlin.getKotlinProperty());

        // we can call @JvmStatic method from the class itself
        KotlinClass.fullStatic();
        // we can call non-@JvmStatic method only from the Companion
        KotlinClass.Companion.viaCompanionOnly();
    }
}