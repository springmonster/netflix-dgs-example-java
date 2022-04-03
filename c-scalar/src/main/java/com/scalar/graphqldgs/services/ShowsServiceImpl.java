package com.scalar.graphqldgs.services;

import com.scala.graphqldgs.types.Show;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ShowsServiceImpl implements ShowsService {
    @Override
    public List<Show> shows() {
        return Arrays.asList(
                Show.newBuilder().id(1).title("Stranger Things").releaseYear(2016)
                        .price(100L).bigDecimal(new BigDecimal("100.00")).dateTime(OffsetDateTime.now()).uuid(UUID.randomUUID())
                        .build(),
                Show.newBuilder().id(2).title("Ozark").releaseYear(2017)
                        .price(100L).bigDecimal(new BigDecimal("100.00")).dateTime(OffsetDateTime.now()).uuid(UUID.randomUUID())
                        .build(),
                Show.newBuilder().id(3).title("The Crown").releaseYear(2016)
                        .price(100L).bigDecimal(new BigDecimal("100.00")).dateTime(OffsetDateTime.now()).uuid(UUID.randomUUID())
                        .build(),
                Show.newBuilder().id(4).title("Dead to Me").releaseYear(2019)
                        .price(100L).bigDecimal(new BigDecimal("100.00")).dateTime(OffsetDateTime.now()).uuid(UUID.randomUUID())
                        .build(),
                Show.newBuilder().id(5).title("Orange is the New Black").releaseYear(2013)
                        .price(100L).bigDecimal(new BigDecimal("100.00")).dateTime(OffsetDateTime.now()).uuid(UUID.randomUUID())
                        .build()
        );
    }
}
