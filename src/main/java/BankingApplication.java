package banking;
import java.math.BigDecimal;
import java.util.Scanner;

import exception.DeactivatedAccountException;
import global.AccountType;
import global.EMenu;

public class BankingApplication {
	public static void main(String[] args) {
		BankingApplication banking = new BankingApplication();
		banking.run();
	}
	
	private CentralBank centralBank;
	private String menu;
	private Scanner scanner;
	public BankingApplication() {
		this.centralBank = CentralBank.getInstance();
		
		StringBuilder menuStringBuilder = new StringBuilder();
		menuStringBuilder.append("[�޴� ����]");
		for(EMenu menu : EMenu.values())
			menuStringBuilder.append("\n").append(menu.ordinal()+1).append(". ").append(menu.getMenuText());
		this.menu = menuStringBuilder.toString();
	}
	public void run() {
		this.scanner = new Scanner(System.in);
		boolean isRunning = true;
		int selectedMenuInt;
		while(isRunning) {
			selectedMenuInt = getInputInt(this.menu);
			EMenu menu = EMenu.selectedMenu(selectedMenuInt);
			switch(menu) {
			case eMakeAccount:
				makeAccount();
				break;
			case eWithdrawal:
				withdrawal();
				break;
			case eDeposit:
				deposit();
				break;
			case eRemittance:
				remittance();
				break;
			case eAccountInfo:
				getAccountInfo();
				break;
			case eInterest:
				getInterest();
				break;
			case eDeactiveAccount:
				deactiveAccount();
				break;
			case eActiveAccount:
				activeAccount();
				break;
			case eDeleteAccount:
				deleteAccount();
				break;
			case eQuit:
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
	private String getInputString(String printMsg) {
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
	private int getInputInt(String printMsg) {
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
	private String getAccountNumber(String addPrintMsg) {
		String accountNumber = getInputString(addPrintMsg + "���� ��ȣ�� �Է����ּ���.");
		if(!this.centralBank.checkAccountNumber(accountNumber)) {
			System.out.println("�������� �ʴ� ���� ��ȣ�Դϴ�.");
			return null;
		}
		return accountNumber;
	}
	/*
	 * ���� ����
	 */
	private void makeAccount() {
		AccountType accountType = null;
		boolean validData = false;
		int inputInt;
		do {
			inputInt = getInputInt("�����Ϸ��� ���� ������ �Է����ּ���\n1. �Ϲ� ���� ����\n2. ���� ����\n3. ���� ���� ���");
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
		String owner = getInputString("�����Ϸ��� �������� �̸��� �Է����ּ���");
		String accountNumber = null;
		switch(accountType) {
		case NORMAL_ACCOUNT:
			accountNumber = centralBank.makeAccount(accountType, owner);
			break;
		case SAVING_ACCOUNT:
			inputInt = getInputInt("��ǥ �ݾ��� �Է����ּ���.");
			BigDecimal targetAmount = new BigDecimal(inputInt);
			accountNumber = centralBank.makeAccount(accountType, owner, targetAmount);
			break;
		default:
			break;
		}
		if(accountNumber != null)
			try {
				System.out.println("���������� �����Ǿ����ϴ�.\n"+centralBank.getAccountInfo(accountNumber));
			} catch (DeactivatedAccountException e) {
				System.out.println("��Ȱ��ȭ�� �����Դϴ�.");
			}
		else
			System.out.println("���� ������ �����Ͽ����ϴ�");
	}
	/*
	 * ���
	 */
	private void withdrawal() {
		String accountNumber = getAccountNumber("����� ");
		if(accountNumber == null) return;
		int inputInt = getInputInt("����� �ݾ��� �Է����ּ���.");
		BigDecimal withdrawalAmount = new BigDecimal(inputInt);
		if(centralBank.withdrawal(accountNumber, withdrawalAmount))
			System.out.println("��ݿ� �����ϼ̽��ϴ�.");
		else
			System.out.println("��ݿ� �����Ͽ����ϴ�.");
		try {
			System.out.println(centralBank.getAccountInfo(accountNumber));
		} catch (DeactivatedAccountException e) {
			System.out.println("��Ȱ��ȭ�� �����Դϴ�.");
		}
	}
	/*
	 * �Ա�
	 */
	private void deposit() {
		String accountNumber = getAccountNumber("�Ա��� ");
		if(accountNumber == null) return;
		int inputInt = getInputInt("�Ա��� �ݾ��� �Է����ּ���.");
		BigDecimal depositAmount = new BigDecimal(inputInt);
		centralBank.deposit(accountNumber, depositAmount);
		System.out.println("�Աݿ� �����ϼ̽��ϴ�.");
		try {
			System.out.println(centralBank.getAccountInfo(accountNumber));
		} catch (DeactivatedAccountException e) {
			System.out.println("��Ȱ��ȭ�� �����Դϴ�.");
		}
	}
	/*
	 * �۱�
	 */
	private void remittance() {
		String withdrawalAccountNumber = getAccountNumber("����� ");
		if(withdrawalAccountNumber == null) return;
		String depositAccountNumber = getAccountNumber("�Ա��� ");
		if(depositAccountNumber == null) return;

		int inputInt = getInputInt("�۱��� �ݾ��� �Է����ּ���.");
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
	private void getAccountInfo() {
		String accountNumber = getAccountNumber("������ Ȯ���� ");
		if(accountNumber == null) return;
		try {
			System.out.println(centralBank.getAccountInfo(accountNumber));
		} catch (DeactivatedAccountException e) {
			System.out.println("��Ȱ��ȭ�� �����Դϴ�.");
		}
	}
	/*
	 * ���� Ȯ��
	 */
	private void getInterest() {
		String accountNumber = getAccountNumber("���ڸ� Ȯ���� ");
		if(accountNumber == null) return;
		try {
			System.out.println("��" + centralBank.getInterest(accountNumber));
		} catch (DeactivatedAccountException e) {
			System.out.println("��Ȱ��ȭ�� �����Դϴ�.");
		}
	}
	/*
	 * ���� ��Ȱ��ȭ
	 */
	private void deactiveAccount() {
		String accountNumber = getAccountNumber("��Ȱ��ȭ�� ");
		if(accountNumber == null) return;
		try {
			centralBank.deactive(accountNumber);
		} catch (DeactivatedAccountException e) {
			System.out.println("�̹� ��Ȱ��ȭ�� �����Դϴ�.");
		}
		System.out.println("������ ��Ȱ��ȭ�Ǿ����ϴ�.");
	}
	/*
	 * ���� Ȱ��ȭ
	 */
	private void activeAccount() {
		String accountNumber = getAccountNumber("Ȱ��ȭ�� ");
		if(accountNumber == null) return;
		centralBank.active(accountNumber);
		System.out.println("������ Ȱ��ȭ�Ǿ����ϴ�.");
	}
	/*
	 * ���� ����
	 */
	private void deleteAccount() {
		String accountNumber = getAccountNumber("�����Ϸ��� ");
		if(accountNumber == null) return;
		boolean result = true;
		result = centralBank.deleteAccount(accountNumber);
		if(result)
			System.out.println("������ �����Ͽ����ϴ�.");
		else
			System.out.println("������ �����Ͽ����ϴ�.");
	}
}
