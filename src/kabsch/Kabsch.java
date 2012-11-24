package kabsch;

import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import cern.colt.matrix.linalg.SingularValueDecomposition;
import cern.jet.math.Functions;

/**
 * must be able to produce a rotation matrix
 * 
 * @author nikos
 * 
 */
public class Kabsch {
	private static final int x = 0, y = 1, z = 2;
	private DoubleMatrix2D p, q;
	private double initError, err;
	private double[] pCentroid, qCentroid;

	public Kabsch(DoubleMatrix2D p, DoubleMatrix2D q) {
		this.p = p;
		this.q = q;
	}

	private double[] calculateCentroid(DoubleMatrix2D vector) {
		double cx = 0, cy = 0, cz = 0;
		for (int i = 0; i < vector.rows(); i++) {
			cx += vector.get(i, x);
			cy += vector.get(i, y);
			cz += vector.get(i, z);
		}
		cx = cx / (vector.rows() * 1.0);
		cy = cy / (vector.rows() * 1.0);
		cz = cz / (vector.rows() * 1.0);
		double[] result = { cx, cy, cz };
		return result;
	}

	private void translate() {
		DoubleFactory1D df = DoubleFactory1D.dense;
		pCentroid = calculateCentroid(p);
		DoubleMatrix1D cp = df.make(pCentroid);
		qCentroid = calculateCentroid(q);
		DoubleMatrix1D cq = df.make(qCentroid);

		for (int i = 0; i < p.rows(); i++) {
			p.viewRow(i).assign(cp, Functions.minus);
			q.viewRow(i).assign(cq, Functions.minus);
		}
	}

	private double calculateInitError() {
		double pInit = 0;
		double qInit = 0;
		double tx = 0, ty = 0, tz = 0;

		for (int i = 0; i < p.rows(); i++) {
			tx = Math.pow(p.get(i, x), 2);
			ty = Math.pow(p.get(i, y), 2);
			tz = Math.pow(p.get(i, z), 2);
			pInit += tx + ty + tz;
		}

		for (int i = 0; i < q.rows(); i++) {
			tx = Math.pow(q.get(i, x), 2);
			ty = Math.pow(q.get(i, y), 2);
			tz = Math.pow(q.get(i, z), 2);
			qInit += tx + ty + tz;
		}
		return pInit + qInit;
	}

	public DoubleMatrix2D calculateRotationMatrix() {
		translate();
		initError = calculateInitError();

		Algebra algebra = new Algebra();

		DoubleMatrix2D tP = p.viewDice(); // transpose
		DoubleMatrix2D covarM = algebra.mult(tP, q);

		SingularValueDecomposition svd = new SingularValueDecomposition(covarM);
		DoubleMatrix2D V = svd.getV();
		DoubleMatrix2D S = svd.getS();
		DoubleMatrix2D U = svd.getU();

		// check for right-handed coordinate system
		int d = -1;
		if (algebra.det(V) * algebra.det(U) > 0) {
			d = 1;
		}

		double[][] updateArr = { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, d } };
		DoubleMatrix2D updateMat = p.like(3, 3);
		updateMat.assign(updateArr);

		S = algebra.mult(S, updateMat);
		U = algebra.mult(U, updateMat);
		DoubleMatrix2D tV = V.viewDice();
		DoubleMatrix2D tU = U.viewDice();

		calcErr(S);

		DoubleMatrix2D R = algebra.mult(V, tU);
		return R;
	}

	public DoubleMatrix1D getPCentroid() {
		DoubleFactory1D fac = DoubleFactory1D.dense;
		DoubleMatrix1D pc = fac.make(pCentroid);
		return pc;
	}

	public DoubleMatrix1D getQCentroid() {
		DoubleFactory1D fac = DoubleFactory1D.dense;
		DoubleMatrix1D qc = fac.make(qCentroid);
		return qc;
	}

	public double getInitError() {
		return initError;
	}

	public double calcErr(DoubleMatrix2D S) {
		err = S.get(0, 0) + S.get(1, 1) + S.get(2, 2);
		return err;
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

	public double getErr() {
		return err;
	}
}
