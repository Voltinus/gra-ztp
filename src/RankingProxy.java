import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class RankingProxy implements Ranking {
	RankingReal rankingReal;
	
	RankingProxy() throws FileNotFoundException, ClassNotFoundException, IOException {
		rankingReal = RankingReal.getInstance();
	}
	
	public int getScoreCount() throws ClassNotFoundException, IOException {
		return rankingReal.getScoreCount();
	}
	
	public int getLowestScore() throws ClassNotFoundException, IOException {
		if(getScoreCount() == 0) return 0;
		String[] lowest = rankingReal.ranking.get(0);
		
		for(String[] i : rankingReal.ranking) {
			if(Integer.parseInt(i[1]) < Integer.parseInt(lowest[1])) {
				lowest = i;
			}
		}
		
		return Integer.parseInt(lowest[1]);
	}

	@Override
	public void save(String nick, int score) throws IOException, ClassNotFoundException {
		rankingReal.save(nick, score);
	}

	@Override
	public ArrayList<String[]> load() throws IOException, ClassNotFoundException {
 		return rankingReal.load();
	}
}
