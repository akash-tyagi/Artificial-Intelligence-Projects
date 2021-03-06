package goal_regression;

public class OperGenerator {

	public static void main(String args[]) {
		OperGenerator operGenerator = new OperGenerator();
		operGenerator.pickupGenertor();
		operGenerator.putonGenerator();
		operGenerator.pickupFromTable();
		operGenerator.putonTable();
	}

	private void putonTable() {
		String b2 = "table";
		for (char b1 = 'a'; b1 <= 'd'; b1++) {
			String preconds = " holding(" + b1 + ")";
			String addlist = " on(" + b1 + "," + b2 + ") clear(" + b1
					+ ") gripper_empty()";
			String dellist = preconds;

			String conflict = "";
			for (char b3 = 'a'; b3 <= 'd'; b3++) {
				conflict += " holding(" + b3 + ")";
				if (b1 != b3)
					conflict += " on(" + b3 + "," + b1 + ")";

			}

			System.out.println("\n\nOPER puton(" + b1 + "," + b2 + ")");
			System.out.println("precond:" + preconds);
			System.out.println("addlist:" + addlist);
			System.out.println("dellist:" + dellist);
			System.out.println("conflict:" + conflict);
			System.out.println("END");
		}
	}

	private void pickupFromTable() {
		String b2 = "table";
		for (char b1 = 'a'; b1 <= 'd'; b1++) {
			String preconds = " clear(" + b1 + ") on(" + b1 + "," + b2
					+ ") gripper_empty()";
			String addlist = " holding(" + b1 + ")";
			String dellist = preconds;

			String conflict = "";
			for (char b3 = 'a'; b3 <= 'd'; b3++) {
				if (b1 == b3)
					continue;
				conflict += " on(" + b3 + "," + b1 + ") on(" + b1 + "," + b3
						+ ") holding(" + b3 + ")";
			}

			System.out.println("\n\nOPER pickup(" + b1 + "," + b2 + ")");
			System.out.println("precond:" + preconds);
			System.out.println("addlist:" + addlist);
			System.out.println("dellist:" + dellist);
			System.out.println("conflict:" + conflict);
			System.out.println("END");
		}
	}

	public void putonGenerator() {
		for (char b1 = 'a'; b1 <= 'd'; b1++) {
			for (char b2 = 'a'; b2 <= 'd'; b2++) {
				if (b1 == b2)
					continue;
				String preconds = " holding(" + b1 + ")" + " clear(" + b2 + ")";
				String addlist = " on(" + b1 + "," + b2 + ") clear(" + b1
						+ ") gripper_empty()";
				String dellist = preconds;

				String conflict = "";
				for (char b3 = 'a'; b3 <= 'd'; b3++) {
					conflict += " holding(" + b3 + ")";
					if (b1 != b3)
						conflict += " on(" + b3 + "," + b1 + ")";

					if (b2 != b3 && b3 != b1)
						conflict += " on(" + b3 + "," + b2 + ")";

				}

				System.out.println("\n\nOPER puton(" + b1 + "," + b2 + ")");
				System.out.println("precond:" + preconds);
				System.out.println("addlist:" + addlist);
				System.out.println("dellist:" + dellist);
				System.out.println("conflict:" + conflict);
				System.out.println("END");
			}
		}
	}

	public void pickupGenertor() {
		for (char b1 = 'a'; b1 <= 'd'; b1++) {
			for (char b2 = 'a'; b2 <= 'd'; b2++) {
				if (b1 == b2)
					continue;
				String preconds = " clear(" + b1 + ") on(" + b1 + "," + b2
						+ ") gripper_empty()";
				String addlist = " holding(" + b1 + ") clear(" + b2 + ")";
				String dellist = preconds;

				String conflict = "";
				for (char b3 = 'a'; b3 <= 'd'; b3++) {
					if (b1 == b3)
						continue;
					conflict += " on(" + b3 + "," + b1 + ") on(" + b1 + ","
							+ b3 + ") holding(" + b3 + ")";
				}

				System.out.println("\n\nOPER pickup(" + b1 + "," + b2 + ")");
				System.out.println("precond:" + preconds);
				System.out.println("addlist:" + addlist);
				System.out.println("dellist:" + dellist);
				System.out.println("conflict:" + conflict);
				System.out.println("END");
			}
		}
	}

}
