package com.fastma;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import com.fastma.entities.Fraction;
import com.fastma.entities.FractionalQuestion;
import com.fastma.entities.Question;
import com.fastma.entities.SimpleQuestion;
import com.fastma.util.Utilities;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;

public class Launch {

	private static Question<?>[] askQuestions(int numQuestions, long testDuration) {

		int questionNum = 0;
		int points = 0;
		int questionType;

		// DatabaseConnectionProperties dbConnection;

		Question<?>[] allQuestions = new Question<?>[numQuestions];
		Question<?> currentQuestion;

		Instant testStartDateTime = Instant.now();
		Instant testFinishDateTime = testStartDateTime.plusSeconds(testDuration);

		Scanner sc = new Scanner(System.in);
		DecimalFormat decimalFormat = new DecimalFormat("#.########");
		
		while ((Instant.now().isBefore(testFinishDateTime)) && (questionNum < numQuestions)) {
			// Duration class has more useful methods than simply returning as a long the
			// number of seconds
			// Duration timeElapsed = Duration.between(testStartDateTime,
			// LocalDateTime.now());
			Duration timeRemaining = Duration.between(Instant.now(), testFinishDateTime);
			// long secondsElapsed = ChronoUnit.SECONDS.between(LocalDateTime.now(),
			// testStartDateTime);
			// long secondsRemaining = LocalDateTime.now().until(testFinishDateTime,
			// ChronoUnit.SECONDS);
			// long secondsRemaining = ChronoUnit.SECONDS.between(testFinishDateTime,
			// LocalDateTime.now());
			System.out.println("\n" + "Question " + (questionNum + 1) + ")" + "\n");
			System.out.println(
					"Time Remaining: " + timeRemaining.toMinutes() + ":" + timeRemaining.toSecondsPart() + "\n");

			questionType = Utilities.getRandIntBetween(0, 3); // assuming 4 types of question.

			if (questionType == 0) {
				currentQuestion = SimpleQuestion.generateSimpleAdd(questionNum);
			} else if (questionType == 1) {
				currentQuestion = FractionalQuestion.generateFractionalAdd(questionNum);
			} else if (questionType == 2) {
				currentQuestion = SimpleQuestion.generateSimpleMulti(questionNum);
			} else {
				currentQuestion = FractionalQuestion.generateFractionalMulti(questionNum);
			}
			
			System.out.println(currentQuestion.getQuestion() + "\n");
							
			final AtomicInteger answerChoiceNumber = new AtomicInteger(1);

			if (currentQuestion instanceof SimpleQuestion) {
				currentQuestion.getAnswerChoices().forEach(d -> System.out.println(answerChoiceNumber.getAndAdd(1) + ") " + decimalFormat.format((Double) d)));
			} else {
				currentQuestion.getAnswerChoices().forEach(f -> System.out.println(answerChoiceNumber.getAndAdd(1) + ") " + ((Fraction) f).toString()));
			} 

			Instant questionStartInstant = Instant.now();

			// stay in the while loop if the user has not input a single digit between 1 and
			// 4
			int userAnswerChoice;
			do {
				System.out.println("\n" + "Your answer: type number 1-4, then ENTER: ");
				while (!sc.hasNextInt()) {
					System.out.println("That's not a number!");
					sc.next(); // this is important!
				}
				userAnswerChoice = sc.nextInt();
			} while (userAnswerChoice < 1 || userAnswerChoice > 4);

			currentQuestion.setTimeOnQu(Duration.between(questionStartInstant, Instant.now()).getSeconds());

			if (currentQuestion.getTimeOnQu() > timeRemaining.getSeconds()) {
				System.out.println(
						"You ran out of time before answering. Point will not be counted towards total." + "\n");
			} else {
				System.out.println("answerChoice: " + currentQuestion.getAnswerChoices().get(userAnswerChoice - 1).toString());
				System.out.println("answer: " + currentQuestion.getAnswer());
				
				if (currentQuestion.getAnswer().equals(currentQuestion.getAnswerChoices().get(userAnswerChoice - 1).toString())) {
					points++;
				} else {
					points--;
				}
			}

			allQuestions[questionNum++] = currentQuestion;			
		}
		
		System.out.println("Total number of points: " + points);

		if (points > 53) {
			System.out.println("You probably would have passed the real test. Well done!!!" + "\n");
		} else {
			System.out.println("You would have failed the real test, keep practising!" + "\n");
		}		

		return allQuestions;
	}

	public static void main(String[] args) {

		// TODO - make numQuestions, testTime(?) user defined
		// TODO - keep track of a user's results over time, noting which type of
		// question they struggle with most
		// - first by outputting to and from a file, then to a database
		// TODO - statistics for each type of question
		// TODO - have ability to create a user profile (sign in with google etc?)
		// TODO - have user be able to choose percentage of questions of each type that
		// appear, so that they can focus on their weaknesses
		// TODO - include training materials
		// TODO - consider creating a object the holds each all the questions asked in each test, as well statistics on the points, types of questions etc.
		int numQuestions = 5;
		long testDuration = 480;
		// contains arrays containing the questions asked and the responses given by the user for each practice session 
		List<Question<?>[]> SessionTestResults = new ArrayList<>(); 
		
		Scanner sc = new Scanner(System.in);
		String keepPractising = "y";
		do {
			SessionTestResults.add(askQuestions(numQuestions, testDuration));
			System.out.println("Would you like to keep practising? (y/n)");
			while (!sc.hasNext("[yn]")) {
				System.out.println("Please enter only 'y' for yes, or 'n' for no");
				sc.next(); // this is important!
			}
			keepPractising = sc.nextLine();
		} while (keepPractising.equals("y"));

		//sc.next
		// String leftAlignFormat = "| %-15s | %-4d |%n";
		//
		// System.out.format("+-----------------+------+%n");
		// System.out.format("| Column name | ID |%n");
		// System.out.format("+-----------------+------+%n");
		// for (int i = 0; i < 5; i++) {
		// System.out.format(leftAlignFormat, "some data" + i, i * i);
		// }
		// System.out.format("+-----------------+------+%n");

		// System.out.printf(%10s, s); prints s taking up 10 characters

		// System.out.format(" %1$9s )", "qNumber");
		// System.out.format(" %1$27s", "Question");
		// System.out.format(" %1$13s", "Corr answer");
		// System.out.format(" %1$13s", "Your answer");
		// System.out.format(" %1$7s", "Rt/Wg");
		// System.out.println("Time spent" + "\n");
		//
		// //Formatter formatter = new Formatter();
		//
		// for (Question<?> questionInfo : allQuestions) {
		//
		// System.out.format(" %1$9d )", questionInfo.getqNumber());
		// System.out.format(" %1$27s", questionInfo.getQuestion());
		// System.out.format(" %1$13f", ((SimpleQuestion) questionInfo).getAnswer());
		// System.out.format(" %1$13f", ((SimpleQuestion)
		// questionInfo).getYourAnswer());
		// System.out.format((questionInfo.isAnswerCorrect() ? "R" : "W") + " %1$7d",
		// questionInfo.getTimeOnQu());
		// System.out.println();
		// }

		// BufferedReader is synchronised, so read operations on a BufferedReader can be
		// safely done from multiple threads.
		// BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		if(keepPractising.equals("n")) {
			System.out.println("\n" + "See you next time!");
			sc.close();
		}
		

	}

}
