package com.github.ifsantos;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StreamsTest {
    

    @Test
    public void test(){
        int N=10;
        int a[]=new int[N];
        for (int i=0; i<N; i++) {
            a[i] = i;
        }
        System.out.print(Stream.of(a)
        .map(String::valueOf )
        .collect(Collectors.joining(" ") 
     ));
    }

    public static void main(String[] args) {
        System.out.println("Testing print array now");
			int N=10;
			
			Integer[] arr = new Integer[N]; //Declare it as the Class Type
			for (int i=0; i<N; i++) {
                arr[i] = i;
			 // input values
			}
            
			System.out.print(
				Stream.of(arr).map(Object::toString).collect(Collectors.joining(" ") 
            ));
    }

}
