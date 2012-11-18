package PDB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("rawtypes")
public class AminoAcid implements Comparable {
	
	private List<cAtom> atoms = new ArrayList<cAtom>();
	private String name;

	public AminoAcid(List<cAtom> atoms, String name) {
		this.atoms = atoms;
		this.name = name;
	}

	public List<cAtom> getAtoms() {
//		ArrayList<Atom> atomList = new ArrayList<Atom>(atoms.values());
		return atoms;
	}

	public void setAtoms(ArrayList<cAtom> atoms) {
		this.atoms = atoms;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Object o) {
		if (Integer.parseInt(name) < Integer
				.parseInt(((AminoAcid) o).getName()))
			return -1;
		else if (Integer.parseInt(name) > Integer.parseInt(((AminoAcid) o)
				.getName()))
			return 1;
		else
			return 0;
	}
	
	public List<cAtom> getBackbone() {
		Collections.sort(atoms);
		ArrayList<cAtom> result = new ArrayList<cAtom>();
		result.add(atoms.get(0));
		result.add(atoms.get(1));
		
		return result;
	}

}
