package com.kotlin.xkotlin.datafetchers

import com.example.demo.generated.types.Show
import com.kotlin.xkotlin.scalars.DateTimeScalarRegistration
import com.kotlin.xkotlin.services.ShowsService
import com.netflix.graphql.dgs.DgsQueryExecutor
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest(classes = [ShowsDataFetcher::class, DgsAutoConfiguration::class, DateTimeScalarRegistration::class])
internal class ShowsDataFetcherTest {

    @Autowired
    lateinit var dgsQueryExecutor: DgsQueryExecutor

    @MockBean
    lateinit var showsService: ShowsService

    @BeforeEach
    fun before() {
        `when`(showsService.shows()).thenAnswer { listOf(Show(id = 1, title = "mock title", releaseYear = 2020)) }
    }

    @Test
    fun shows() {
        val titles: List<String> = dgsQueryExecutor.executeAndExtractJsonPath(
            """
            {
                shows {
                    title
                    releaseYear
                }
            }
        """.trimIndent(), "data.shows[*].title"
        )

        assertThat(titles).contains("mock title")
    }
}