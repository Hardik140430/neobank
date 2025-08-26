package com.neobank.account.exception;

public class InsufficientBalanceException extends RuntimeException {
		public InsufficientBalanceException(String message) {
			super(message);
		}
}
