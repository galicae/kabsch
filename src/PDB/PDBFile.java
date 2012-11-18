package PDB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PDBFile {
	public ArrayList<AminoAcid> aminoList;

	// the key is resSeq+chainID

	public PDBFile(List<AminoAcid> aa) {
		this.aminoList.addAll(aa);
	}
	
	public PDBFile() {
		aminoList = new ArrayList<AminoAcid>();
	}

	public void setAminoacids(ArrayList<AminoAcid> aminoacids) {
		Collections.copy(aminoList, aminoacids);
	}


}
