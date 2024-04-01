package Account;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public interface Account {
	final StringBuilder stringBuilder = new StringBuilder();
	final DecimalFormat decimalFormat = new DecimalFormat("###,###");
	String getAccountInfo();
	/**
	 * value��ŭ �ܾ��� �� �ڿ� ���� ���θ� ��ȯ�Ѵ�.
	 */
	boolean withdraw(BigDecimal value);
	/**
	 * value��ŭ �ܾ��� ���Ѵ�.
	 */
	void deposit(BigDecimal value);
	/**
	 * accountNumber�� ���¹�ȣ�� ��ġ ���θ� ��ȯ�Ѵ�.
	 */
	boolean checkNumber(String accountNumer);
	/**
	 * Account Type�� ��ȯ�Ѵ�.
	 * @return AccountType
	 */
	AccountType getAccountType();
	BigDecimal getBalance();
}
