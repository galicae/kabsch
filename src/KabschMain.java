import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

import PDB.AminoAcid;
import PDB.PDBReader;
import PDB.cAtom;
import align.Alignment;
import align.AlignmentReader;

/**
 * this is a naive take to exercise 2 of assignment 1
 * 
 * @author nikos
 * 
 */
public class KabschMain {

	private static int GAP = (int) "-".charAt(0);

	public static void main(String[] args) {
		AlignmentReader ali = new AlignmentReader(args[0]);
		Alignment alignment = ali.loadAlignment();
		PDBReader p = new PDBReader(args[0]);
		PDBReader q = new PDBReader(args[1]);

		ArrayList<AminoAcid> pAminoList = new ArrayList<AminoAcid>();
		ArrayList<AminoAcid> qAminoList = new ArrayList<AminoAcid>();

		p.loadPDBFile(pAminoList);
		q.loadPDBFile(qAminoList);
		AminoAcid[] pArray = new AminoAcid[1];
		AminoAcid[] qArray = new AminoAcid[1];

		alignment.synchronizeStructure(0, pAminoList, pArray);
		alignment.synchronizeStructure(1, qAminoList, qArray);
		// now pAminoList and qAminoList have the sequence in amino acid form

	}
}
