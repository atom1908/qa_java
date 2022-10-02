package com.example;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


@RunWith(Enclosed.class)
public class LionTest {

    @RunWith(Parameterized.class)
    public static class LionTestWithParameters {
        private final String sex;
        private final boolean hasMane;

        public LionTestWithParameters(String sex, boolean hasMane) {
            this.sex = sex;
            this.hasMane = hasMane;
        }

        @Parameterized.Parameters
        public static Object[][] selectSex() {
            return new Object[][]{
                    {"Самец", true},
                    {"Самка", false},
            };
        }

        @Test
        public void doesHaveManeTest() throws Exception {
            Lion lion = new Lion(sex);
            boolean isDoesHaveMane = lion.doesHaveMane();
            assertEquals(hasMane, isDoesHaveMane);
        }
    }


    @RunWith(MockitoJUnitRunner.class)
    public static class LionTestWithoutParameters {
        @Mock
        private Feline feline;

        @Test
        public void getKittensTest() {
            Mockito.when(feline.getKittens()).thenReturn(1);
            Lion lion = new Lion(feline);
            int expectedKittens = 1;
            assertEquals(expectedKittens, lion.getKittens());
        }


        @Test
        public void doesHaveManeExceptionTest() {
            Exception exception = assertThrows(Exception.class, () -> {
                new Lion("Не самец и не самка");
            });
            assertEquals("Используйте допустимые значения пола животного - самец или самка", exception.getMessage());
        }

        @Test
        public void getFoodTest() throws Exception {
            List<String> food = List.of("Животные", "Птицы", "Рыба");
            Mockito.when(feline.getFood("Хищник")).thenReturn(food);
            Lion lion = new Lion(feline);
            assertEquals(food, lion.getFood());
        }
    }
}