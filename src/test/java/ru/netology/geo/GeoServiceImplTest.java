package ru.netology.geo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GeoServiceImplTest {

    private GeoService sut;

    @BeforeEach
    public void setUp() {
        sut = new GeoServiceImpl();
    }

    public static Stream<Arguments> byIp() {
        return Stream.of(
                Arguments.of("127.0.0.1", new Location(null, null, null, 0)),
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.0.32.100", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.12.212.1", new Location("New York", Country.USA, null, 0)),
                Arguments.of("112.246.76.178", null)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void byIp(String ip, Location expected) {
        Location result = sut.byIp(ip);

        assertEquals(expected, result);
    }

    @Test
    public void byCoordinates() {
        double lat = 1.2;
        double lon = 1.1;

        Executable method = () -> sut.byCoordinates(lat, lon);

        assertThrows(RuntimeException.class, method);
    }
}