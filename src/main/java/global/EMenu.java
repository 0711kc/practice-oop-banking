package global;

public enum EMenu {
	eMakeAccount("���� ����"),
	eWithdrawal("���"),
	eDeposit("�Ա�"),
	eRemittance("�۱�"),
	eAccountInfo("���� ���� Ȯ��"),
	eInterest("���� Ȯ��"),
	eDeactiveAccount("���� ��Ȱ��ȭ"),
	eActiveAccount("���� Ȱ��ȭ"),
	eDeleteAccount("���� ����"),
	eQuit("����");

	private String menuText;
	EMenu(String menuText) {
		this.menuText = menuText;
	}
	public String getMenuText() {
		return this.menuText;
	}
	public static EMenu selectedMenu(int selectedInt) {
		EMenu[] menuList = EMenu.values();
		if(selectedInt-1 < 0 || selectedInt-1 > menuList.length)
			return null;
		return menuList[selectedInt-1];
	}
}
