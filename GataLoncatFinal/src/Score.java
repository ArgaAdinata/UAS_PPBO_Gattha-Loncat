import java.io.*;
public class Score {
    public void updateScores(int score) {
        int fileScore = getScore();
        if (score > fileScore) {
            fileScore = score;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("score.txt"))) {
            writer.write(String.valueOf(fileScore));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int getScore() {
        int fileScore = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("score.txt"))) {
            String score = reader.readLine();
            if (score != null) {
                fileScore = Integer.parseInt(score);
            }
        } catch (FileNotFoundException e) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("score.txt"))) {
                writer.write("0");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileScore;
    }
}