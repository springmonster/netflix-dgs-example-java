package com.http.graphqldgs.service;

import com.http.graphqldgs.types.Person;
import com.http.graphqldgs.types.Show;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShowsService {

    public Show fromPerson(Person person) {
        return new Show("fromPerson", Optional.ofNullable(person).orElse(new Person()).getName());
    }


}
