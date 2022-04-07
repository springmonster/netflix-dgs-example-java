package com.nplusone.graphqldgs.noproblem.services;

import com.inplusone.graphqldgs.types.ShowN;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ShowsNServiceImpl {

    public List<ShowN> shows() {
        return Arrays.asList(
                ShowN.newBuilder().id(1).title("Stranger Things").releaseYear(2016).build(),
                ShowN.newBuilder().id(2).title("Ozark").releaseYear(2017).build(),
                ShowN.newBuilder().id(3).title("The Crown").releaseYear(2016).build(),
                ShowN.newBuilder().id(4).title("Dead to Me").releaseYear(2019).build(),
                ShowN.newBuilder().id(5).title("Orange is the New Black").releaseYear(2013).build()
        );
    }
}
