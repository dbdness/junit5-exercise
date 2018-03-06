public enum Stage {
    //The stage the artist is playing at
    Orange(9),
    Arena(8),
    Avalon(7),
    Apollo(6),
    Pavilion(5),
    Countdown(4),
    Rising(3),
    East(2),
    Gloria(1);

    //Stage popularity rating. The higher the rating, the bigger the stage.
    private int popularity;

    Stage(int popularity) {
        this.popularity = popularity;
    }

    public int getPopularity() {
        return popularity;
    }
}
