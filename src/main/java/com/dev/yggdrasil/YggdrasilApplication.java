package com.dev.yggdrasil;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@Theme(value = "yggdrasil")
@EnableFeignClients
public class YggdrasilApplication implements AppShellConfigurator {

    public static void main(final String[] args) {
        SpringApplication.run(YggdrasilApplication.class, args);
    }

}
