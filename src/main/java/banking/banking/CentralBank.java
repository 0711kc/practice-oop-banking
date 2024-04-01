package Banking;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import Account.Account;
import Account.AccountType;
import Account.BasicAccount;
import Account.SavingAccount;
import Interest.InterestCalculator;

public class CentralBank {
	private static CentralBank centralBank;
	private static ArrayList<Account> accountList;
	private static HashMap<AccountType, InterestCalculator> interestPolicy;
	private CentralBank() {
		accountList = new ArrayList<Account>();
		interestPolicy = new HashMap<AccountType, InterestCalculator>();
		for(AccountType accountType : AccountType.values()) {
			interestPolicy.put(accountType, accountType.getInterestCalculator());
		}
	}
	public static CentralBank getInstance() {
		centralBank = Optional.ofNullable(centralBank).orElseGet(CentralBank::new);
		return centralBank;
	}
	private String makeAccountNumber() {
		String accountNumber;
		while(true) {
			accountNumber = "" + ((int)(Math.random() * 899999999) + 100000000); // 100000000~999999999 ���� ����
			if(!checkAccountNumber(accountNumber))
				return accountNumber;
		}
	}
	/**
	 * ���� ������ �����ϸ� ���� ��ȣ�� ��ȯ�Ѵ�.
	 * @param accountType ������ ������ ���޹޴´�.
	 * @param owner �������� �̸��� ���޹޴´�.
	 * @return ������� ������ ���¹�ȣ
	 */
	public String makeAccount(AccountType accountType, String owner) {
		if(!accountType.equals(AccountType.NORMAL_ACCOUNT)) return null;
		String accountNumber = makeAccountNumber();
		Account account = BasicAccount.getInstance(accountNumber, owner, new BigDecimal(0), true);
		if(accountList.add(account))
			return accountNumber;
		else
			return null;
	}
	/**
	 * ���� ������ �����ϸ� ���� ��ȣ�� ��ȯ�Ѵ�.
	 * @param accountType ������ ����
	 * @param owner �������� �̸�
	 * @param targetAmount ��ǥ �ݾ�
	 * @return ������� ������ ���¹�ȣ
	 */
	public String makeAccount(AccountType accountType, String owner, BigDecimal targetAmount) {
		if(!accountType.equals(AccountType.SAVING_ACCOUNT)) return null;
		String accountNumber = makeAccountNumber();
		Account account = SavingAccount.getInstance(accountNumber, owner, new BigDecimal(0), targetAmount, true);
		if(accountList.add(account))
			return accountNumber;
		else
			return null;
	}
	public boolean checkAccountNumber(String accountNumber) {
		for(Account account : accountList)
			if(account.checkNumber(accountNumber))
				return true;
		return false;
	}
	private Account getAccount(String accountNumber) {
		for(Account account : accountList)
			if(account.checkNumber(accountNumber))
				return account;
		return null;
	}
	public boolean withdrawal(String accountNumber, BigDecimal withdrawalAmount) {
		for(Account account : accountList) {
			if(account.checkNumber(accountNumber))
				return account.withdraw(withdrawalAmount);
		}
		return false;
	}
	public void deposit(String accountNumber, BigDecimal depositAmount) {
		accountList.stream()
			.filter((account) -> account.checkNumber(accountNumber))
			.forEach((account) -> account.deposit(depositAmount));
	}
	
	public String getAccountInfo(String accountNumber) {
		Account account = this.getAccount(accountNumber);
		return account != null ? account.getAccountInfo() : "";
	}
	public boolean deleteAccount(String accountNumber) {
		Account account = this.getAccount(accountNumber);
		return account != null ? accountList.remove(account) : false;
	}
	public String getInterest(String accountNumber) {
		Account account = this.getAccount(accountNumber);
		String result = "";
		if(account != null) {
			result = interestPolicy.get(account.getAccountType())
				.getInterest(account.getBalance())
				.toString();
		}
		return result;
	}
}
