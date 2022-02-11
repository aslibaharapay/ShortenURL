package com.asli.shortener.url.api.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class Base62Test {

    @Test
    public void should_returnEncodingValue_whenSendValue() {
        assertEquals("I", Base62.encodeToString(34));
    }

    @Test
    public void should_returnDecodingValue_whenSendEncodingValue() {
        assertEquals(12, Base62.decodeFromString("m"));
    }

    @Test
    public void should_returnEncodingValue_whenSendGreaterThan62() {
        assertEquals("bf", Base62.encodeToString(67));
    }

    @Test
    public void should_returnDecodingValue_whenSendEncodingGreaterThan62Value() {
        assertEquals(112, Base62.decodeFromString("bY"));
    }
}
