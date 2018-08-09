package com.fastma.entities;

import java.util.ArrayList;
import java.util.List;

public class FractionalQuestion extends Question<Fraction> {

	public FractionalQuestion(int questionNum, String question, String answer, List<Fraction> answerChoices) {
		super(questionNum, question, answer, answerChoices);
		// this.answer = answer;
		// this.answerChoices = answerChoices;
	}

	@Override
	public String getAnswerChoiceAsString(int index) {
		return this.getAnswerChoices().get(index).toString();				
	}
	
	public static FractionalQuestion generateFractionalAdd(int questionNum) {

		List<Fraction> answerChoices = new ArrayList<>();

		String question = null;
		Fraction answer;

		if (getRandIntBetween(0, 1) == 0) { // normal fraction additions and divisions.

			int numerator1 = 0;
			int numerator2 = 0;
			int ansNumerator = 0; // contains answer numerator
			int denominator1 = 0;
			int denominator2 = 0; // has to be computed from denominator1
			int ansDenominator = 0; // contains answer denominator
			int commonDivisor1 = 0;
			int commonDivisor2 = 0;
			double unknLoc = 0;

			// get the two commonDivisors
			commonDivisor1 = getRandIntBetween(2, 12);
			if (commonDivisor1 > 4) {
				commonDivisor2 = getRandIntBetween(2, 5);
			} else {
				commonDivisor2 = getRandIntBetween(2, 12);
			}

			numerator1 = getRandIntBetween(1, 7);

			while (numerator2 == 0 || numerator2 == numerator1) { // numerator1 and numerator2 must be different to
																	// avoid answer = 0
				numerator2 = getRandIntBetween(1, 7);
			}
			denominator1 = getRandIntBetween(1, 7);
			denominator2 = denominator1;

			if (getRandIntBetween(0, 1) == 0) {// not allowing minus signs to denominators (too ugly on output)
				numerator1 = -numerator1;
			}

			if (getRandIntBetween(0, 1) == 0) {
				numerator2 = -numerator2;
			}

			int oSnumerator1 = numerator1 * commonDivisor1; // on screen numbers
			int oSdenominator1 = denominator1 * commonDivisor1;
			int oSnumerator2 = numerator2 * commonDivisor2;
			int oSdenominator2 = denominator2 * commonDivisor2; // simplifiable to same denominator

			int multOrDiv = getRandIntBetween(0, 1);

			// bogus multiplier used in ansDenominator and ansNumerator to make the answer
			// less obvious, as in real test.
			int bogMult = getRandIntBetween(1, 5);

			ansDenominator = denominator1 * bogMult; // ansNumerator also has to be multiplied by bogMult.

			// determine unknown
			unknLoc = getRandIntBetween(0, 2);

			if (multOrDiv == 1) { // addition

				if (unknLoc == 0) { // ... + numerator1/denominator1 = numerator2/denominator2 =>
									// (numerator2/denominator2) - (numerator1/denominator1) = ...
					ansNumerator = (numerator2 - numerator1) * bogMult;
					question = "... + (" + oSnumerator1 + "/" + oSdenominator1 + ") = (" + oSnumerator2 + "/"
							+ oSdenominator2 + ")";

				} else if (unknLoc == 1) { // numerator1/denominator1 + ... = numerator2/denominator2 =>
											// (numerator2/denominator2) - (denominator1/numerator1) = ...
					ansNumerator = (numerator2 - numerator1) * bogMult;
					question = "(" + oSnumerator1 + "/" + oSdenominator1 + ") + ... = (" + oSnumerator2 + "/"
							+ oSdenominator2 + ")";

				} else { // numerator1/denominator1 + numerator2/denominator2 = ...
					ansNumerator = (numerator1 + numerator2) * bogMult;
					question = "(" + oSnumerator1 + "/" + oSdenominator1 + ") + (" + oSnumerator2 + "/" + oSdenominator2
							+ ")" + " = ...";
				}
			} else { // subtraction

				if (unknLoc == 0) { // ... - numerator1/denominator1 = numerator2/denominator2 =>
									// (numerator2/denominator2) + (numerator1/denominator1) = ...
					ansNumerator = (numerator1 + numerator2) * bogMult;
					question = "... - (" + oSnumerator1 + "/" + oSdenominator1 + ") = (" + oSnumerator2 + "/"
							+ oSdenominator2 + ")";
				} else if (unknLoc == 1) { // numerator1/denominator1 - ... = numerator2/denominator2 =>
											// (numerator1/denominator1) - (numerator2/denominator2) = ...
					ansNumerator = (numerator1 - numerator2) * bogMult;
					question = "(" + oSnumerator1 + "/" + oSdenominator1 + ") - ... = (" + oSnumerator2 + "/"
							+ oSdenominator2 + ")";
				}

				else if (unknLoc == 2) { // numerator1/denominator1 - numerator2/denominator2 = ...
					ansNumerator = (numerator1 - numerator2) * bogMult;
					question = "(" + oSnumerator1 + "/" + oSdenominator1 + ") - (" + oSnumerator2 + "/" + oSdenominator2
							+ ")" + " = ...";
				}
			}

			answer = new Fraction(ansNumerator, ansDenominator, false, false);

			answerChoices = returnFract(answer);

		} else { // fraction addition and divisions with mixed numbers

			int integer1 = 0; // number that will be on screen as is
			int integer2 = 0; // whole of second number.
			int denominator = 0; // rest of answer has same denominator.
			int restNumerator = 0;
			int ansInteger = 0; // contains whole of answer
			int ansRestNumerator = 0; // contains answer numerator for rest
			int unknLoc = 0;

			integer1 = getRandIntBetween(11, 99);
			integer2 = getRandIntBetween(1, 9);
			denominator = getRandIntBetween(3, 9);
			restNumerator = getRandIntBetween(1, (denominator - 1));

			int oSnumerator = (integer2 * denominator) + restNumerator; // on screen numerator

			int multOrDiv = getRandIntBetween(0, 1);

			if (multOrDiv == 1) { // addition
				// determine unknown
				unknLoc = getRandIntBetween(0, 2);

				if (unknLoc == 0) { // ... + oSnumerator/denominator = integer1 => integer1 - (integer2
									// restNumerator/restDenominator) =...
					ansInteger = integer1 - (integer2 + 1); // because of rest
					ansRestNumerator = denominator - restNumerator;
					question = "... + (" + oSnumerator + "/" + denominator + ") = " + integer1;

				} else if (unknLoc == 1) { // oSnumerator/denominator + ... = integer1 => integer1 - (integer2
											// restNumerator/restDenominator) =...
					ansInteger = integer1 - (integer2 + 1); // because of rest
					ansRestNumerator = denominator - restNumerator;
					question = "(" + oSnumerator + "/" + denominator + ") + ... = " + integer1;

				} else { // oSnumerator/denominator + integer1 = ... => integer1 + (integer2
							// restNumerator/restDenominator) =...
					ansInteger = integer1 + integer2;
					ansRestNumerator = restNumerator;
					question = "(" + oSnumerator + "/" + denominator + ") + " + integer1 + "  = ...";
				}
			}

			else { // subtraction
				unknLoc = getRandIntBetween(0, 2);

				if (unknLoc == 0) { // ... - oSnumerator/denominator = integer1 => integer1 + (integer2
									// restNumerator/restDenominator) =...
					ansInteger = integer1 + integer2;
					ansRestNumerator = restNumerator;
					question = "... - (" + oSnumerator + "/" + denominator + ") = " + integer1;

				} else if (unknLoc == 1) { // integer1 - ... = oSnumerator/denominator => integer1 - (integer2
											// restNumerator/restDenominator) =...
					ansInteger = integer1 - (integer2 + 1); // because of rest
					ansRestNumerator = denominator - restNumerator;
					question = integer1 + " - ..." + " = (" + oSnumerator + "/" + denominator + ")";

				} else { // integer1 - oSnumerator/denominator = ... => integer1 - (integer2
							// restNumerator/restDenominator) =...
					ansInteger = integer1 - (integer2 + 1); // because of rest
					ansRestNumerator = denominator - restNumerator;
					question = integer1 + " - " + "(" + oSnumerator + "/" + denominator + ") = ...";
				}
			}

			answer = new Fraction(ansRestNumerator+(ansInteger*denominator), denominator, false, true);

			answerChoices = returnMixedFract(answer);

		}

		return new FractionalQuestion(questionNum, question, answer.toString(), answerChoices);

	}

	private static List<Fraction> returnMixedFract(Fraction answer) {

		List<Fraction> answers = new ArrayList<>(4);	
		Fraction bogAnswer;
		
		int bogAnswerType;
		int bogQuotient = 0;
		int bogNumerator = 0;
		int bogDenominator = 0;		
		
		int ansDenominator = answer.getDenominator();
		int ansInteger = answer.getNumerator() / ansDenominator;
		int ansNumerator = Math.floorMod(answer.getNumerator(), ansDenominator);				

		for (int i = 0; i < 4; ++i) { // get all wrong value in vector
			
			bogAnswer = null;
			
			while ((bogAnswer == null) || answers.contains(bogAnswer) || (answer.equals(bogAnswer))) { // look for
																										// actual new																										//wrong answer
				bogQuotient = 0;
				bogNumerator = 0;
				bogDenominator = 0;
				bogAnswerType = getRandIntBetween(0, 3);

				if (bogAnswerType == 0) {
					if (getRandIntBetween(0, 1) == 0) { // ansDenominator - ansNumerator
						if (ansDenominator / ansNumerator != 2) {
							bogQuotient = ansInteger;
							bogNumerator = (ansDenominator - ansNumerator);
							bogDenominator = ansDenominator;
						}
					}

					else {
						if (getRandIntBetween(0, 1) == 0) { // rest stays correct. larger whole number
							bogQuotient = (ansInteger + getRandIntBetween(1, 5));
							bogNumerator = ansNumerator;
							bogDenominator = ansDenominator;
						} else { // rest stays correct. Smaller whole number
							if (ansInteger > 5) {
								bogQuotient = (ansInteger - getRandIntBetween(1, 5));
								bogNumerator = ansNumerator;
								bogDenominator = ansDenominator;
							} else {
								bogQuotient = (ansInteger - getRandIntBetween(1, ansInteger));
								bogNumerator = ansNumerator;
								bogDenominator = ansDenominator;
							}
						}

					}

				} else if ((bogAnswerType == 1) && (ansDenominator - ansNumerator > 1)) { // larger rest (only if
																							// possible)
					if (getRandIntBetween(0, 1) == 0) {// smaller whole, larger rest
						if (ansInteger > 5) {
							bogQuotient = (ansInteger - getRandIntBetween(1, 5));
							bogNumerator = (ansNumerator + getRandIntBetween(1, ansDenominator - ansNumerator));
							bogDenominator = ansDenominator;
						} else {
							bogQuotient = (ansInteger - getRandIntBetween(1, ansInteger));
							bogNumerator = (ansNumerator + getRandIntBetween(1, ansDenominator - ansNumerator - 1));
							bogDenominator = ansDenominator;
						}
					} else {// larger whole, larger rest
						bogQuotient = (ansInteger - getRandIntBetween(1, 5));
						bogNumerator = (ansNumerator + getRandIntBetween(1, ansDenominator - ansNumerator - 1));
						bogDenominator = ansDenominator;
					}

				} else if ((bogAnswerType == 2) && (ansNumerator > 1)) {
					if (getRandIntBetween(0, 1) == 0) {// smaller whole, smaller rest
						if (ansInteger > 5) {
							bogQuotient = (ansInteger - getRandIntBetween(1, 5));
							bogNumerator = (ansNumerator - getRandIntBetween(0, ansNumerator - 1));
							bogDenominator = ansDenominator;
						} else {
							bogQuotient = (ansInteger - getRandIntBetween(1, ansInteger));
							bogNumerator = (ansNumerator + getRandIntBetween(1, ansNumerator - 1));
							bogDenominator = ansDenominator;
						}
					} else {// larger whole, smaller rest
						bogQuotient = (ansInteger - getRandIntBetween(1, 5));
						bogNumerator = (ansNumerator + getRandIntBetween(1, ansNumerator - 1));
						bogDenominator = ansDenominator;
					}
				}

				// wAToPrint = bogQuotient + " "+ bogNumerator + "/" + bogDenominator + "\n";
				if(bogDenominator!=0 && bogNumerator!=0) {
					bogAnswer = new Fraction(bogNumerator+(bogQuotient*bogDenominator), bogDenominator, false, true);
				} else {
					bogAnswer = null;
				}

			}

			answers.add(bogAnswer);

		}

		answers.set(getRandIntBetween(0, 3), answer); // position between 1 and 4

		return answers;

	}

	public static FractionalQuestion generateFractionalMulti(int questionNum) {
		int numerator1 = 0;
		int numerator2 = 0;
		int ansNumerator = 0; // contains answer numerator
		int denominator1 = 0;
		int denominator2 = 0;
		int ansDenominator = 0; // contains answer denominator
		int commonDivisor1 = 0;
		int commonDivisor2 = 0;
		int multWhat = 0; // what to multiply by common divisor
		int multOrDiv = 0;
		double unknLoc = 0;
		Fraction answer;

		List<Fraction> answerChoices = new ArrayList<>();

		String question = null;

		// get two values for numbers to compute.

		commonDivisor1 = getRandIntBetween(2, 16);
		if (commonDivisor1 > 4) {
			commonDivisor2 = getRandIntBetween(2, 5);
		} else {
			commonDivisor2 = getRandIntBetween(2, 16);
		}

		if (commonDivisor1 > 12 || commonDivisor2 > 12) { // if divisors are big, numbers are small
			numerator1 = getRandIntBetween(1, 4);
			numerator2 = getRandIntBetween(1, 4);
			denominator1 = getRandIntBetween(1, 4);
			denominator2 = getRandIntBetween(1, 4);
		} else { // give slightly larger random numbers to 4 terms
			numerator1 = getRandIntBetween(1, 6);
			numerator2 = getRandIntBetween(1, 6);
			denominator1 = getRandIntBetween(1, 6);
			denominator2 = getRandIntBetween(1, 6);
		}

		if (getRandIntBetween(0, 1) == 0) { // not allowing minus signs to denominators (too ugly on output)
			numerator1 = -numerator1;
		}

		if (getRandIntBetween(0, 1) == 0) {
			numerator2 = -numerator2;
		}

		multOrDiv = getRandIntBetween(0, 1);
		multWhat = getRandIntBetween(0, 3);

		if (multOrDiv == 1) { // multiplication
			// determine unknown
			unknLoc = getRandIntBetween(0, 2);

			if (unknLoc == 0) { // ... x numerator1/denominator1 = numerator2/denominator2 =>
								// (numerator2/denominator2) * (denominator1/numerator1) = ...

				ansNumerator = numerator2 * denominator1;
				ansDenominator = denominator2 * numerator1;

				if (multWhat == 0) {
					numerator2 *= commonDivisor1;
					denominator1 *= commonDivisor2;
					denominator2 *= commonDivisor2;
					numerator1 *= commonDivisor1;
				} else if (multWhat == 1) {
					numerator2 *= commonDivisor2;
					denominator1 *= commonDivisor1;
					denominator2 *= commonDivisor1;
					numerator1 *= commonDivisor2;
				} else if (multWhat == 2) {
					numerator2 *= commonDivisor1;
					denominator1 *= commonDivisor2;
					denominator2 *= commonDivisor1;
					numerator1 *= commonDivisor2;
				} else if (multWhat == 3) {
					numerator2 *= commonDivisor2;
					denominator1 *= commonDivisor1;
					denominator2 *= commonDivisor2;
					numerator1 *= commonDivisor1;
				}

				question = "... x (" + numerator1 + "/" + denominator1 + ") = (" + numerator2 + "/" + denominator2
						+ ")";

			} else if (unknLoc == 1) { // numerator1/denominator1 x ... = numerator2/denominator2 =>
										// (numerator2/denominator2) * (denominator1/numerator1) = ...

				ansNumerator = numerator2 * denominator1;
				ansDenominator = denominator2 * numerator1;

				if (multWhat == 0) {
					numerator2 *= commonDivisor1;
					denominator1 *= commonDivisor2;
					denominator2 *= commonDivisor2;
					numerator1 *= commonDivisor1;
				} else if (multWhat == 1) {
					numerator2 *= commonDivisor2;
					denominator1 *= commonDivisor1;
					denominator2 *= commonDivisor1;
					numerator1 *= commonDivisor2;
				} else if (multWhat == 2) {
					numerator2 *= commonDivisor1;
					denominator1 *= commonDivisor2;
					denominator2 *= commonDivisor1;
					numerator1 *= commonDivisor2;
				} else if (multWhat == 3) {
					numerator2 *= commonDivisor2;
					denominator1 *= commonDivisor1;
					denominator2 *= commonDivisor2;
					numerator1 *= commonDivisor1;
				}

				question = "(" + numerator1 + "/" + denominator1 + ") x ... = (" + numerator2 + "/" + denominator2
						+ ")";

			} else { // numerator1/denominator1 x numerator2/denominator2 = ...

				ansNumerator = numerator1 * numerator2;
				ansDenominator = denominator1 * denominator2;

				if (multWhat == 0) {
					numerator1 *= commonDivisor1;
					numerator2 *= commonDivisor2;
					denominator1 *= commonDivisor2;
					denominator2 *= commonDivisor1;
				} else if (multWhat == 1) {
					numerator1 *= commonDivisor2;
					numerator2 *= commonDivisor1;
					denominator1 *= commonDivisor1;
					denominator2 *= commonDivisor2;
				} else if (multWhat == 2) {
					numerator1 *= commonDivisor1;
					numerator2 *= commonDivisor2;
					denominator1 *= commonDivisor1;
					denominator2 *= commonDivisor2;
				} else if (multWhat == 3) {
					numerator1 *= commonDivisor2;
					numerator2 *= commonDivisor1;
					denominator1 *= commonDivisor2;
					denominator2 *= commonDivisor1;
				}

				question = "(" + numerator1 + "/" + denominator1 + ") x (" + numerator2 + "/" + denominator2 + ")"
						+ " = ...";
			}
		} else { // division
			unknLoc = getRandIntBetween(0, 2);

			if (unknLoc == 0) { // ... / numerator1/denominator1 = numerator2/denominator2 =>
								// (numerator2/denominator2) * (numerator1/denominator1) = ...

				ansNumerator = numerator1 * numerator2;
				ansDenominator = denominator1 * denominator2;

				if (multWhat == 0) {
					numerator1 *= commonDivisor1;
					numerator2 *= commonDivisor2;
					denominator1 *= commonDivisor2;
					denominator2 *= commonDivisor1;
				} else if (multWhat == 1) {
					numerator1 *= commonDivisor2;
					numerator2 *= commonDivisor1;
					denominator1 *= commonDivisor1;
					denominator2 *= commonDivisor2;
				} else if (multWhat == 2) {
					numerator1 *= commonDivisor1;
					numerator2 *= commonDivisor2;
					denominator1 *= commonDivisor1;
					denominator2 *= commonDivisor2;
				} else if (multWhat == 3) {
					numerator1 *= commonDivisor2;
					numerator2 *= commonDivisor1;
					denominator1 *= commonDivisor2;
					denominator2 *= commonDivisor1;
				}

				question = "... : (" + numerator1 + "/" + denominator1 + ") = (" + numerator2 + "/" + denominator2
						+ ")";

			} else if (unknLoc == 1) { // numerator1/denominator1 / ... = numerator2/denominator2 =>
										// (denominator2/numerator2) * (numerator1/denominator1) = ...

				ansNumerator = denominator2 * numerator1;
				ansDenominator = numerator2 * denominator1;

				if (multWhat == 0) {
					numerator2 *= commonDivisor1;
					denominator1 *= commonDivisor2;
					denominator2 *= commonDivisor2;
					numerator1 *= commonDivisor1;
				} else if (multWhat == 1) {
					numerator2 *= commonDivisor2;
					denominator1 *= commonDivisor1;
					denominator2 *= commonDivisor1;
					numerator1 *= commonDivisor2;
				} else if (multWhat == 2) {
					numerator2 *= commonDivisor1;
					denominator1 *= commonDivisor2;
					denominator2 *= commonDivisor1;
					numerator1 *= commonDivisor2;
				} else if (multWhat == 3) {
					numerator2 *= commonDivisor2;
					denominator1 *= commonDivisor1;
					denominator2 *= commonDivisor2;
					numerator1 *= commonDivisor1;
				}

				question = "(" + numerator1 + "/" + denominator1 + ") : ... = (" + numerator2 + "/" + denominator2
						+ ")";
			}

			else if (unknLoc == 2) { // numerator1/denominator1 / numerator2/denominator2 = ... =>
										// (denominator2/numerator2) * (numerator1/denominator1) = ...

				ansNumerator = denominator2 * numerator1;
				ansDenominator = numerator2 * denominator1;

				if (multWhat == 0) {
					numerator2 *= commonDivisor1;
					denominator1 *= commonDivisor2;
					denominator2 *= commonDivisor2;
					numerator1 *= commonDivisor1;
				} else if (multWhat == 1) {
					numerator2 *= commonDivisor2;
					denominator1 *= commonDivisor1;
					denominator2 *= commonDivisor1;
					numerator1 *= commonDivisor2;
				} else if (multWhat == 2) {
					numerator2 *= commonDivisor1;
					denominator1 *= commonDivisor2;
					denominator2 *= commonDivisor1;
					numerator1 *= commonDivisor2;
				} else if (multWhat == 3) {
					numerator2 *= commonDivisor2;
					denominator1 *= commonDivisor1;
					denominator2 *= commonDivisor2;
					numerator1 *= commonDivisor1;
				}

				question = "(" + numerator1 + "/" + denominator1 + ") : (" + numerator2 + "/" + denominator2 + ")"
						+ " = ...";
			}
		}

		answer = new Fraction(ansNumerator, ansDenominator, false, false);

		answerChoices = returnFract(answer);

		return new FractionalQuestion(questionNum, question, answer.toString(), answerChoices);
	}

	private static List<Fraction> returnFract(Fraction answer) {

		List<Fraction> answers = new ArrayList<>(4);

		Fraction bogAnswer = null;
		int bogAnswerType = 0;
		int bogNumerator = 0;
		int bogDenominator = 0;
		int ansDenominator = answer.getDenominator();
		int ansNumerator = answer.getNumerator();

		// if (ansDenominator < 0) { //to make it more readable
		// ansDenominator = -ansDenominator;
		// ansNumerator = -ansNumerator;
		// }

		for (int i = 0; i < 4; ++i) { // get all wrong value in vector

			bogAnswer = null;
			bogNumerator = 0;
			bogDenominator = 0;

			while ((bogAnswer == null) || answers.contains(bogAnswer) || (answer.equals(bogAnswer))) { // answer already																										//input
				// if (ansDenominator < 0) { //reset signs to assure cohesion
				// ansDenominator = -ansDenominator;
				// ansNumerator = -ansNumerator;
				// }

				bogAnswerType = getRandIntBetween(0, 3);

				if (bogAnswerType == 0) { // inverse numerator and denominator
					if (getRandIntBetween(0, 1) == 0) {
						if (ansNumerator != ansDenominator) {
							if (ansNumerator < 0) { // cohesion again
								ansDenominator = -ansDenominator;
								ansNumerator = -ansNumerator;
							}
							bogNumerator = ansDenominator;
							bogDenominator = ansNumerator;	
							
						} else {
							if (ansDenominator > 5) {
								bogNumerator = ansNumerator + getRandIntBetween(1, 5);
								bogDenominator = ansDenominator - getRandIntBetween(1, 5);	
							}
						}

					} else {
						if (ansNumerator != ansDenominator) {
							if (ansNumerator < 0) { // cohesion again
								ansDenominator = -ansDenominator;
								ansNumerator = -ansNumerator;
							}
							bogNumerator = -ansDenominator;
							bogDenominator = ansNumerator;		
						} else {
							if (ansNumerator > 5) {
								bogNumerator = ansNumerator - getRandIntBetween(1, 5);
								bogDenominator = ansDenominator + getRandIntBetween(1, 5);	
							}
						}
					}

				} else if (bogAnswerType == 1) {
					if (getRandIntBetween(0, 1) == 0) {
						if ((ansNumerator > 5) || (ansNumerator < -5)) {
							if (ansNumerator < 0) {
								ansDenominator = -ansDenominator;
								ansNumerator = -ansNumerator;
							}
							if (ansDenominator > 0) {// look at what absolute values do to understand why condition.
								bogNumerator = ansDenominator + getRandIntBetween(1, 5);
								bogDenominator = ansNumerator - getRandIntBetween(1, 5);	
							} else {
								bogNumerator = ansDenominator - getRandIntBetween(1, 5);
								bogDenominator = ansNumerator - getRandIntBetween(1, 5);								
							}
						} else {
							bogNumerator = ansNumerator * getRandIntBetween(1, 5);
							bogDenominator = ansDenominator * getRandIntBetween(6, 10);							
						}
					} else {
						if (ansNumerator < 0) {
							// TODO - should this be 5? Originally 2
							bogNumerator = -ansNumerator + getRandIntBetween(1, 5);
							bogDenominator = ansDenominator + getRandIntBetween(1, 5);	
						} else {
							bogNumerator = -ansNumerator - getRandIntBetween(1, 5);
							bogDenominator = ansDenominator + getRandIntBetween(1, 5);							
						}
					}

				} else if (bogAnswerType == 2) { // induce in error.
					if (getRandIntBetween(0, 1) == 0) {
						bogNumerator = -ansNumerator * getRandIntBetween(1, 5);
						bogDenominator = ansDenominator * getRandIntBetween(6, 10);							
					}
					else {
						if (ansDenominator > 5) {
							if (ansNumerator < 0) {
								ansDenominator = -ansDenominator;
								ansNumerator = -ansNumerator;
								if (ansDenominator < 0) {
									bogNumerator = ansDenominator + getRandIntBetween(1, 5);
									bogDenominator = ansNumerator + getRandIntBetween(1, 5);										
								}
								else {
									bogNumerator = ansDenominator - getRandIntBetween(1, 5);
									bogDenominator = ansNumerator + getRandIntBetween(1, 5);										
								}
							}

						} else {
							bogNumerator = ansNumerator * getRandIntBetween(6, 10);
							bogDenominator = ansDenominator * getRandIntBetween(1, 5);							
						}
					}
				} else if (bogAnswerType == 3) {
					if (getRandIntBetween(0, 1) == 0) {
						if (ansNumerator != ansDenominator) {
							if (ansNumerator < 0) {
								ansDenominator = -ansDenominator;
								ansNumerator = -ansNumerator;
							}
							bogNumerator = -ansDenominator * getRandIntBetween(3, 7);
							bogDenominator = ansNumerator + 1;							
						}
					} else {
						if (ansNumerator != ansDenominator) {
							if (ansNumerator < 0) {
								ansDenominator = -ansDenominator;
								ansNumerator = -ansNumerator;
							}
							bogNumerator = ansDenominator * getRandIntBetween(1, 5);
							bogDenominator = ansNumerator * getRandIntBetween(6, 10);								
						}
					}
				}
				
				if(bogDenominator!=0 && bogNumerator!=0) {
					bogAnswer = new Fraction(bogNumerator, bogDenominator, false, false);
				} else {
					bogAnswer = null;
				}
			}
			
			answers.add(bogAnswer);
		}

		answers.set(getRandIntBetween(0, 3), answer); // position between 1 and 4

		return answers;

	}



}
