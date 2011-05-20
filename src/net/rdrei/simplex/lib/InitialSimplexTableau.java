package net.rdrei.simplex.lib;

public class InitialSimplexTableau extends SimplexTableau {
	/**
	 * When called via the public constructor, an initial canonical tableau is
	 * created. 
	 * @param problem The problem to create the tableau from.
	 */
	public InitialSimplexTableau(SimplexProblem problem) {
		// Pass on to the next constructor.
		this(problem.getRestrictionSet(), problem.getBaseVariables());
		this.loadFromProblem(problem);
	}
	
	/**
	 * Internal constructor allowing us to pass integer values on to the
	 * super class without creating 200-char lines.
	 * @param restrictionSet
	 * @param baseVariables
	 */
	InitialSimplexTableau(SimplexRestrictionSet restrictionSet,
			int[] baseVariables) {
		super(restrictionSet.size(), baseVariables.length);
	}
	
	private void loadFromProblem(SimplexProblem problem) {
		int row = 0, column;
		for (EnumeratedSimplexRestriction restriction :
			problem.getRestrictionSet()) {
			
			int z;
			// Reset the column counter.
			column = 0;
			
			// Fill out the coefficients.
			for (int var : restriction.getEquationCoefficients()) {
				this.cells[column++][row] = var;
			}
			
			// Fill the Z-column. It's 0 except for the last row.
			if (row == this.getRowCount()) {
				z = 1;	
			} else {
				z = 0;
			}
			
			this.cells[column++][row] = z;
			
			// Fill the b-column
			this.cells[column][row] = restriction.getResult();
			
			row += 1;
		}
		
		// 'row' is now set the to last column which contains the target
		// function.
		column = 0;
		for (int var : this.getTargetFunctionCoefficients(problem)) {
			this.cells[column++][row] = var;
		}
	}
	
	/**
	 * 
	 * Base variables are provided as coefficients to an equation like
	 * Z = 30x1 + 20x2, while we need them in the form of
	 * -30x1 - 20x2 + Z = 0
	 * 
	 * This method returns them in the latter format, like [-30, -20, 1].
	 */
	protected int[] getTargetFunctionCoefficients(SimplexProblem problem) {
		int[] baseVariables = problem.getBaseVariables();
		int restrictionCount = problem.getRestrictionSet().size();
		int length = baseVariables.length + restrictionCount;
		int results[] = new int[length + 1];
		
		for (int i = 0; i < baseVariables.length; i += 1) {
			results[i] = -1 * baseVariables[i];
		}
		
		for (int i = 0; i < restrictionCount; i += 1) {
			results[baseVariables.length + i] = 0;
		}
		
		// Last element
		results[length] = 1;
		
		return results;
	}
}
