package ru.netology.i18n;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LocalizationServiceImplTest {

    public static Stream<Arguments> locale() {
        String rus = "^[а-яА-Я\s]+$";
        String eng = "^[a-zA-Z\s]+$";

        return Stream.of(
                Arguments.of(Country.RUSSIA, rus),
                Arguments.of(Country.USA, eng),
                Arguments.of(Country.BRAZIL, eng),
                Arguments.of(Country.GERMANY, eng)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void locale(Country country, String expected) {
        LocalizationService sut = new LocalizationServiceImpl();

        String result = sut.locale(country);

        assertTrue(result.matches(expected));
    }
}