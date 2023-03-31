package rockpaperscissors;
import java.util.*;
import java.io.*;

public class Main {
    public static class RockPaperScissors {
        private String userName = "";
        private int userRating = 0;
        private String userOption = "";
        private String computerOption = "";
        private int computerOptionIndex = 0;
        private String[] gameOptions = {"rock", "paper", "scissors"};
        public static Scanner scanner = new Scanner(System.in);
        public String lostResponse = "Sorry, but the computer chose " + this.computerOption;
        public String drawResponse = "There is a draw " + this.computerOption;
        public String winResponse = "Well done. The computer chose " + this.computerOption + " and failed";
        public static final File ratingFile = new File("rating.txt");
        public static final int DRAW_PTS = 50;
        public static final int WIN_PTS = 100;
        
        public String getUserName() {
            return this.userName;
        }

        public int getUserRating() {
            return this.userRating;
        }
        
        public String[] getGameOptions() {
            return this.gameOptions;
        }
    
        public String getUserOption() {
            return this.userOption;
        }

        public String getCompOption() {
            return this.computerOption;
        }

        public int getCompOptionIndex() {
            return this.computerOptionIndex;
        }
        public void setUserName(String name) {
            this.userName = name;
        }

        public void setUserRating(int rating) {
            this.userRating = rating;
        }
        
        public void addToUserRating(int rating) {
            this.userRating += rating;
        }

        public void setGameOptions(String[] options) {
            this.gameOptions = options;
        }
        
        public void setUserOption(String option) {
            this.userOption = option;
        }

        public void setCompOption(String option) {
            this.computerOption = option;
        }

        
        public boolean testForValidInput(String option) {
            String[] gameOptions = this.getGameOptions();
            int gameOptLen = gameOptions.length;
            for (int i = 0; i < gameOptLen; i++) {
                if (option.equals(gameOptions[i])) {
                    return true;
                } 
            }
            return false;
        }
        
        public void chooseCompOption() {
            String[] computerOptions = this.getGameOptions();
            int length = computerOptions.length;
            Random random = new Random();
            int randInt = random.nextInt(length);
            this.computerOptionIndex = randInt;
            this.computerOption = computerOptions[randInt];
            
        }
        public boolean testForDraw() {
            if(this.getUserOption().equals(this.getCompOption())) {
                return true;
            }
            return false;
        }

        public boolean CompLoses() {
            String userOption = this.getUserOption();
            int computerOptionIndex = this.getCompOptionIndex();
            String[] gameOptions = this.getGameOptions();
            int len = gameOptions.length;
            int firstHalfLen = len / 2;
            String[] firstHalf = new String[firstHalfLen];
            boolean includedInFirstHalf = false;
            for (int i = 0; i < len; i++) {
                // get the index value of the user option
                if (userOption.equals(gameOptions[i])) {
                    // counter needs to be zero index based 
                    int counter = computerOptionIndex + 1;
                    for(int j = 0; j < firstHalfLen; j++) {
                        if (counter >= len) {
                            counter = 0;
                        }
                        if (userOption.equals(gameOptions[counter]) ) {
                               includedInFirstHalf = true;
                               break;
                        };
                        
                        counter++;
                    }
                }
            }
            return includedInFirstHalf;

        }
        
        public String drawResponse() {
            String statement = "There is a draw (" + this.computerOption + ")";
            this.addToUserRating(DRAW_PTS);
            return statement;
        }

        public String lostResponse() {
            String statement = "Sorry, but the computer chose " + this.computerOption;
            return statement;
        }

        public String winResponse() {
            String statement = "Well done. The computer chose " + this.computerOption + " and failed";
            this.addToUserRating(WIN_PTS);
            return statement;
        }
        
        public String ratingResponse() {
            String statement = "You rating: " + this.userRating;
            return statement;
        }
        public void readRatings() {
            try (Scanner scanner = new Scanner(ratingFile)) {
                int row = 0;
                int col = 0;
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    String[] lineArr = line.split(" ");
                    String name = lineArr[0];
                    int initialRating = Integer.parseInt(lineArr[1]);
                    if (name.equals(this.getUserName())) {
                        this.setUserRating(initialRating);
                        break;
                    }
                    row++;
                }
                if (this.getUserRating() == 0) {
                    this.setUserRating(350);
                }
            } catch (FileNotFoundException e) {
                System.out.println("No file found: " + ratingFile);
            }
        }
        
        public void printIntro() {
            System.out.print("Enter your name: ");
            String userName = scanner.nextLine();
            this.setUserName(userName);
            System.out.println("Hello, " + userName);
        }

        public void obtainOptions() {
            String options = scanner.nextLine();
            if (options.isEmpty() == false) {
                this.setGameOptions(options.split(","));
            }
            System.out.println("Okay, let's start");
        }
     
        public void printResults() {
            if(this.testForDraw()) {
                System.out.println(this.drawResponse());
            } else if (this.CompLoses()) {
                System.out.println(this.winResponse());
            } else {
                System.out.println(this.lostResponse());
            }

        }
        
        public void execute() {
            String[] gameOptions = this.getGameOptions();
            this.printIntro();
            this.readRatings();
            this.obtainOptions();
            while (true) {
                String option = scanner.nextLine();
                this.setUserOption(option);
                
                if (option.equals("!rating")) {
                    System.out.println(this.ratingResponse());
                } else if (option.equals("!exit")) {
                    System.out.println("Bye!");
                    break;
                } else if (this.testForValidInput(option)) {
                    this.chooseCompOption();
                    this.printResults();    
                } else {
                    System.out.println("Invalid input");
                }                
                   
            }
        }
        
    }
    public static void main(String[] args) {
        RockPaperScissors rockPaperScissors = new RockPaperScissors();
        rockPaperScissors.execute();                        
    }
}
