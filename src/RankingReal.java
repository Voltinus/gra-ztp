import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class RankingReal implements Ranking {
	public ArrayList<String[]> ranking = new ArrayList<String[]>();
	
	private static RankingReal instance = null;
	public static RankingReal getInstance() throws FileNotFoundException, ClassNotFoundException, IOException {
		if(instance == null) {
			instance = new RankingReal();
		}
		
		return instance;
	}
	
	RankingReal() throws FileNotFoundException, IOException, ClassNotFoundException {
		File f = new File("ranking.txt");
		if(f.exists()) {
			load();
		} else {
			save("Miro", 1500);
			save("Miro", 2400);
			save("Miro", 1400);
		}
	}
	
	public int getScoreCount() throws ClassNotFoundException, IOException {
		return ranking.size();
	}
	
	public int getLowestScore() throws ClassNotFoundException, IOException {
		if(getScoreCount() == 0) return 0;
		String[] lowest = ranking.get(0);
		
		for(String[] i : ranking) {
			if(Integer.parseInt(i[1]) < Integer.parseInt(lowest[1])) {
				lowest = i;
			}
		}
		
		return Integer.parseInt(lowest[1]);
	}
	
	private void sort() {
		ArrayList<String[]> newRanking = new ArrayList<String[]>();
		
		String[] max;
		while(ranking.size() > 0) {
			max = ranking.get(0);
			for(String[] i : ranking) {
				if(Integer.parseInt(i[1]) > Integer.parseInt(max[1])) {
					max = i;
				}
			}
			newRanking.add(max);
			ranking.remove(max);
		}
		
		ranking = newRanking;
	}
	
	
	public void save(String nick, int score) throws IOException, ClassNotFoundException {
		File fout = new File("ranking.txt");
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	 
		ranking.add(new String[] { nick, ""+score });
		sort();
		
		if(ranking.size() > 10) {
			ranking.remove(10);
		}
		
		for(String[] item : ranking){
			bw.write(item[0] + "," + item[1]);
			bw.newLine();
		}
	 
		bw.close();
	}
	
	
	public ArrayList<String[]> load() throws IOException, ClassNotFoundException {
		ranking = new ArrayList<String[]>();
		
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("ranking.txt"));
			String line = reader.readLine();
			while(line != null) {
				String[] splitted = line.split(",");
				ranking.add(splitted);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ranking;
	}
}
