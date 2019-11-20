package io.github.burnieliang.metro;

import com.ulisesbocchio.jasyptspringboot.encryptor.DefaultLazyEncryptor;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.springframework.core.env.StandardEnvironment;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

public class ENCTest {

    @Test
    public void test() {
        // 对应application-dev.yml 中配置的根密码
        System.setProperty("jasypt.encryptor.password", "metro");
        StringEncryptor stringEncryptor = new DefaultLazyEncryptor(new StandardEnvironment());


        //加密方法
        System.out.println(stringEncryptor.encrypt("beacon"));
        System.out.println(stringEncryptor.encrypt("LBNlc.327712576"));

        System.out.println(stringEncryptor.encrypt("3D-4A-9B-TT-6G.9090"));

    }

}
