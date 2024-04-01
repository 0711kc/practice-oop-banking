package Banking;
import java.math.BigDecimal;
import java.util.Scanner;

import Account.AccountType;

public class BankingApplication {
	public static void main(String[] args) {
		BankingApplication banking = new BankingApplication();
		banking.run();
	}
	
	private CentralBank centralBank;
	public BankingApplication() {
		this.centralBank = CentralBank.getInstance();
	}
	private String getMenuString() {
		return 	  "[���� �޴�]\n"
				+ "1. ���� ����\n"
				+ "2. ���\n"
				+ "3. �Ա�\n"
				+ "4. �۱�\n"
				+ "5. ���� ���� Ȯ��\n"
				+ "6. ���� Ȯ��\n"
				+ "7. ���� ����\n"
				+ "8. ����";
	}
	public void run() {
		Scanner scanner = new Scanner(System.in);
		boolean isRunning = true;
		int selectedMenu;
		while(isRunning) {
			selectedMenu = getInputInt(getMenuString(), scanner);
			switch(selectedMenu) {
			case 1:
				addAccount(scanner);
				break;
			case 2:
				withdrawal(scanner);
				break;
			case 3:
				deposit(scanner);
				break;
			case 4:
				remittance(scanner);
				break;
			case 5:
				getAccountInfo(scanner);
				break;
			case 6:
				getInterest(scanner);
				break;
			case 7:
				deleteAccount(scanner);
				break;
			case 8:
				System.out.println("�ý����� �����մϴ�.");
				isRunning = false;
				break;
			default :
				System.out.println("�߸��� �Է°��Դϴ�. �ٽ� �Է����ּ���.");
				break;
			}
		}
		scanner.close();
	}
	/**
	 * ����ڷκ��� ���ڿ��� �Է¹޴� �޼ҵ�
	 * @param printMsg ����Ϸ��� ����
	 * @param scanner �Է¹��� Scanner
	 * @return �Է¹��� ����
	 */
	private String getInputString(String printMsg, Scanner scanner) {
		System.out.println(printMsg);
		String input = scanner.next();
		return input;
	}
	/**
	 * ����ڷκ��� ������ �Է¹޴� �޼ҵ�
	 * @param printMsg ����Ϸ��� ����
	 * @param scanner �Է¹��� Scanner
	 * @return �Է¹��� ����
	 */
	private int getInputInt(String printMsg, Scanner scanner) {
		System.out.println(printMsg);
		boolean validData = false;
		int inputInt = 0;
		do {
			try {
				String input = scanner.next();
				inputInt = Integer.parseInt(input);
				if(inputInt < 0) System.out.println("0���� ū ���ڸ� �Է����ּ���.");
				else validData = true;
			} catch (NumberFormatException e) {
				System.out.println("���ڸ� �Է����ּ���.");
			}
		} while(!validData);
		return inputInt;
	}
	/**
	 * ���� ��ȣ�� �Է¹޴� �Լ�
	 * @param addPrintMsg �տ� �߰��Ϸ��� �޽���
	 * @param scanner �Է¹������� Scanner
	 * @return �������� �ʴ� ���¹�ȣ��� null, �����Ѵٸ� ���� ��ȣ�� ��ȯ�Ѵ�.
	 */
	private String getAccountNumber(String addPrintMsg, Scanner scanner) {
		String accountNumber = getInputString(addPrintMsg + "���� ��ȣ�� �Է����ּ���.", scanner);
		if(!this.centralBank.checkAccountNumber(accountNumber)) {
			System.out.println("�������� �ʴ� ���� ��ȣ�Դϴ�.");
			return null;
		}
		return accountNumber;
	}
	/*
	 * ���� ����
	 */
	private void addAccount(Scanner scanner) {
		AccountType accountType = null;
		boolean validData = false;
		int inputInt;
		do {
			inputInt = getInputInt("�����Ϸ��� ���� ������ �Է����ּ���\n1. �Ϲ� ���� ����\n2. ���� ����\n3. ���� ���� ���", scanner);
			switch(inputInt) {
			case 1:
				accountType = AccountType.NORMAL_ACCOUNT;
				validData = true;
				break;
			case 2:
				accountType = AccountType.SAVING_ACCOUNT;
				validData = true;
				break;
			case 3:
				System.out.println("���� ������ ����մϴ�.");
				return;
			default:
				System.out.println("�߸��� �Է°��Դϴ�. �ٽ� �Է����ּ���.");
				break;
			}
		} while(!validData);
		validData = false;
		String owner = getInputString("�����Ϸ��� �������� �̸��� �Է����ּ���", scanner);
		String accountNumber = null;
		switch(accountType) {
		case NORMAL_ACCOUNT:
			accountNumber = centralBank.makeAccount(accountType, owner);
			break;
		case SAVING_ACCOUNT:
			inputInt = getInputInt("��ǥ �ݾ��� �Է����ּ���.", scanner);
			BigDecimal targetAmount = new BigDecimal(inputInt);
			accountNumber = centralBank.makeAccount(accountType, owner, targetAmount);
			break;
		default:
			break;
		}
		if(accountNumber != null)
			System.out.println("���������� �����Ǿ����ϴ�.\n"+centralBank.getAccountInfo(accountNumber));
		else
			System.out.println("���� ������ �����Ͽ����ϴ�");
	}
	/*
	 * ���
	 */
	private void withdrawal(Scanner scanner) {
		String accountNumber = getAccountNumber("����� ", scanner);
		if(accountNumber == null) return;
		int inputInt = getInputInt("����� �ݾ��� �Է����ּ���.", scanner);
		BigDecimal withdrawalAmount = new BigDecimal(inputInt);
		if(centralBank.withdrawal(accountNumber, withdrawalAmount))
			System.out.println("��ݿ� �����ϼ̽��ϴ�.");
		else
			System.out.println("��ݿ� �����Ͽ����ϴ�.");
		System.out.println(centralBank.getAccountInfo(accountNumber));
	}
	/*
	 * �Ա�
	 */
	private void deposit(Scanner scanner) {
		String accountNumber = getAccountNumber("�Ա��� ", scanner);
		if(accountNumber == null) return;
		int inputInt = getInputInt("�Ա��� �ݾ��� �Է����ּ���.", scanner);
		BigDecimal depositAmount = new BigDecimal(inputInt);
		centralBank.deposit(accountNumber, depositAmount);
		System.out.println("�Աݿ� �����ϼ̽��ϴ�.");
		System.out.println(centralBank.getAccountInfo(accountNumber));
	}
	/*
	 * �۱�
	 */
	private void remittance(Scanner scanner) {
		String withdrawalAccountNumber = getAccountNumber("����� ", scanner);
		if(withdrawalAccountNumber == null) return;
		String depositAccountNumber = getAccountNumber("�Ա��� ", scanner);
		if(depositAccountNumber == null) return;

		int inputInt = getInputInt("�۱��� �ݾ��� �Է����ּ���.", scanner);
		BigDecimal remittanceAmount = new BigDecimal(inputInt);
		if(!centralBank.withdrawal(withdrawalAccountNumber, remittanceAmount)) {
			System.out.println("�۱ݿ� �����Ͽ����ϴ�.");
			return;
		}
		centralBank.deposit(depositAccountNumber, remittanceAmount);
		System.out.println("�۱ݿ� �����ϼ̽��ϴ�.");
	}
	/*
	 * ���� ���� Ȯ��
	 */
	private void getAccountInfo(Scanner scanner) {
		String accountNumber = getAccountNumber("������ Ȯ���� ", scanner);
		if(accountNumber == null) return;
		System.out.println(centralBank.getAccountInfo(accountNumber));
	}
	/*
	 * ���� Ȯ��
	 */
	private void getInterest(Scanner scanner) {
		String accountNumber = getAccountNumber("���ڸ� Ȯ���� ", scanner);
		if(accountNumber == null) return;
		System.out.println("��" + centralBank.getInterest(accountNumber));
	}
	/*
	 * ���� ����
	 */
	private void deleteAccount(Scanner scanner) {
		String accountNumber = getAccountNumber("�����Ϸ��� ", scanner);
		if(accountNumber == null) return;
		boolean result = centralBank.deleteAccount(accountNumber);
		if(result)
			System.out.println("������ �����Ͽ����ϴ�.");
		else
			System.out.println("������ �����Ͽ����ϴ�.");
	}
}
