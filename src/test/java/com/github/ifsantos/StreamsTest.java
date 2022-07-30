package com.github.ifsantos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StreamsTest {

    @Test
    public void testCollectArrayWithSpaces(){
        int N=10;
        Integer a[]=new Integer[N];
        for (int i=0; i<N; i++) {
            a[i] = i;
        }
        String collect = Stream.of(a)
        .map(String::valueOf )
        .collect(Collectors.joining(" ") 
     );
        String expected = "0 1 2 3 4 5 6 7 8 9";
        assertEquals(expected,collect);
        assertThat(collect).isEqualTo(expected);
    }

}
