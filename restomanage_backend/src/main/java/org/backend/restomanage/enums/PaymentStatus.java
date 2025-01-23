package org.backend.restomanage.enums;

public enum PaymentStatus {
    PENDING,    // Payment has been initiated but not completed
    PAID,       // Payment has been successfully completed
    FAILED,     // Payment attempt failed
    REFUNDED,   // Payment was successful but later refunded
    CANCELLED   // Payment was cancelled before completion
}