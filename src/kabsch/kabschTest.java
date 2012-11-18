package kabsch;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;

public class kabschTest {

	public static void main(String[] args) {
		double d = Math.sqrt(2) / 2;
		// double[][] structP = { { 0 , 0, 0 }, { d , -d, 0 }, { d + d , 0, 0 },
		// { d , d, 0 }, { d , 0, 0 } };
		double[][] structP = { { 0, 0, 1 }, { 1, 0, 1 }, { 1, 1, 1 },
				{ 0, 1, 1 }, { 0.5, 0.5, 1 } };
		double[][] structQ = { { 0, 0, 2 }, { 1, 0, 2 }, { 1, 1, 2 },
				{ 0, 1, 2 }, { 0.5, 0.5, 2 } };

		DoubleFactory2D factory = DoubleFactory2D.dense;
		DoubleMatrix2D P = factory.make(structP);
		DoubleMatrix2D Q = factory.make(structQ);

		Procrustes proc = new Procrustes(P, Q);
		double kabschRMSD = (proc.getKabschSlave().getRMSD());

		proc.transform(Q);
		System.out.println(proc.getP());
		System.out.println(proc.getQ());

		System.out.println(kabschRMSD);
		System.out.println(proc.calcOtherRMSD());
		System.out.println(proc.getRMSD());
	}
}
