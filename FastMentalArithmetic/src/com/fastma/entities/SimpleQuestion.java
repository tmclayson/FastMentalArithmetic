package com.fastma.entities;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SimpleQuestion extends Question<Double> {

	// particularly useful as it is a) easier to understand, and b) removes any extra 0s at the end. 
	private static final DecimalFormat decimalFormat = new DecimalFormat("#.########");
	
	private static String df(double value) {
		return decimalFormat.format(value);
	}
	
//	private List<Double> answerChoices = new ArrayList<>();
//	private Double answer;
//	private Double yourAnswer;
	
	public SimpleQuestion(int qNumber, String question, String answer, List<Double> answerChoices) {
		super(qNumber, question, answer, answerChoices);	
	}	
	
	public static SimpleQuestion generateSimpleAdd(int questionNum) {

		List<Double> answerChoices = new ArrayList<>();		
		String question;
		Double answer;
		
		// generate random integrals in the range of 4 to 103
		int num1 = getRandIntBetween(4, 103);
		int num2 = getRandIntBetween(4, 103);

		int exponent1 = getRandIntBetween(0, 3);
		double divisor1 = Math.pow(10.0, exponent1); // Math.pow(base, exponent)

		int exponent2 = getRandIntBetween(-1, 1);
		double divisor2 = divisor1 * Math.pow(10.0, exponent2); // get divisor, second close to first.

		double value1 = num1 / divisor1; // get real values for numbers.
		double value2 = num2 / divisor2; // get real values for numbers.

		if (getRandIntBetween(0, 1) == 0) {
			num1 = -num1; // numbers can be positive or negative.
		}
		if (getRandIntBetween(0, 1) == 0) {
			num2 = -num2; // numbers can be positive or negative.
		}

		int addOrSub = getRandIntBetween(0, 1);
		// determine unknown
		int unknLoc = getRandIntBetween(0, 2);

		if (addOrSub == 1) { // addition;

			if (unknLoc == 0) { // ... + num1 = num2
				answer = value2 - value1;
				question = "... + " + df(value1) + " = " + df(value2);
			} else if (unknLoc == 1) { // value1 + ... = value2
				answer = value2 - value1;
				question = df(value1) + " + ... " + "= " + df(value2);
			} else { // value1 + value2 = ...
				answer = value1 + value2;
				question = df(value1) + " + " + df(value2) + " = ...";
			}
		} else { // subtraction

			if (unknLoc == 0) { // ... - value1 = value2
				answer = value2 + value1;
				question = "... - " + df(value1) + " = " + df(value2);
			} else if (unknLoc == 1) { // value1 - ... = value2
				answer = value1 - value2;
				question = df(value1) + " - ... " + " = " + df(value2);
			} else { // value1 - value2 = ...
				answer = value1 - value2;
				question = df(value1) + " - " + df(value2) + " = ...";
			}
		}

		answerChoices = returnSimple(answer, divisor1);	

		return new SimpleQuestion(questionNum, question, answer.toString(), answerChoices);	
	}

	private static List<Double> returnSimple(double answer, double divisor) {

		List<Double> answers = new ArrayList<>(4); // this just sets the initial capacity
		// double[] answersArray = new double[4];
		double bogAnswer = 0; // saves bogus answer
		double divisor1 = Math.pow(10.0, getRandIntBetween(1, 2));

		// TODO - check how many iterations this goes through.
		for (int i = 0; i < 4; ++i) {

			bogAnswer = 0;

			// C++ find() returns an iterator to the first element in the range [first,last)
			// that compares equal to val. If no such element is found, the function returns
			// last.
			// so if bogAnswer cannot be found in the answers list
			// (find(answers.begin(), answers.end(), bogAnswer) != answers.end())
			// returns answers.end() if the element cannot be found
			// therefore, the loop continues if the element can be found
			while ((bogAnswer == 0) || answers.contains(bogAnswer) || (bogAnswer == answer)) { // answer already
																									// input

				int bogAnswerType = getRandIntBetween(0, 4);
				divisor1 = Math.pow(10.0, getRandIntBetween(1, 2));

				if ((bogAnswerType == 0)
						&& ((answer < 0.1 && answer > 0.0) || (answer > -0.1 && answer < 0.0))) {
					if (getRandIntBetween(0, 1) == 0) {
						bogAnswer = (answer + (getRandIntBetween(1, 5)) / divisor) / divisor1;
					} else {
						bogAnswer = -(answer + (getRandIntBetween(1, 9)) / divisor) / divisor1;
					}
				} else if (bogAnswerType == 1
						&& ((answer < 0.1 && answer > 0.0) || (answer > -0.1 && answer < 0.0))) {
					if (getRandIntBetween(0, 1) == 0) {
						bogAnswer = (answer - (getRandIntBetween(1, 5)) / divisor) / divisor1;
					} else {
						bogAnswer = -(answer - (getRandIntBetween(1, 5)) / divisor) / divisor1;
					}
				} else if (bogAnswerType == 2) { // induce in error.
					if (getRandIntBetween(0, 2) == 0
							&& ((answer < 0.1 && answer > 0.0) || (answer > -0.1 && answer < 0.0))) {
						if (getRandIntBetween(0, 1) == 0) {
							bogAnswer = answer / divisor1;
						} else {
							bogAnswer = -answer / divisor1;
						}
					} else if (getRandIntBetween(0, 1) == 0
							&& ((answer < 0.1 && answer > 0.0) || (answer > -0.1 && answer < 0.0))) {
						if (getRandIntBetween(0, 1) == 0) {
							bogAnswer = answer * divisor1;
						} else {
							bogAnswer = -answer * divisor1;
						}
					} else if (getRandIntBetween(0, 2) == 0) { // so that it doesn't occur too often
						bogAnswer = -answer;
					}

				} else if (bogAnswerType == 3) {
					if (getRandIntBetween(0, 1) == 0) {
						bogAnswer = answer + (getRandIntBetween(1, 5)) / divisor;
					} else {
						bogAnswer = -(answer + (getRandIntBetween(1, 5)) / divisor);
					}
				} else {
					if (getRandIntBetween(0, 1) == 0) {
						bogAnswer = answer - (getRandIntBetween(1, 5)) / divisor;
					} else {
						bogAnswer = -(answer - (getRandIntBetween(1, 5)) / divisor);
					}
				}
			}

			answers.add(bogAnswer);

		}

		int corrPos = getRandIntBetween(0, 3); // position between 1 and 4
		answers.set(corrPos, answer);

		return answers;
	}

	public static SimpleQuestion generateSimpleMulti(int questionNum) {

		int multOrDiv = 0;
		int unknLoc = 0;

		List<Double> answerChoices = new ArrayList<>(4);
		
		String question;
		Double answer;
		// get two values for numbers to compute.
		int num1 = getRandIntBetween(2, 31);
		int num2 = (num1 > 12) ? getRandIntBetween(2, 12) : getRandIntBetween(2, 31);

		int exponent1 = getRandIntBetween(0, 2);
		double divisor1 = Math.pow(10.0, exponent1);

		int exponent2 = getRandIntBetween(-1, 1);
		double divisor2 = divisor1 * Math.pow(10.0, exponent2); // get divisor, second close to first.

		double value1 = num1 / divisor1; // get real values for numbers.
		double value2 = num2 / divisor2; // get real values for numbers.

		if (getRandIntBetween(0, 1) == 0) {
			value1 = -value1;
		}

		if (getRandIntBetween(0, 1) == 0) {
			value2 = -value2;
		}

		multOrDiv = getRandIntBetween(0, 1);
		// determine unknown
		unknLoc = getRandIntBetween(0, 2);

		if (multOrDiv == 1) { // multiplication

			if (unknLoc == 0) { // ... x num1 = num2
				answer = value1;
				value1 = value2;
				value2 = answer * value1;
				question = "... x " + df(value1) + " = " + df(value2);
			} else if (unknLoc == 1) { // value1 x ... = value2
				answer = value2;
				value2 = value1 * answer;
				question = df(value1) + " x ... " + "= " + df(value2);
			} else { // value1 x value2 = ...
				answer = value1 * value2;
				question = df(value1) + " x " + df(value2) + " = ...";
			}
		}

		else { // division

			if (unknLoc == 0) { // ... / value1 = value2
				answer = value1 * value2;
				question = "... : " + df(value1) + " = " + df(value2);
			} else if (unknLoc == 1) { // value1 : ... = value2 (value1 = value2*...)
				answer = value2;
				value2 = value1;
				value1 = value2 * answer;
				question = df(value1) + " : ... " + " = " + df(value2);
			} else { // value1 / value2 = ...
				answer = value2;
				value2 = value1;
				value1 = value2 * answer;
				question = df(value1) + " : " + df(value2) + " = ...";
			}
		}

		answerChoices = returnSimple(answer, divisor1);	
		
		return new SimpleQuestion(questionNum, question, answer.toString(), answerChoices);	
	}

	@Override
	public String getAnswerChoiceAsString(int index) {
		return df(this.getAnswerChoices().get(index));		
	}

}
