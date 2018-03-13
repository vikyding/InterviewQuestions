package amazonOA;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengchending on 3/11/18.
 */
public class Movie{
    private int id;
    private float rate;
    private List<Movie> similiarMovies;

    public Movie(int id, float rate){
        this.id = id;
        this.rate = rate;
        similiarMovies = new ArrayList<>();
    }

    public int getId(){
        return this.id;
    }
    public float getRate(){
        return rate;
    }

    public List<Movie> getSimiliarMovies() {
        return similiarMovies;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public void setSimiliarMovies(List<Movie> similiarMovies) {
        this.similiarMovies = similiarMovies;
    }
}