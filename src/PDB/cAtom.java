package PDB;

public class cAtom implements Comparable {
	int serial;
	String name;
	String altLoc;
	String resName;
	String chainID;
	int resSeq;
	String iCode;
	double x, y, z;

	public cAtom() {
		// TODO Auto-generated constructor stub
	}

	public cAtom(int serial, String name, String altLoc, String resName,
			String chainID, int resSeq, String iCode, double x, double y,
			double z) {
		this.serial = serial;
		this.name = name;
		this.altLoc = altLoc;
		this.resName = resName;
		this.chainID = chainID;
		this.resSeq = resSeq;
		this.iCode = iCode;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getSerial() {
		return serial;
	}

	public void setSerial(int serial) {
		this.serial = serial;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAltLoc() {
		return altLoc;
	}

	public void setAltLoc(String altLoc) {
		this.altLoc = altLoc;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getChainID() {
		return chainID;
	}

	public void setChainID(String chainID) {
		this.chainID = chainID;
	}

	public int getResSeq() {
		return resSeq;
	}

	public void setResSeq(int resSeq) {
		this.resSeq = resSeq;
	}

	public String getiCode() {
		return iCode;
	}

	public void setiCode(String iCode) {
		this.iCode = iCode;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public String toString() {
		String result = "";
		result += "ATOM  ";

		String serial = Integer.toString(this.serial);
		while (serial.length() < 5)
			serial = " " + serial;
		result += serial + " ";

		while (name.length() < 4)
			name += " ";
		result += name;

		if (altLoc.isEmpty())
			result += " ";
		else
			result += altLoc;

		while (resName.length() < 3)
			resName += " ";
		result += resName + " ";

		result += chainID;

		String resSeq = Integer.toString(this.resSeq);
		while (resSeq.length() < 4)
			resSeq += " ";
		result += resSeq;

		if (iCode.isEmpty())
			result += " ";
		else
			result += iCode;

		result += "   ";

		String xc = Double.toString(x);
		while (xc.length() < 8)
			xc += " ";
		String yc = Double.toString(y);
		while (yc.length() < 8)
			yc += " ";
		String zc = Double.toString(z);
		while (zc.length() < 8)
			zc += " ";
		result += xc + yc + zc;

		return result;
	}

	@Override
	public int compareTo(Object o) {
		if (serial < ((cAtom) o).getSerial())
			return -1;
		else if (serial > ((cAtom) o).getSerial())
			return 1;
		else
			return 0;
	}

}
