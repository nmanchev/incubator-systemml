package com.ibm.bi.dml.runtime.matrix.io;

import java.util.HashMap;

import com.ibm.bi.dml.runtime.controlprogram.CacheableData;
import com.ibm.bi.dml.runtime.instructions.CPInstructions.MatrixObjectNew;
import com.ibm.bi.dml.utils.CacheAssignmentException;
import com.ibm.bi.dml.utils.CacheException;

public class MatrixBlock extends MatrixBlockDSM
{
	
	private MatrixObjectNew envelope = null;

	public MatrixBlock(int i, int j, boolean sparse1) {
		super(i, j, sparse1);
	}

	public MatrixBlock() {
		super();
	}
	
	public MatrixBlock(HashMap<CellIndex, Double> map) {
		super(map);
	}
	
	public static MatrixBlock randOperations(int rows, int cols, double sparsity, double min, double max, String pdf, long seed)
	{
		MatrixBlock m = null;
		m = new MatrixBlock(); // rows, cols, (sparsity < SPARCITY_TURN_POINT));
		
		if ( pdf.equalsIgnoreCase("normal") ) {
			m.getNormalRandomSparseMatrix(rows, cols, sparsity, seed);
		}
		else {
			m.getRandomSparseMatrix(rows, cols, sparsity, min, max, seed);
		}
		return m;
	}
	
	/*@Override
	public long getObjectSizeInMemory ()
	{
		return 0 + super.getObjectSizeInMemory ();
	}*/
	
	public MatrixObjectNew getEnvelope ()
	{
		return envelope;
	}
	
	public void clearEnvelope ()
	{
		envelope = null;
	}
	
	/**
	 * 
	 * @param newEnvelope
	 * @throws CacheAssignmentException if this matrix has already been assigned
	 *     to some other envelope.
	 */
	public void setEnvelope (MatrixObjectNew newEnvelope)
		throws CacheAssignmentException
	{
		if (envelope != null && envelope != newEnvelope)
			throw new CacheAssignmentException("MatrixBlock cannot be assigned to multiple envelopes.");
		envelope = newEnvelope; 
	}
	
	@Override
	public void finalize()
	{
		if( CacheableData.LDEBUG )
			System.out.println("Matrix Block: finalize matrix block for "+envelope.getVarName()+", "+envelope.getFileName()+" at "+envelope.getStatusAsString());
		
		try 
		{
			if( envelope != null )
				envelope.attemptEviction( this );
			// NOTE: finalize() is called ONLY once on an object. 
			// At this point, the data is either written to disk (through proper eviction) 
			// or eviction is rejected (data references are set to null) & GC will reclaim underlying data in the next pass or through other MatrixBlocks (recovery/original)  
		} 
		catch (CacheException e)
		{
			//e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	public void clearDataReferences() {
		sparseRows = null;
		denseBlock = null;
	}
	
	public MatrixBlock createShallowCopy() 
	{
		MatrixBlock mb = new MatrixBlock( getNumRows(), getNumColumns(), isInSparseFormat());
		//mb.copy(this); //MB: replaced by following code because only shallow copy (new MatrixBlock obj) required 
		if( mb.isInSparseFormat() )
			mb.sparseRows = sparseRows;
		else
			mb.denseBlock = denseBlock;
		mb.maxrow = maxrow;
		mb.maxcolumn = maxcolumn;
		mb.nonZeros = nonZeros;	
		
		mb.envelope = envelope;
		
		return mb;
	}
}
