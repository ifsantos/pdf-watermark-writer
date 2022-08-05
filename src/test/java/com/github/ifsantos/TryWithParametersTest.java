package com.github.ifsantos;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class TryWithParametersTest {
    Logger log = LoggerFactory.getLogger(getClass());
    @Test
    void throwsFileNotFoundException() {
        assertThrowsExactly(FileNotFoundException.class, () -> {
            try(FileInputStream fis = new FileInputStream(".\new.txt")){
                fis.read();
            }
        });
    }

    @Test
    void throwsIOException(){
        assertThrowsExactly(IOException.class, () -> {
            try(InputStream is = getClass().getClassLoader().getResourceAsStream("test.png")){
                is.close();
                is.read();
            }
        });
    }

}
