package ClasesBase;

import java.awt.Toolkit;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class TextFilter extends PlainDocument {
	public final static String DIGITS = "0123456789";
	public final static String LETTERS =" abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZaÄöÖüÜíÍáÁúÚéÉóÓ";
        public final static String DIGITS_AND_COMMA = DIGITS + ".";
	public final static String DIGITS_AND_LETTERS = DIGITS + LETTERS;
        public final static String PASS_Y_USR = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz_-.,;:?!0123456789";
        public final static String IP_NUMBER = ".0123456789";
	public final static int FORBIDDEN_LENGTH = -1;

	private String acceptedCharacters;
	private int maximumLength = FORBIDDEN_LENGTH;

	public TextFilter(String acceptedCharacters) {
		this.acceptedCharacters = acceptedCharacters;
	}

	public TextFilter(String acceptedCharacters, int maximumLength) {
		this.acceptedCharacters = acceptedCharacters;
		setMaximumLength(maximumLength);
	}

	public TextFilter(int maximumLength) {
		setMaximumLength(maximumLength);
	}

	public String getAcceptedCharacters() {
		return acceptedCharacters;
	}

	public void setAcceptedCharacters(String acceptedCharacters) {
		this.acceptedCharacters = acceptedCharacters;
	}

	public int getMaximumLength() {
		return maximumLength;
	}

	public void setMaximumLength(int maximumLength) {
		if (maximumLength < FORBIDDEN_LENGTH || maximumLength == 0) {
			throw new IllegalArgumentException(
				"The maximum length must be >=1 or UNRESTRICTED_LENGTH.");
		}
		this.maximumLength = maximumLength;
	}

	@Override
	public void insertString(int offset, String string, AttributeSet attributes)
			throws BadLocationException {
		if (string == null) {
                        Toolkit.getDefaultToolkit().beep();
			return;
		}

		// Checks maximum length
		if (maximumLength != FORBIDDEN_LENGTH &&
			getLength() + string.length() > maximumLength) {
                        Toolkit.getDefaultToolkit().beep();
			return;
		}

		// Checks accepted characters
		if (acceptedCharacters != null) {
			for (int i = 0; i < string.length(); i++) {
				if (acceptedCharacters.indexOf(string.charAt(i)) == -1) {
                                        Toolkit.getDefaultToolkit().beep();
					return;
				}
			}
		}

		// Performs the insert operation
		super.insertString(offset, string, attributes);
	}
}