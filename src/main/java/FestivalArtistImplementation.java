import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A possible implementation of the {@link FestivalArtistManager} interface.
 */
public class FestivalArtistImplementation implements FestivalArtistManager {
    //Common DateTime format.
    private DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");

    public List<Artist> getArtistsFromJson(String jsonFile) throws FileNotFoundException, JsonSyntaxException {
        JsonParser jsonParser = new JsonParser();
        List<Artist> artistsFromJson = new ArrayList<>();

        JsonObject json = (JsonObject) jsonParser.parse(new FileReader(jsonFile));
        JsonArray jsonArray = json.get("Artists").getAsJsonArray();
        for (Object jsonObject : jsonArray) {
            JsonObject jsonArtist = (JsonObject) jsonObject;
            String artistName = jsonArtist.get("artistName").getAsString();
            String stage = jsonArtist.get("stage").getAsString();
            String showStart = jsonArtist.get("showStart").getAsString();
            String showEnd = jsonArtist.get("showEnd").getAsString();

            Artist artist = new Artist(artistName, Artist.Stage.valueOf(stage), showStart, showEnd);
            artistsFromJson.add(artist);
        }

        return artistsFromJson;

    }

    public List<Artist> getArtistsByDay(String date, List<Artist> artists) throws IllegalArgumentException {
        ArrayList<Artist> artistsByDate = new ArrayList<Artist>();

        DateTime dateTime = null;
        dateTime = dtf.parseDateTime(date);

        for (Artist artist : artists) {
            if (artist.getShowStart().withTimeAtStartOfDay().isEqual(dateTime.withTimeAtStartOfDay())) {
                artistsByDate.add(artist);
            }
        }

        return artistsByDate;
    }

    public List<Artist> getArtistsByStage(Artist.Stage stage, List<Artist> artists) {
        List<Artist> artistsByStage = new ArrayList<Artist>();

        for (Artist artist : artists) {
            if (artist.getStage() == stage) artistsByStage.add(artist);
        }

        return artistsByStage;
    }

    public void sortArtistsByPopularity(List<Artist> artists) {
        Comparator<Artist> comparator = new Comparator<Artist>() {
            public int compare(Artist a1, Artist a2) {
                return a2.getStage().getPopularity() - a1.getStage().getPopularity();
            }
        };

        Collections.sort(artists, comparator);
    }

    public Artist getLongestPlaying(List<Artist> artists) {
        Interval largestInterval = null;
        Artist longestPlaying = null;

        for (Artist artist : artists) {
            Interval interval = new Interval(artist.getShowStart(), artist.getShowEnd());
            if (largestInterval == null || interval.toDurationMillis() > largestInterval.toDurationMillis()) {
                largestInterval = interval;
                longestPlaying = artist;
            }
        }
        return longestPlaying;
    }

    public Artist getShortestPlaying(List<Artist> artists) {
        Interval shortestInterval = null;
        Artist shortestPlaying = null;

        for (Artist artist : artists) {
            Interval interval = new Interval(artist.getShowStart(), artist.getShowEnd());
            if (shortestInterval == null || interval.toDurationMillis() < shortestInterval.toDurationMillis()) {
                shortestInterval = interval;
                shortestPlaying = artist;
            }
        }
        return shortestPlaying;
    }

    public void sortByArtistName(List<Artist> artists) {
        Comparator<Artist> comparator = new Comparator<Artist>() {
            public int compare(Artist a1, Artist a2) {
                return a1.getArtistName().compareTo(a2.getArtistName());
            }
        };

        Collections.sort(artists, comparator);
    }

    public void sortByPlaytime(List<Artist> artists) {
        Comparator<Artist> comparator = new Comparator<Artist>() {
            public int compare(Artist a1, Artist a2) {
                Interval interval1 = new Interval(a1.getShowStart(), a1.getShowEnd());
                Interval interval2 = new Interval(a2.getShowStart(), a2.getShowEnd());

                //Ascending
                //return (int) interval1.toDurationMillis() - (int) interval2.toDurationMillis();

                //Descending
                return (int) interval2.toDurationMillis() - (int) interval1.toDurationMillis();
            }
        };

        Collections.sort(artists, comparator);

    }
}
