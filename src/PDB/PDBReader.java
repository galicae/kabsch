package PDB;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class PDBReader {
	private String pdbfile;

	public PDBReader(String file) {
		this.pdbfile = file;
	}

	public void loadPDBFile(ArrayList<AminoAcid> aa) {
		try {
			FileInputStream fstream = new FileInputStream(pdbfile);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String strLine;
			String curr = "";
			String prev = "empty";
			cAtom a = new cAtom();
			ArrayList<cAtom> atoms = new ArrayList<cAtom>();
			while ((strLine = br.readLine()) != null) {
				if (strLine.startsWith("ATOM")) {
					int serial = findInt(strLine.substring(6, 11));
					String name = strLine.substring(12, 16).trim();
					String altLoc = strLine.substring(16, 17).trim();
					String resName = strLine.substring(17, 20).trim();
					String chainID = strLine.substring(21, 22).trim();
					int resSeq = findInt(strLine.substring(22, 26));
					String iCode = strLine.substring(26, 27).trim();
					double x = findDouble(strLine.substring(30, 38));
					double y = findDouble(strLine.substring(38, 46));
					double z = findDouble(strLine.substring(46, 54));
//					double occupancy = findDouble(strLine.substring(54, 60));
//					double tempFactor = findDouble(strLine.substring(60, 66));
//					String element = strLine.substring(76, 78).trim();
//					String charge = strLine.substring(78, 80).trim();

					curr = resSeq + chainID;

					a = new cAtom(serial, name, altLoc, resName, chainID, resSeq, iCode, x, y, z);

					if (prev.equals("empty")) {
						atoms.add(a);
						prev = curr;
						continue;
					}
					if (prev.equals(curr)) {
						atoms.add(a);
					} else {
						AminoAcid naA = new AminoAcid(atoms, prev);
						aa.add(naA);
						atoms = new ArrayList<cAtom>();
						// System.out.println("added " +
						// naA.getAtoms().get(0).getResSeq());
						atoms.add(a);
						prev = curr;
					}
				}
			}
			AminoAcid naA = new AminoAcid(atoms, prev);
			aa.add(naA);
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: The alignment has this to say: "
					+ e.getMessage());
		}
	}

	public double findDouble(String s) {
		return Double.parseDouble(s.trim());
	}

	public int findInt(String s) {
		return Integer.parseInt(s.trim());
	}
}
