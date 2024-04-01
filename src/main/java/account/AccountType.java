package Account;
import Interest.BasicAccountInterest;
import Interest.InterestCalculator;
import Interest.SavingAccountInterest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountType {
	NORMAL_ACCOUNT("���� ����", new BasicAccountInterest()),
	SAVING_ACCOUNT("���� ����", new SavingAccountInterest());

	private final String accountName;
	private final InterestCalculator interestCalculator;
}
