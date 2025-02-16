package kz.netcracker.libra.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class QRCodeGenerator {
    public String generateQRCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}