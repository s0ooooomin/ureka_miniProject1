package jdbc.dto;

public class MovieDTO {
	
	private int movie_id;
	private String movie_title;
	private String director;
	private int price;
	
	
	public int getMovie_id() {
		return movie_id;
	}
	public void setMovie_id(int movie_id) {
		this.movie_id = movie_id;
	}
	
	public String getMovie_title() {
		return movie_title;
	}
	public void setMovie_title(String movie_title) {
		this.movie_title = movie_title;
	}
	
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
	@Override
	public String toString() {
		return "MovieDto [movie_id=" + movie_id + ", movie_title=" + movie_title + ", director=" + director + ", price="
				+ price + "]";
	}
	
	
}
