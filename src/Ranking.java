import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface Ranking {
	public int getScoreCount() throws ClassNotFoundException, IOException;
	public int getLowestScore() throws ClassNotFoundException, IOException;
	public void save(String nick, int score) throws IOException, ClassNotFoundException;
	public ArrayList<String[]> load() throws IOException, ClassNotFoundException;
}
