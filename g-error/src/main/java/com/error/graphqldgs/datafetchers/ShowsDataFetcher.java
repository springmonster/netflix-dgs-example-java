package com.error.graphqldgs.datafetchers;

import com.error.graphqldgs.exception.ShowNotFoundException;
import com.error.graphqldgs.types.Good;
import com.error.graphqldgs.types.Person;
import com.error.graphqldgs.types.Show;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DgsComponent
public class ShowsDataFetcher {

    @DgsQuery
    public Show shows(@InputArgument(collectionType = Person.class) List<Person> personList) {
        if (CollectionUtils.isEmpty(personList)) {
            return new Show("showId1", "showName1");
        }
        return new Show("showId1", personList.get(0).getName());
    }

    @DgsQuery
    public Show show(@InputArgument(name = "people") Person person) {
        if ("lisi".equals(person.getName())) {
            return new Show("showId2", person.getName());
        } else if ("zhangsan".equals(person.getName())) {
            int a = 100;
            int b = 0;
            int c = a / b;
            return null;
        } else {
            throw new ShowNotFoundException(UUID.randomUUID().toString());
        }
    }

    @DgsQuery
    public Show showWithGood(@InputArgument(collectionType = Good.class) Optional<Good> good) {
        if (good.isEmpty()) {
            return new Show("showId2", "Good is Empty");
        }
        return new Show("showId2", good.get().getName());
    }
}
