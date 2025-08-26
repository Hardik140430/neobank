package com.neobank.account.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.neobank.account.clients.KycClient;
import com.neobank.account.clients.UserServiceClient;
import com.neobank.account.dto.AccountRequest;
import com.neobank.account.dto.BalanceUpdateRequest;
import com.neobank.account.dto.KycStatusResponse;
import com.neobank.account.events.AccountCreatedEvent;
import com.neobank.account.exception.AccountNotFoundException;
import com.neobank.account.exception.InsufficientBalanceException;
import com.neobank.account.exception.KycNotDoneException;
import com.neobank.account.model.Account;
import com.neobank.account.model.KycStatus;
import com.neobank.account.model.TransactionType;
import com.neobank.account.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
public class AccountService {

	@Autowired
	private final AccountRepository accountRepository;
	private final UserServiceClient userServiceClient;

	@Autowired
	private KycClient kycClient;

	@Autowired
	private AuditService auditService;

	// topic name
	private static final String ACCOUNT_TOPIC = "account-events";

	@Autowired
	public AccountService(AccountRepository accountRepository, UserServiceClient userServiceClient) {
		this.accountRepository = accountRepository;
		this.userServiceClient = userServiceClient;
	}

	public String createAccount(AccountRequest request) {

		// step 1: call user-service via feign to verify user exits
		try {
			ResponseEntity<com.neobank.account.dto.User> response = userServiceClient.getUserById(request.getUserId());
			if (response.getBody() == null) {
				return "User not found, Cannot create account";
			}
		} catch (Exception e) {
			return "Error while checking user: " + e.getMessage();
		}

		// step 2 : kyc check
		KycStatusResponse kycStatus = kycClient.getKycStatus(request.getUserId());
		if (!kycStatus.isVerified()) {
			throw new KycNotDoneException("KYC not verified. Cannot create account");
		}

		// STEP 3: Generate unique account number

		// Generate a random unique account number starting with "NEO"
		String generatedAccountNumber = "NEO" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

		// STEP 4: Create account
		Account account = Account.builder().userId(request.getUserId()).accountType(request.getAccountType())
				.balance(request.getBalance() != null ? request.getBalance() : BigDecimal.ZERO)
				.accountNumber(generatedAccountNumber).CreationDate(LocalDateTime.now()).kycStatus(KycStatus.VERIFIED)
				.build();

		// Save the account entity to the database
		Account saved = accountRepository.save(account);

		auditService.logEvent("Account", saved.getAccountNumber(), "DEPOSIT", request.getUserId().toString(),
				"Account created with balance: " + saved.getBalance());

		// build event
		AccountCreatedEvent event = new AccountCreatedEvent(saved.getAccountNumber(), saved.getUserId(),
				saved.getAccountType(), saved.getBalance(), Instant.now());

		return "Account created with Account Number: " + saved.getAccountNumber();
	}

	public void updateBalance(BalanceUpdateRequest request) {
		System.out.println("in account-service updateBalance " + request);
		switch (request.getTransactionType()) {
		case DEPOSIT:
			deposit(request.getToAccountNumber(), request.getAmount());
			break;
		case WITHDRAWAL:
			withdrawal(request.getFromAccountNumber(), request.getAmount());
			break;
		case TRANSFER:
			transfer(request.getFromAccountNumber(), request.getToAccountNumber(), request.getAmount());
			break;
		default:
			throw new RuntimeException("Invalid transaction type");
		}
	}

	public void deposit(String accountNumber, BigDecimal amount) {
		Account account = accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountNumber));

		account.setBalance(account.getBalance().add(amount));
		accountRepository.save(account);

		auditService.logEvent("Account", accountNumber, "DEPOSIT", account.getUserId().toString(),
				"Deposited " + amount + " to account. New balance: " + account.getBalance());
	}

	public void withdrawal(String accountNumber, BigDecimal amount) {
		Account account = accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountNumber));

		// compareTo returns:
		// -1 if less than
		// 0 if equal
		// 1 if greater
		if (account.getBalance().compareTo(amount) < 0) {
			throw new InsufficientBalanceException("Insufficient funds in account: " + accountNumber);
		}

		account.setBalance(account.getBalance().subtract(amount));
		accountRepository.save(account);
		
		auditService.logEvent(
				"Account",
				accountNumber,
				"WITHDRAWAL", 
				account.getUserId().toString(), 
				"Withdrew " + amount + " from account. New balance: "+ account.getBalance());

	}

	public void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
		if (fromAccountNumber.equals(toAccountNumber)) {
			throw new RuntimeException("Transfer not allowed to same account");
		}

		// Withdraw from source account
		Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber)
				.orElseThrow(() -> new AccountNotFoundException("Source account not found: " + fromAccountNumber));

		if (fromAccount.getBalance().compareTo(amount) < 0) {
			throw new InsufficientBalanceException("Insufficient funds in source account: " + fromAccountNumber);
		}

		fromAccount.setBalance(fromAccount.getBalance().subtract(amount));

		// Deposit to target acount
		Account toAccount = accountRepository.findByAccountNumber(toAccountNumber)
				.orElseThrow(() -> new AccountNotFoundException("Target account not found: " + toAccountNumber));

		toAccount.setBalance(toAccount.getBalance().add(amount));

		accountRepository.save(fromAccount);
		accountRepository.save(toAccount);
		
		auditService.logEvent(
				"Account", 
				fromAccountNumber, 
				"TRANSFER-DEBIT", 
				fromAccount.getUserId().toString(), 
				"Transferred "+ amount + " from account " + fromAccountNumber + 
				" to account "+ toAccountNumber + ". New Balance: "+ fromAccount.getBalance());
		
		auditService.logEvent(
                "Account",
                toAccountNumber,
                "TRANSFER-CREDIT",
                toAccount.getUserId().toString(),
                "Received " + amount + " from account " + fromAccountNumber +
                        ". New balance: " + toAccount.getBalance()
        );
		
	}
	
	
	public ResponseEntity<com.neobank.account.dto.User> getUserDetails(Long userId) {
		ResponseEntity<com.neobank.account.dto.User> response = userServiceClient.getUserById(userId);
		return response;
	}
	
	

}
