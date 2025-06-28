package com.ingsoft.tf.api_edurents.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void enviarCorreoRecuperacion(String destinatario, String token) throws MessagingException {
        String asunto = "Recuperación de contraseña";

        String contenido = """
            <p>Hola!</p>
            <p>Este es tu código para restablecer tu contraseña:</p>
            <h2>%s</h2>
            <br>
            <p>Ingresa este código en la aplicación para continuar con el proceso.</p>
            <p>Si no realizaste esta solicitud, puedes ignorar este mensaje.</p>
            """.formatted(token);

        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

        helper.setTo(destinatario);
        helper.setSubject(asunto);
        helper.setText(contenido, true);

        mailSender.send(mensaje);
    }

}
