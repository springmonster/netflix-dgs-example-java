package com.http.graphqldgs.datafetchers;

import com.http.graphqldgs.service.ShowsService;
import com.http.graphqldgs.types.Good;
import com.http.graphqldgs.types.Person;
import com.http.graphqldgs.types.Show;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@DgsComponent
@RequiredArgsConstructor
public class ShowsDataFetcher {

    final ShowsService showsService;

    @DgsQuery
    public Show shows(@InputArgument(collectionType = Person.class) List<Person> personList) {
        if (CollectionUtils.isEmpty(personList)) {
            return new Show("showId1", "showName1");
        }
        return new Show("showId1", personList.get(0).getName());
    }

    @DgsQuery
    public Show show(@InputArgument(name = "people") Person person) {
        return showsService.fromPerson(person);
    }

    @DgsQuery
    public Show showWithGood(@InputArgument(collectionType = Good.class) Optional<Good> good) {
        if (good.isEmpty()) {
            return new Show("showId2", "Good is Empty");
        }
        return new Show("showId2", good.get().getName());
    }
}
