package com.kotlin.xkotlin.datafetchers

import com.example.demo.generated.types.Show
import com.kotlin.xkotlin.services.ShowsService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import kotlinx.coroutines.coroutineScope

@DgsComponent
class ShowsDataFetcher(private val showsService: ShowsService) {
    @DgsQuery
    suspend fun shows(@InputArgument titleFilter: String?): List<Show> = coroutineScope {
        if (titleFilter != null) {
            showsService.shows().filter { it.title.contains(titleFilter) }
        } else {
            showsService.shows()
        }
    }
}