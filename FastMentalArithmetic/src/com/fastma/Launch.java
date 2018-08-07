package com.fastma;

import java.util.Scanner;

import com.fastma.entities.Fraction;
import com.fastma.entities.FractionalQuestion;
import com.fastma.entities.Question;
import com.fastma.entities.SimpleQuestion;
import com.fastma.util.Utilities;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;

public class Launch {

	public static void main(String[] args) {
		
		// TODO - make numQuestions, testTime(?) user defined
		// TODO - keep track of a user's results over time, noting which type of question they struggle with most
		//		- first by outputting to and from a file, then to a database
		// TODO - statistics for each type of question		
		// TODO - have ability to create a user profile (sign in with google etc?)
		// TODO - have user be able to choose percentage of questions of each type that appear, so that they can focus on their weaknesses
		// TODO - include training materials
		int numQuestions = 80;
		long testDuration = 480;
		int questionNum = 1;				
		int points = 0;		
		
		//DatabaseConnectionProperties dbConnection;
				
		Question<?>[] allQuestions = new Question<?>[numQuestions];
		
		Instant testStartDateTime = Instant.now();
		Instant testFinishDateTime = testStartDateTime.plusSeconds(testDuration);
		
		Scanner sc = new Scanner(System.in);
		
		while ((Instant.now().isBefore(testFinishDateTime)) && (questionNum <= numQuestions)) {
			
			// Duration class has more useful methods than simply returning as a long the number of seconds
			// Duration timeElapsed = Duration.between(testStartDateTime, LocalDateTime.now());
			Duration timeRemaining = Duration.between(Instant.now(), testFinishDateTime);
			// long secondsElapsed = ChronoUnit.SECONDS.between(LocalDateTime.now(), testStartDateTime);			
			// long secondsRemaining = LocalDateTime.now().until(testFinishDateTime, ChronoUnit.SECONDS);
			// long secondsRemaining = ChronoUnit.SECONDS.between(testFinishDateTime, LocalDateTime.now());								
			System.out.println("\n" + "Question " + questionNum + ")" + "\n");							
			System.out.println("Time Remaining: " + timeRemaining.toMinutes() + ":" + timeRemaining.toSecondsPart() + "\n"); 
			
			int questionType = Utilities.getRandIntBetween(0, 3); //assuming 4 types of question.
			
			Question<?> currentQuestion;
			
			if (questionType == 0) { //call simple addition/subtraction
				currentQuestion = SimpleQuestion.generateSimpleAdd(questionNum);				
			}
			else if (questionType == 1) { //call fraction addition/subtraction
				currentQuestion = FractionalQuestion.generateFractionalAdd(questionNum);			
			}
			else if (questionType == 2) { //call simple multiplication/division
				currentQuestion = SimpleQuestion.generateSimpleMulti(questionNum);				
			}
			else { //call fraction multiplication/division
				currentQuestion = FractionalQuestion.generateFractionalMulti(questionNum);				
			}
		
			DecimalFormat decimalFormat = new DecimalFormat("#.########");
			int answerChoiceNumber;
			answerChoiceNumber = 1; // need to reset counter each time a new question is generated. 

				//currentQuestion.getAnswerChoices().forEach(q -> System.out.println(answerChoiceNumber + ") " + decimalFormat.format(q)));
				if(currentQuestion instanceof SimpleQuestion) {					
					currentQuestion.getAnswerChoices().forEach(d -> decimalFormat.format((Double)d));
					//System.out.println(answerChoiceNumber + ") " + decimalFormat.format(currentQuestion.getAnswer()));
				}
				else {	
					currentQuestion.getAnswerChoices().forEach(f -> ((Fraction)f).toString());
					//System.out.println(answerChoiceNumber + ") " + currentQuestion.getAnswer().toString());
				}					
				//answerChoiceNumber++;
			
			//System.out.println("Your answer: type number 1-4, then ENTER: ");
			Instant questionStartDateTime = Instant.now();

			// stay in the while loop if the user has not input a single digit between 1 and 4
			int userAnswerChoice;
			do {
			    System.out.println("Your answer: type number 1-4, then ENTER: ");
			    while (!sc.hasNextInt()) {
			        System.out.println("That's not a number!");
			        sc.next(); // this is important!
			    }
			    userAnswerChoice = sc.nextInt();
			} while (userAnswerChoice < 1 || userAnswerChoice > 4);		
			
			currentQuestion.setTimeOnQu(Duration.between(questionStartDateTime, Instant.now()).getSeconds());								
			
			if (currentQuestion.getTimeOnQu()>timeRemaining.getSeconds()) {				
				System.out.println("You ran out of time before answering. Point will not be counted towards total." + "\n");
			}
			else {
				if (currentQuestion.getAnswerChoices().get(userAnswerChoice-1).equals(currentQuestion.getAnswer())) {			
					points++;					
				}
				else {
					points--;
				}				
			}				
			
			allQuestions[questionNum] = currentQuestion;
			++questionNum;						
		}

		System.out.println("Total number of points: " + points);

		if (points > 53) {
			System.out.println("You probably would have passed the real test. Well done!!!" + "\n");
		}
		else {
			System.out.println("You would have failed the real test, try again..." + "\n"); 
		}	

//		String leftAlignFormat = "| %-15s | %-4d |%n";
//
//		System.out.format("+-----------------+------+%n");
//		System.out.format("| Column name     | ID   |%n");
//		System.out.format("+-----------------+------+%n");
//		for (int i = 0; i < 5; i++) {
//		    System.out.format(leftAlignFormat, "some data" + i, i * i);
//		}
//		System.out.format("+-----------------+------+%n");
		
//		System.out.printf(%10s, s); prints s taking up 10 characters
		
//		System.out.format(" %1$9s )", "qNumber");
//		System.out.format(" %1$27s", "Question");
//		System.out.format(" %1$13s", "Corr answer");	
//		System.out.format(" %1$13s", "Your answer");
//		System.out.format(" %1$7s", "Rt/Wg");
//		System.out.println("Time spent" + "\n");
//
//		//Formatter formatter = new Formatter();
//		
//		for (Question<?> questionInfo : allQuestions) {
//			
//			System.out.format(" %1$9d )", questionInfo.getqNumber());
//			System.out.format(" %1$27s", questionInfo.getQuestion());
//			System.out.format(" %1$13f", ((SimpleQuestion) questionInfo).getAnswer());	
//			System.out.format(" %1$13f", ((SimpleQuestion) questionInfo).getYourAnswer());
//			System.out.format((questionInfo.isAnswerCorrect() ? "R" : "W") + " %1$7d", questionInfo.getTimeOnQu());
//			System.out.println();
//		}
				
		System.out.println("Press any key then Enter to exit");
		// BufferedReader is synchronised, so read operations on a BufferedReader can be safely done from multiple threads. 		
		// BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		String dontExit = sc.nextLine(); 
		sc.close();
		
	}
}
