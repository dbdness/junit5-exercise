import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Artist {
    private DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");

    //The stage the artist is playing at
    public enum Stage {
        Orange(9),
        Arena(8),
        Avalon(7),
        Apollo(6),
        Pavilion(5),
        Countdown(4),
        Rising(3),
        East(2),
        Gloria(1);

        //Popularity rating
        private int popularity;

        Stage(int popularity) {
            this.popularity = popularity;
        }

        public int getPopularity() {
            return popularity;
        }
    }

    //Name of the artist
    private String artistName;
    //What stage the artist is playing at
    private Stage stage;
    //When the show is expected to start
    private DateTime showStart;
    //When the show is expected to end
    private DateTime showEnd;

    public Artist(String artistName, Stage stage, String showStart, String showEnd) {
        this.artistName = artistName;
        this.stage = stage;
        this.showStart = dtf.parseDateTime(showStart);
        this.showEnd = dtf.parseDateTime(showEnd);
    }

    public String getArtistName() {
        return artistName;
    }

    public Stage getStage() {
        return stage;
    }

    public DateTime getShowStart() {
        return showStart;
    }

    public DateTime getShowEnd() {
        return showEnd;
    }

    @Override
    public String toString() {
        return String.format("%s|%s|%s", artistName, stage, showStart.toString(dtf));
    }
}
