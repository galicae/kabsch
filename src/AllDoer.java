import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AllDoer {
	private String pdbFile, alignment;

	public AllDoer(String file, String ali) {
		pdbFile = file;
		alignment = ali;
	}

	public String[] loadAlignment() {
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
				if (strLine.startsWith(">"))
					continue;
				gapAligned[i] = strLine;
				i++;
			}
			in.close();
			return gapAligned;
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
}
