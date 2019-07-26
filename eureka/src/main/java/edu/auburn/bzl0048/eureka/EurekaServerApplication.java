package edu.auburn.bzl0048.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}


	/**
	 * 2.1版本的security默认加上了 csrf 拦截, 所以需要通过重写方法, 把csrf拦截禁用
	 * 参考: https://github.com/yinjihuan/spring-cloud/issues/1
	 * <pre>
	 *     This is because @EnableWebSecurity is now added by default when Spring Security is on the classpath.
	 *     This enable CSRF protection by default. You will have the same problem in 1.5.10 if you add @EnableWebSecurity.
	 *     One work around, which is not the most secure workaround if you have browsers using the Eureka dashboard, is to disable CSRF protection.
	 *     This can be done by adding the following configurations to your app.
	 * </pre>
	 */
//	@EnableWebSecurity
//	static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//		@Override
//		protected void configure(HttpSecurity httpSecurity) throws Exception {
//			httpSecurity.csrf().disable();
//			super.configure(httpSecurity);
//		}
//	}

}
