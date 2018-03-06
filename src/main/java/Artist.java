import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Artist {
    private DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");

    //Name of the artist
    private String artistName;
    //What stage the artist is playing at
    private Stage stage;
    //When the show is expected to start
    private DateTime showStart;
    //When the show is expected to end
    private DateTime showEnd;

    /**
     * @param artistName Name of the artist.
     * @param stage      Stage the artist is playing on.
     * @param showStart  The time the show starts.
     *                   The required format is: dd/MM/yyyy HH:mm.
     *                   Example: 02/07/2018 15:00
     * @param showEnd    The time the show ends.
     *                   The required format is: dd/MM/yyyy HH:mm.
     *                   Example: 02/07/2018 16:30
     */
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
