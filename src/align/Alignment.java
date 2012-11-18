package align;

import java.util.Collections;
import java.util.List;

import PDB.AminoAcid;

public class Alignment {

	private static int GAP = (int) "-".charAt(0);
	public char[][] ali;
	public AminoAcid[] refStruct;

	public Alignment(char[][] ali, String id) {
		this.ali = ali;
	}

	public Alignment() {
		// TODO Auto-generated constructor stub
	}

	public void synchronizeStructure(int seqIndex, List<AminoAcid> aminoacids, AminoAcid[] aminoArray) {
		int aliCounter = 0;
		for (char c : ali[1]) {
			if (c != GAP) {
				aliCounter++;
			}
		}
		aminoArray = new AminoAcid[aliCounter];

		Collections.sort(aminoacids);
		int aminoCount = 0;
		for (int i = 0; i < aliCounter; i++) {
			while (ali[seqIndex][i] == GAP)
				i++;
			if (sameAminoAcid(ali[seqIndex][i], aminoacids.get(aminoCount))) {
				aminoArray[aminoCount] = aminoacids.get(aminoCount);
				aminoCount++;
			}
		}
	}

	private boolean sameAminoAcid(char c, AminoAcid aminoAcid) {
		if (Character.toString(c).equals("A")
				&& aminoAcid.getAtoms().get(0).getResName().equals("ALA"))
			return true;
		if (Character.toString(c).equals("R")
				&& aminoAcid.getAtoms().get(0).getResName().equals("ARG"))
			return true;
		if (Character.toString(c).equals("N")
				&& aminoAcid.getAtoms().get(0).getResName().equals("ASN"))
			return true;
		if (Character.toString(c).equals("D")
				&& aminoAcid.getAtoms().get(0).getResName().equals("ASP"))
			return true;
		// if(Character.toString(c).equals("B") &&
		// aminoAcid.getAtoms().get(0).getResName().equals("ASX"))
		// return true;
		if (Character.toString(c).equals("C")
				&& aminoAcid.getAtoms().get(0).getResName().equals("CYS"))
			return true;
		if (Character.toString(c).equals("E")
				&& aminoAcid.getAtoms().get(0).getResName().equals("GLU"))
			return true;
		if (Character.toString(c).equals("Q")
				&& aminoAcid.getAtoms().get(0).getResName().equals("GLN"))
			return true;
		// if(Character.toString(c).equals("Z") &&
		// aminoAcid.getAtoms().get(0).getResName().equals("GLX"))
		// return true;
		if (Character.toString(c).equals("G")
				&& aminoAcid.getAtoms().get(0).getResName().equals("GLY"))
			return true;
		if (Character.toString(c).equals("L")
				&& aminoAcid.getAtoms().get(0).getResName().equals("LEU"))
			return true;
		if (Character.toString(c).equals("H")
				&& aminoAcid.getAtoms().get(0).getResName().equals("HIS"))
			return true;
		if (Character.toString(c).equals("I")
				&& aminoAcid.getAtoms().get(0).getResName().equals("ILE"))
			return true;
		if (Character.toString(c).equals("K")
				&& aminoAcid.getAtoms().get(0).getResName().equals("LYS"))
			return true;
		if (Character.toString(c).equals("M")
				&& aminoAcid.getAtoms().get(0).getResName().equals("MET"))
			return true;
		if (Character.toString(c).equals("F")
				&& aminoAcid.getAtoms().get(0).getResName().equals("PHE"))
			return true;
		if (Character.toString(c).equals("P")
				&& aminoAcid.getAtoms().get(0).getResName().equals("PRO"))
			return true;
		if (Character.toString(c).equals("S")
				&& aminoAcid.getAtoms().get(0).getResName().equals("SER"))
			return true;
		if (Character.toString(c).equals("T")
				&& aminoAcid.getAtoms().get(0).getResName().equals("THR"))
			return true;
		if (Character.toString(c).equals("W")
				&& aminoAcid.getAtoms().get(0).getResName().equals("TRP"))
			return true;
		if (Character.toString(c).equals("Y")
				&& aminoAcid.getAtoms().get(0).getResName().equals("TYR"))
			return true;
		if (Character.toString(c).equals("V")
				&& aminoAcid.getAtoms().get(0).getResName().equals("VAL"))
			return true;
		return false;
	}
}
