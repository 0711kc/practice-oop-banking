package account;
import java.math.BigDecimal;

import global.AccountType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BasicAccount implements Account{
	private AccountType accountType;
	private String accountNumber;
	private String owner;
	private BigDecimal balance;
	private boolean isActivated;
	public static BasicAccount getInstance(String accountNumber, String owner, BigDecimal balance, boolean isActivated) {
		return new BasicAccount(AccountType.NORMAL_ACCOUNT, accountNumber, owner, balance, isActivated);
	}
	@Override
	public synchronized boolean withdraw(BigDecimal value) {
		if(this.balance.compareTo(value) < 0) // �ܾ׺��� ��� �ݾ��� �� ū ���
			return false;
		this.balance = this.balance.subtract(value);
		return true;
	}
	@Override
	public synchronized void deposit(BigDecimal value) {
		this.balance = this.balance.add(value);
	}
	public synchronized String getAccountInfo() {
		stringBuilder.setLength(0);
		stringBuilder.append("[���� ����]").append("\n");
		stringBuilder.append("��������: ").append(accountType.getAccountName()).append("\n");
		stringBuilder.append("���¹�ȣ: ").append(getAccountNumber()).append("\n");
		stringBuilder.append("������: ").append(getOwner()).append("\n");
		stringBuilder.append("�ܾ�: �� ").append(decimalFormat.format(getBalance())).append("\n");
		stringBuilder.append("Ȱ������: ").append(isActivated());
		return stringBuilder.toString();
	}
	public boolean checkNumber(String accountNumber) {
		return this.accountNumber.equals(accountNumber);
	}
	@Override
	public AccountType getAccountType() {
		return this.accountType;
	}
	@Override
	public synchronized void deactive() {
		this.isActivated = false;
	}
	@Override
	public synchronized void active() {
		this.isActivated = true;
	}
	@Override
	public boolean isActive() {
		return this.isActivated;
	}
}
