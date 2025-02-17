package com.gamza.sportry.service.login;

import com.gamza.sportry.util.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final EmailSender emailSender;
    private final ConcurrentHashMap<String, String> verificationCodes = new ConcurrentHashMap<>();

    public void sendVerificationEmail(String toEmail) {
        String verificationCode = generateVerificationCode();
        verificationCodes.put(toEmail, verificationCode);

        String subject = "회원가입 이메일 인증 코드";
        String body = "인증 코드: " + verificationCode + "\n\n이 코드를 입력하여 이메일을 인증하세요.";

        emailSender.sendEmail(toEmail, subject, body);
    }

    public boolean verifyCode(String email, String inputCode) {
        return verificationCodes.containsKey(email) && verificationCodes.get(email).equals(inputCode);
    }

    private String generateVerificationCode() {
        return String.valueOf(new Random().nextInt(900000) + 100000); // 6자리 랜덤 숫자
    }
}
