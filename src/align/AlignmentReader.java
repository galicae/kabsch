package align;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AlignmentReader {
	private String alignment;
	private String chainKey;

	public AlignmentReader(String ali) {
		alignment = ali;
	}

	// the assumption is that the target sequence is aligned with the reference
	// sequence, that is, in the alignment the target sequence is on the x axis
	// (in the [0] position)
	public Alignment loadAlignment() {
		String[] gapAligned = new String[2];
		try {
			FileInputStream fstream = new FileInputStream(alignment);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			int i = 0;
			while ((strLine = br.readLine()) != null) {
				if (i == 2)
					break;
				if (strLine.startsWith(">")) {
					try {
						chainKey = strLine.split(" ")[1].substring(4, 5);
					} catch (NullPointerException e) {
						System.out
								.println("No chain modifier found on reference number!");
					}
					continue;
				}
				gapAligned[i] = strLine.split(": ")[1];
				i++;
			}
			in.close();
			char[][] alignMatrix = new char[2][gapAligned[0].length()];
			alignMatrix[0] = gapAligned[0].toCharArray();
			alignMatrix[1] = gapAligned[1].toCharArray();
			Alignment result = new Alignment(alignMatrix, alignment.substring(
					alignment.length() - 3, alignment.length() - 3));
			return result;
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: The pairfile has this to say: "
					+ e.getMessage());
		}
		return null;
	}

	/**
	 * *really* fast search for \n characters - maybe expand for whole
	 * expressions?
	 * 
	 * @param filename
	 *            the name of the file to count lines into
	 * @return the number of lines of the text file
	 * @throws IOException
	 */
	public int countLines(String filename) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(filename));
		try {
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n')
						++count;
				}
			}
			return (count == 0 && !empty) ? 1 : count;
		} finally {
			is.close();
		}
	}

	public String getAlignment() {
		return alignment;
	}

	public String getChainKey() {
		return chainKey;
	}
}
