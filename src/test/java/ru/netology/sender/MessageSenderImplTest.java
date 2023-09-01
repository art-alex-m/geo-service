package ru.netology.sender;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Header;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageSenderImplTest {

    @ParameterizedTest
    @MethodSource
    public void send(Map<String, String> headers, Integer byIpTimes,
            Integer localeTimes, Country country, String expected) {
        Location location = new Location("Some-City", country, "Lenina", 15);
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.anyString())).thenReturn(location);
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(country)).thenReturn(expected);
        MessageSender sut = new MessageSenderImpl(geoService, localizationService);

        String result = sut.send(headers);

        assertEquals(expected, result);
        Mockito.verify(geoService, Mockito.times(byIpTimes)).byIp(headers.get(Header.IP_ADDRESS.value));
        Mockito.verify(localizationService, Mockito.times(localeTimes)).locale(country);
    }

    public static Stream<Arguments> send() {
        String rus = "Добро пожаловать";
        String eng = "Welcome";

        return Stream.of(
                Arguments.of(createHeaders("172.0.32.11"), 1, 1, Country.RUSSIA, rus),
                Arguments.of(createHeaders("127.0.0.1"), 1, 1, Country.USA, eng),
                Arguments.of(createHeaders(""), 0, 1, Country.USA, eng),
                Arguments.of(createHeaders(null), 0, 1, Country.USA, eng),
                Arguments.of(
                        new HashMap<String, String>() {{
                            put("x-some-unexpected-header", "172.0.12.13");
                        }},
                        0,
                        1,
                        Country.USA,
                        eng
                )
        );
    }

    private static Map<String, String> createHeaders(String ip) {
        Map<String, String> headers = new HashMap<>();
        headers.put(Header.IP_ADDRESS.value, ip);

        return headers;
    }
}