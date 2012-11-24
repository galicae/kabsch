package kabsch;
import java.util.ArrayList;

import PDB.AminoAcid;
import PDB.PDBReader;
import align.Alignment;
import align.AlignmentReader;

/**
 * this is a naive take to exercise 2 of assignment 1
 * 
 * @author nikos
 * 
 */
public class KabschCollective {
	
	public KabschCollective() {
		
	}
	
	public AminoAcid[][] createKabschInput(String align, String pFile, String qFile) {
		AlignmentReader ali = new AlignmentReader(align);
		Alignment alignment = ali.loadAlignment();
		PDBReader p = new PDBReader(pFile);
		PDBReader q = new PDBReader(qFile);

		ArrayList<AminoAcid> pAminoList = new ArrayList<AminoAcid>();
		ArrayList<AminoAcid> qAminoList = new ArrayList<AminoAcid>();

		p.loadPDBFile(pAminoList);
		q.loadPDBFile(qAminoList);
		AminoAcid[] pArray = new AminoAcid[1];
		AminoAcid[] qArray = new AminoAcid[1];

		pArray = alignment.synchronizeStructure(0, pAminoList, pArray);
		qArray = alignment.synchronizeStructure(1, qAminoList, qArray);
		// now pAminoList and qAminoList have the sequence in amino acid form

		// now scan arrays and only keep the Ca from aligned sequences
		AminoAcid[][] kabschFood = new AminoAcid[2][1];
		kabschFood = alignment.synchronizeKabschStructure(pAminoList, qAminoList);
		kabschPrune(kabschFood);
		return kabschFood;
	}
	
	public double[][][] kabschPrune(AminoAcid[][] input) {
		int ca = 0;
		for(int i = 0; i < input[0].length; i++) {
			if(input[0][i].hasCa() && input[1][i].hasCa()) {
				ca++;
			}
		}
		
		double[][][] realAminos = new double[2][ca][3];
		int cIndex = 0;
		for(int i = 0; i < input[0].length; i++) {
			if(input[0][i].hasCa() && input[1][i].hasCa()) {
				realAminos[0][cIndex][0] = input[0][i].getCa().getX();
				realAminos[0][cIndex][1] = input[0][i].getCa().getY();
				realAminos[0][cIndex][2] = input[0][i].getCa().getZ();
				
				realAminos[1][cIndex][0] = input[1][i].getCa().getX();
				realAminos[1][cIndex][1] = input[1][i].getCa().getY();
				realAminos[1][cIndex][2] = input[1][i].getCa().getZ();
				cIndex++;
			}
		}
		return realAminos;
	}
}
