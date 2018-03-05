import com.google.gson.JsonSyntaxException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FestivalArtistTest {
    private List<Artist> artists;
    private FestivalArtistImplementation artistManager;
    private static int score = 0;

    @BeforeEach
    void init() {
        artistManager = new FestivalArtistImplementation();

        artists = new ArrayList<>();
        artists.add(new Artist("Vince Staples", Artist.Stage.Avalon, "05/07/2018 20:00", "05/07/2018 21:00"));
        artists.add(new Artist("Eminem", Artist.Stage.Orange, "04/07/2018 20:00", "04/07/2018 22:30"));
        artists.add(new Artist("Nephew", Artist.Stage.Orange, "05/07/2018 20:00", "05/07/2018 22:00"));
        artists.add(new Artist("Benal", Artist.Stage.Avalon, "06/07/2018 16:00", "06/07/2018 17:15"));
        artists.add(new Artist("Emil Kruse", Artist.Stage.Countdown, "06/07/2018 14:00", "06/07/2018 15:00"));
    }

    @Test
    @DisplayName("Get artists from JSON")
    void getArtistsFromJson() throws FileNotFoundException {
        List<Artist> artistsFromJson = artistManager.getArtistsFromJson("artists.json");
        assertEquals(3, artistsFromJson.size());
        assertEquals("Bruno Mars", artistsFromJson.get(0).getArtistName());

        score++;
    }

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

    @Test
    @DisplayName("Get artists form JSON - JsonSyntaxException")
    void getArtistsFromJsonSyntaxTest() {
        assertThrows(JsonSyntaxException.class, this::badJsonSyntax);

        score++;
    }

    private void badJsonSyntax() throws IOException, JsonSyntaxException {
        List<Artist> artistsFromJson = artistManager.getArtistsFromJson("badJson.json");

        score++;
    }

    @Test
    @DisplayName("Artists by day")
    void artistsByDayTest() {
        assertEquals(5, artists.size());
        List<Artist> artistsByDay = artistManager.getArtistsByDay("04/07/2018", artists);
        assertEquals(1, artistsByDay.size());

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

        score++;
    }

    @Test
    @DisplayName("Artists by stage")
    void artistsByStageTest() {
        List<Artist> artistsByStage = artistManager.getArtistsByStage(Artist.Stage.Avalon, artists);
        assertEquals(2, artistsByStage.size());

        score++;
    }

    @Test
    @DisplayName("Sort by popularity")
    void sortByPopularityTest() {
        assertEquals("Vince Staples", artists.get(0).getArtistName());
        artistManager.sortArtistsByPopularity(artists);
        assertEquals("Eminem", artists.get(0).getArtistName());
        System.out.println(artists);

        score++;
    }

    @Test
    @DisplayName("Get longest playing")
    void getLongestPlayingTest() {
        Artist longestPlaying = artistManager.getLongestPlaying(artists);
        assertEquals("Eminem", longestPlaying.getArtistName());

        score++;
    }

    @Test
    @DisplayName("Get shortest playing")
    void getShortestPlayingTest() {
        Artist shortestPlaying = artistManager.getShortestPlaying(artists);
        assertEquals("Vince Staples", shortestPlaying.getArtistName());

        score++;
    }

    @Test
    @DisplayName("Sort by artist name")
    void sortByArtistNameTest() {
        assertEquals("Vince Staples", artists.get(0).getArtistName());
        artistManager.sortByArtistName(artists);
        assertEquals("Benal", artists.get(0).getArtistName());

        score++;
    }

    @Test
    @DisplayName("Sort by playtime")
    void sortByPlaytime() {
        assertEquals("Vince Staples", artists.get(0).getArtistName());
        artistManager.sortByPlaytime(artists);
        assertEquals("Eminem", artists.get(0).getArtistName());

        score++;
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("All tests completed.");
        System.out.printf("Passed: %d out of 11", score);
    }

}
