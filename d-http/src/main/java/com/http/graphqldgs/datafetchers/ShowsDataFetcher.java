package com.http.graphqldgs.datafetchers;

import com.http.graphqldgs.types.Person;
import com.http.graphqldgs.types.Show;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.util.CollectionUtils;

import java.util.List;

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
        return new Show("showId2", "showName2");
    }
}
