import com.google.gson.JsonSyntaxException;

import java.io.FileNotFoundException;
import java.util.IllegalFormatException;
import java.util.List;

/**
 * This is an interface for handling/organizing all the different artist playing at Roskilde Festival.
 */
public interface FestivalArtistManager {

    /**
     * Reads the specified JSON file, and returns proper Artist objects from it.
     *
     * @param jsonFile JSON file containing artist data.
     * @return a list containing Artist objects.
     * @throws FileNotFoundException if the specified JSON file can't be found.
     * @throws JsonSyntaxException   if the specified JSON file contains syntax errors.
     */
    public List<Artist> getArtistsFromJson(String jsonFile) throws FileNotFoundException, JsonSyntaxException;

    /**
     * Returns all the artists playing on the specified day.
     *
     * @param date    Day to check.
     *                Date format is: dd/MM/yyyy
     *                Example: 04/07/2018
     * @param artists List of artists registered to play.
     * @return a list of artists playing on the specified day.
     * @throws IllegalArgumentException if the correct data format is not matched.
     */
    public List<Artist> getArtistsByDay(String date, List<Artist> artists) throws IllegalFormatException;

    /**
     * Returns all the artists playing on the specified stage.
     *
     * @param stage   Stage to check.
     * @param artists List of artists registered to play.
     * @return a list of artists playing on the specified stage.
     */
    public List<Artist> getArtistsByStage(Artist.Stage stage, List<Artist> artists);

    /**
     * Sorts the specified artists by popularity, based on the stage they are playing at.
     *
     * @param artists List of artists to be sorted.
     */
    public void sortArtistsByPopularity(List<Artist> artists);

    /**
     * Returns the artist with the longest playtime.
     *
     * @param artists List of artists registered.
     * @return The artist who plays for the longest time.
     */
    public Artist getLongestPlaying(List<Artist> artists);

    /**
     * Returns the artist with the shortest playtime.
     *
     * @param artists List of artists.
     * @return The artist who plays for the shortest time.
     */
    public Artist getShortestPlaying(List<Artist> artists);

    /**
     * Sorts the specified list of artists alphabetically by artist name.
     *
     * @param artists List of artists.
     */
    public void sortByArtistName(List<Artist> artists);

    /**
     * Sorts the specified list of artists by how long they play for.
     *
     * @param artists List of artists.
     */
    public void sortByPlaytime(List<Artist> artists);


}
