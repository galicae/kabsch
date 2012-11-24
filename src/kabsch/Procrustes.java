package kabsch;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import cern.jet.math.Functions;

public class Procrustes {
	private static final int x = 0, y = 1, z = 2;
	private DoubleMatrix2D p, q, R;
	private DoubleMatrix1D T; // the move matrix
	private DoubleMatrix1D pCentroid, qCentroid;
	private Kabsch kabschSlave;

	public Procrustes(DoubleMatrix2D p, DoubleMatrix2D q) {
		this.p = p;
		this.q = q;
		T = p.like1D(3);
		kabschSlave = new Kabsch(p.copy(), q.copy());
	}

	public void transform(DoubleMatrix2D Q) {
		R = kabschSlave.calculateRotationMatrix();

		pCentroid = kabschSlave.getPCentroid();
		qCentroid = kabschSlave.getQCentroid();

		qCentroid.assign(Functions.neg);

		// not quite sure that this matrix multiplication should be like this
		multiply1with2(qCentroid, R, T);

		T.assign(pCentroid, Functions.plus);

		// now move Q to P
		DoubleMatrix1D tempRow = qCentroid.like();
		for (int i = 0; i < q.rows(); i++) {
			multiply1with2(q.viewRow(i), R, tempRow);
			tempRow.assign(T, Functions.plus);
			q.viewRow(i).assign(tempRow);
		}
	}

	private void multiply1with2(DoubleMatrix1D mat1D, DoubleMatrix2D mat2D,
			DoubleMatrix1D result) {
		double[] t = new double[3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				t[i] += mat1D.get(j) * mat2D.get(j, i);
			}
		}
		result.assign(t);
		int i = 0;
	}

	public DoubleMatrix2D getP() {
		return p;
	}

	public DoubleMatrix2D getQ() {
		return q;
	}

	public DoubleMatrix2D getR() {
		return R;
	}

	public DoubleMatrix1D getT() {
		return T;
	}

	public DoubleMatrix1D getpCentroid() {
		return pCentroid;
	}

	public DoubleMatrix1D getqCentroid() {
		return qCentroid;
	}

	public Kabsch getKabschSlave() {
		return kabschSlave;
	}

	public double calcOtherRMSD() {
		double err = kabschSlave.getErr();
		double e0 = kabschSlave.getInitError();

		double RMSD = Math.sqrt((e0 - 2 * err) / (p.rows() * 1.0));
		return RMSD;
	}

	public double getRMSD() {
		double rmsd = 0;
		double tx = 0, ty = 0, tz = 0;
		for (int i = 0; i < p.rows(); i++) {
			tx = p.get(i, 0) - q.get(i, 0);
			ty = p.get(i, 1) - q.get(i, 1);
			tz = p.get(i, 2) - q.get(i, 2);

			tx *= tx;
			ty *= ty;
			tz *= tz;

			rmsd = (tx + ty + tz) * (1.0 * p.rows());
		}
		rmsd = Math.sqrt(rmsd);
		return rmsd;
	}
}
