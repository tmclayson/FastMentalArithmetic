package com.fastma.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Question<T> {
	// minimise mutability and accessibility by declaring fields as final where
	// possible.
	private final int questionNumber;
	private final String question;	
	private final List<T> answerChoices;
	private final String answer;
	
	private long timeOnQu;
	private int userAnswerChoiceListIndex;

	/**
	 * @param min
	 * @param max
	 *            The min and max limits are inclusive in this method.
	 */

	static protected int getRandIntBetween(int min, int max) {
		// Math.random, a static method which generates doubles evenly distributed
		// between 0 (inclusive) and 1 (exclusive).
		return (int) ((Math.random() * ((max - min) + 1)) + min);
	}

	/** The lower bound is included, but the upper bound is excluded. */
	static protected long randomLongInRange(long lower, long upper) {
		// In Effective Java, Joshua Bloch recommends ThreadLocalRandom
		// for most use cases (even for single-threaded code).
		// note the static factory method for getting an object; there's no constructor,
		// and there's no need to pass a seed
		ThreadLocalRandom generator = ThreadLocalRandom.current();
		return generator.nextLong(lower, upper);
	}

	public Question(int questionNumber, String question, String answer, List<T> answerChoices) {
		Objects.requireNonNull(answerChoices, "answerChoices List is null");
		this.questionNumber = questionNumber;
		this.question = question;
		this.timeOnQu = 0;
		this.answer = answer;
		this.answerChoices = answerChoices;
	}

	// private void validateState() throws IllegalArgumentException {
	// if (question.equals("")) {
	// throw new IllegalArgumentException("User name must have content.");
	// }
	// }

	public int getqNumber() {
		return questionNumber;
	}

	public String getQuestion() {
		return question;
	}

	public long getTimeOnQu() {
		return timeOnQu;
	}

	public void setTimeOnQu(long seconds) {
		this.timeOnQu = seconds;
	}

	public List<T> getAnswerChoices() {
		return answerChoices;
	}

	public String getAnswer() {
		return answer;
	}

	public int getYourAnswer() {
		return userAnswerChoiceListIndex;
	}

	public void setYourAnswer(int userAnswerChoiceListIndex) {
		this.userAnswerChoiceListIndex = userAnswerChoiceListIndex;
	}

	public Boolean isAnswerCorrect() {
		return answer.equals(answerChoices.get(userAnswerChoiceListIndex).toString());
	}

	@Override
	public String toString() {
		return "Question [questionNumber=" + questionNumber + ", question=" + question + ", timeOnQu=" + timeOnQu
				+ ", answerChoices=" + answerChoices + ", answer=" + answer + ", userAnswerChoiceListIndex=" + userAnswerChoiceListIndex
				+ "]";
	}
}
