import com.google.gson.JsonSyntaxException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FestivalArtistTest {
    private List<Artist> artists;
    private FestivalArtistImplementation artistManager;
    private static int score = 0;

    @BeforeEach
    void init() {
        artistManager = new FestivalArtistImplementation();

        artists = new ArrayList<>();
        artists.add(new Artist("Vince Staples", Stage.Avalon, "05/07/2018 20:00", "05/07/2018 21:00"));
        artists.add(new Artist("Eminem", Stage.Orange, "04/07/2018 20:00", "04/07/2018 22:30"));
        artists.add(new Artist("Nephew", Stage.Orange, "05/07/2018 20:00", "05/07/2018 22:00"));
        artists.add(new Artist("Benal", Stage.Avalon, "06/07/2018 16:00", "06/07/2018 17:15"));
        artists.add(new Artist("Emil Kruse", Stage.Countdown, "06/07/2018 14:00", "06/07/2018 15:00"));
    }

    //Data driven test number 1.
    @Test
    @DisplayName("Get artists from JSON")
    void getArtistsFromJsonTest() throws FileNotFoundException {
        List<Artist> artistsFromJson = artistManager.getArtistsFromJson("artists.json");
        assertThat(artistsFromJson, hasSize(3));
        assertThat(artistsFromJson.get(0).getArtistName(), is("Bruno Mars"));

        score++;
    }

    //Parameterized data driven test
    @ParameterizedTest
    @ValueSource(strings = {"artists.json", "artists2.json", "artists3.json", "artists4.json", "artists5.json"})
    @DisplayName("Data driven - Artist objects loaded")
    void artistsLoadedFromJsonTest(String jsonFile) throws FileNotFoundException {
        List<Artist> artistsFromJson = artistManager.getArtistsFromJson(jsonFile);

        assertThat(artistsFromJson, not(empty()));

        score++;
    }

    //Data driven test number 3.
    @Test
    @DisplayName("Get artists from JSON - FileNotFoundException")
    void getArtistsFromJsonFileNotFoundTest() {
        assertThrows(FileNotFoundException.class, this::fileNotFound);

        score++;
    }

    private void fileNotFound() throws FileNotFoundException {
        List<Artist> artistsFromJson = artistManager.getArtistsFromJson("404.json");

        score++;
    }

    //Data driven test number 4.
    @Test
    @DisplayName("Get artists form JSON - JsonSyntaxException")
    void getArtistsFromJsonSyntaxTest() {
        assertThrows(JsonSyntaxException.class, this::badJsonSyntax);

        score++;
    }

    private void badJsonSyntax() throws IOException, JsonSyntaxException {
        List<Artist> artistsFromJson = artistManager.getArtistsFromJson("badJson.json");
    }

    @Test
    @DisplayName("Artists by day")
    void artistsByDayTest() {
        assertThat(artists, hasSize(5));
        List<Artist> artistsByDay = artistManager.getArtistsByDay("04/07/2018", artists);
        assertThat(artistsByDay, hasSize(1));
        assertThat(artistsByDay.get(0).getArtistName(), is("Eminem"));

        score++;
    }

    @Test
    @DisplayName("Artists by day - bad format")
    void artistByDayExceptionTest() {
        assertThrows(IllegalArgumentException.class, this::badFormatEntered);

        score++;
    }

    private void badFormatEntered() {
        List<Artist> artistsByDay = artistManager.getArtistsByDay("04-07-2018", artists);
    }

    @Test
    @DisplayName("Artists by stage")
    void artistsByStageTest() {
        List<Artist> artistsByStage = artistManager.getArtistsByStage(Stage.Avalon, artists);
        assertThat(artistsByStage, hasSize(2));

        score++;
    }

    @Test
    @DisplayName("Sort by popularity")
    void sortByPopularityTest() {
        assertThat(artists.get(0).getArtistName(), is("Vince Staples"));
        artistManager.sortArtistsByPopularity(artists);
        assertThat(artists.get(0).getArtistName(), is("Eminem"));

        score++;
    }

    @Test
    @DisplayName("Get longest playing")
    void getLongestPlayingTest() {
        Artist longestPlaying = artistManager.getLongestPlaying(artists);
        assertThat(longestPlaying.getArtistName(), is("Eminem"));

        score++;
    }

    @Test
    @DisplayName("Get shortest playing")
    void getShortestPlayingTest() {
        Artist shortestPlaying = artistManager.getShortestPlaying(artists);
        assertThat(shortestPlaying.getArtistName(), is("Vince Staples"));

        score++;
    }

    @Test
    @DisplayName("Sort by artist name")
    void sortByArtistNameTest() {
        assertThat(artists.get(0).getArtistName(), is("Vince Staples"));
        artistManager.sortByArtistName(artists);
        assertThat(artists.get(0).getArtistName(), is("Benal"));

        score++;
    }

    @Test
    @DisplayName("Sort by playtime")
    void sortByPlaytimeTest() {
        assertThat(artists.get(0).getArtistName(), is("Vince Staples"));
        artistManager.sortByPlaytime(artists);
        assertThat(artists.get(0).getArtistName(), is("Eminem"));

        score++;
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("All tests completed.");
        System.out.printf("Passed tests: %d out of 16", score);
    }

}
