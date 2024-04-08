package util;

import common.ErrorMessage;
import exception.IndexOutOfRangeException;
import view.EMenu;

public class MenuUtil {
  public static EMenu intToMenu(int index) throws IndexOutOfRangeException {
    if (index < EMenu.MIN_INDEX || index > EMenu.MAX_INDEX) {
      throw new IndexOutOfRangeException(ErrorMessage.IndexOutOfRange, EMenu.MIN_INDEX, EMenu.MAX_INDEX, index);
    }
    for (EMenu eMenu : EMenu.values()) {
      if (index == eMenu.ordinal() + EMenu.MIN_INDEX) {
        return eMenu;
      }
    }
    throw new IndexOutOfRangeException(ErrorMessage.IndexOutOfRange, EMenu.MIN_INDEX, EMenu.MAX_INDEX, index);
  }
}
