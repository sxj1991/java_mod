package com.lazzy.base.extend;

import com.lazzy.base.spingExpand.ConfigListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(loader = ExtendTests.CustomLoader.class)
public class ExtendTests {

    public static class CustomLoader extends SpringBootContextLoader {
        @Override
        protected SpringApplication getSpringApplication() {
            SpringApplication app = super.getSpringApplication();
            app.addListeners(new ConfigListener());
            return app;
        }
    }

    @Test
    public void print(){
        System.out.println("123");
    }
}