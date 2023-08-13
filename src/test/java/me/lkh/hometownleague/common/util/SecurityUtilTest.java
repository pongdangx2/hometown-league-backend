package me.lkh.hometownleague.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;

class SecurityUtilTest {

    @DisplayName("패스워드 중복 체크 테스트")
    @Test
    void encrypt() throws NoSuchAlgorithmException {
        String firstPassword = "tesTP@ssw0Rd";
        String secondPassword = "Testp@sSw0rD";

        String encryptedFirstPassword = SecurityUtil.encrypt(firstPassword);
        String encryptedSecondPassword = SecurityUtil.encrypt(secondPassword);

        assertThat(encryptedFirstPassword)
                .isEqualTo(SecurityUtil.encrypt(firstPassword));

        assertThat(encryptedFirstPassword)
                .isNotEqualTo(encryptedSecondPassword);

    }
}