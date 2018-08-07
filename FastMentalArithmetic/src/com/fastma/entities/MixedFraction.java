//package com.fastma.entities;
//
//@SuppressWarnings("serial")
//// by not declaring a class as public, it is by default, package-private, and now part of the implementation rather than the API
//class MixedFraction extends Fraction {
//
//	public MixedFraction(int quotient, int num, int den) {
//		super(num+quotient*den, den);			// 
//	}
//	
//	public String toString() {
//	    int a = this.getNumerator() / this.getDenominator();
//	    int b = this.getNumerator() % this.getDenominator();
//	    return a != 0 ? (a + " " + b + "/" + this.getDenominator()) : (b + "/" + this.getDenominator());
//	}
//	
//	public int getQuotient() {
//		return (super.getNumerator()/super.getDenominator());
//	}
//	
//	public int getNumerator() {
//		return (Math.floorMod(super.getNumerator(), super.getDenominator()));
//	}		
//	
//}