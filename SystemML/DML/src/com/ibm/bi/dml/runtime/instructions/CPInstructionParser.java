package com.ibm.bi.dml.runtime.instructions;

import java.util.HashMap;

import com.ibm.bi.dml.runtime.instructions.CPInstructions.AggregateBinaryCPInstruction;
import com.ibm.bi.dml.runtime.instructions.CPInstructions.AggregateUnaryCPInstruction;
import com.ibm.bi.dml.runtime.instructions.CPInstructions.AppendCPInstruction;
import com.ibm.bi.dml.runtime.instructions.CPInstructions.ArithmeticBinaryCPInstruction;
import com.ibm.bi.dml.runtime.instructions.CPInstructions.BooleanBinaryCPInstruction;
import com.ibm.bi.dml.runtime.instructions.CPInstructions.BooleanUnaryCPInstruction;
import com.ibm.bi.dml.runtime.instructions.CPInstructions.BuiltinBinaryCPInstruction;
import com.ibm.bi.dml.runtime.instructions.CPInstructions.BuiltinUnaryCPInstruction;
import com.ibm.bi.dml.runtime.instructions.CPInstructions.CPInstruction;
import com.ibm.bi.dml.runtime.instructions.CPInstructions.FileCPInstruction;
import com.ibm.bi.dml.runtime.instructions.CPInstructions.FunctionCallCPInstruction;
import com.ibm.bi.dml.runtime.instructions.CPInstructions.ParameterizedBuiltinCPInstruction;
import com.ibm.bi.dml.runtime.instructions.CPInstructions.RandCPInstruction;
import com.ibm.bi.dml.runtime.instructions.CPInstructions.MatrixIndexingCPInstruction;
import com.ibm.bi.dml.runtime.instructions.CPInstructions.RelationalBinaryCPInstruction;
import com.ibm.bi.dml.runtime.instructions.CPInstructions.ReorgCPInstruction;
import com.ibm.bi.dml.runtime.instructions.CPInstructions.SortCPInstruction;
import com.ibm.bi.dml.runtime.instructions.CPInstructions.TertiaryCPInstruction;
import com.ibm.bi.dml.runtime.instructions.CPInstructions.VariableCPInstruction;
import com.ibm.bi.dml.runtime.instructions.CPInstructions.CPInstruction.CPINSTRUCTION_TYPE;
import com.ibm.bi.dml.utils.DMLRuntimeException;
import com.ibm.bi.dml.utils.DMLUnsupportedOperationException;

public class CPInstructionParser extends InstructionParser {

	static public HashMap<String, CPINSTRUCTION_TYPE> String2CPInstructionType;
	static {
		String2CPInstructionType = new HashMap<String, CPINSTRUCTION_TYPE>();

		String2CPInstructionType.put( "ba+*"   	, CPINSTRUCTION_TYPE.AggregateBinary);
		
		String2CPInstructionType.put( "uak+"   	, CPINSTRUCTION_TYPE.AggregateUnary);
		String2CPInstructionType.put( "uark+"   , CPINSTRUCTION_TYPE.AggregateUnary);
		String2CPInstructionType.put( "uack+"   , CPINSTRUCTION_TYPE.AggregateUnary);
		String2CPInstructionType.put( "uamean"  , CPINSTRUCTION_TYPE.AggregateUnary);
		String2CPInstructionType.put( "uarmean" , CPINSTRUCTION_TYPE.AggregateUnary);
		String2CPInstructionType.put( "uacmean" , CPINSTRUCTION_TYPE.AggregateUnary);
		String2CPInstructionType.put( "uamax"   , CPINSTRUCTION_TYPE.AggregateUnary);
		String2CPInstructionType.put( "uarmax"  , CPINSTRUCTION_TYPE.AggregateUnary);
		String2CPInstructionType.put( "uarimax", CPINSTRUCTION_TYPE.AggregateUnary);
		String2CPInstructionType.put( "uacmax"  , CPINSTRUCTION_TYPE.AggregateUnary);
		String2CPInstructionType.put( "uamin"   , CPINSTRUCTION_TYPE.AggregateUnary);
		String2CPInstructionType.put( "uarmin"  , CPINSTRUCTION_TYPE.AggregateUnary);
		String2CPInstructionType.put( "uacmin"  , CPINSTRUCTION_TYPE.AggregateUnary);
		String2CPInstructionType.put( "ua+"     , CPINSTRUCTION_TYPE.AggregateUnary);
		String2CPInstructionType.put( "uar+"    , CPINSTRUCTION_TYPE.AggregateUnary);
		String2CPInstructionType.put( "uac+"    , CPINSTRUCTION_TYPE.AggregateUnary);
		String2CPInstructionType.put( "ua*"     , CPINSTRUCTION_TYPE.AggregateUnary);
		String2CPInstructionType.put( "uatrace" , CPINSTRUCTION_TYPE.AggregateUnary);
		String2CPInstructionType.put( "uaktrace", CPINSTRUCTION_TYPE.AggregateUnary);
		String2CPInstructionType.put( "rdiagM2V", CPINSTRUCTION_TYPE.AggregateUnary);
		String2CPInstructionType.put( "nrow"    ,CPINSTRUCTION_TYPE.AggregateUnary);
		String2CPInstructionType.put( "ncol"    ,CPINSTRUCTION_TYPE.AggregateUnary);
		String2CPInstructionType.put( "length"  ,CPINSTRUCTION_TYPE.AggregateUnary);

		// Arithmetic Instruction Opcodes 
		String2CPInstructionType.put( "+"    , CPINSTRUCTION_TYPE.ArithmeticBinary);
		String2CPInstructionType.put( "-"    , CPINSTRUCTION_TYPE.ArithmeticBinary);
		String2CPInstructionType.put( "*"    , CPINSTRUCTION_TYPE.ArithmeticBinary);
		String2CPInstructionType.put( "/"    , CPINSTRUCTION_TYPE.ArithmeticBinary);
		String2CPInstructionType.put( "^"    , CPINSTRUCTION_TYPE.ArithmeticBinary);

		// Boolean Instruction Opcodes 
		String2CPInstructionType.put( "&&"   , CPINSTRUCTION_TYPE.BooleanBinary);
		String2CPInstructionType.put( "||"   , CPINSTRUCTION_TYPE.BooleanBinary);
		
		String2CPInstructionType.put( "!"    , CPINSTRUCTION_TYPE.BooleanUnary);

		// Relational Instruction Opcodes 
		String2CPInstructionType.put( "=="   , CPINSTRUCTION_TYPE.RelationalBinary);
		String2CPInstructionType.put( "!="   , CPINSTRUCTION_TYPE.RelationalBinary);
		String2CPInstructionType.put( "<"    , CPINSTRUCTION_TYPE.RelationalBinary);
		String2CPInstructionType.put( ">"    , CPINSTRUCTION_TYPE.RelationalBinary);
		String2CPInstructionType.put( "<="   , CPINSTRUCTION_TYPE.RelationalBinary);
		String2CPInstructionType.put( ">="   , CPINSTRUCTION_TYPE.RelationalBinary);

		// File Instruction Opcodes 
		String2CPInstructionType.put( "rm"   , CPINSTRUCTION_TYPE.File);
		String2CPInstructionType.put( "mv"   , CPINSTRUCTION_TYPE.File);

		// Builtin Instruction Opcodes 
		String2CPInstructionType.put( "log"  , CPINSTRUCTION_TYPE.Builtin);

		String2CPInstructionType.put( "max"  , CPINSTRUCTION_TYPE.BuiltinBinary);
		String2CPInstructionType.put( "min"  , CPINSTRUCTION_TYPE.BuiltinBinary);
		
		String2CPInstructionType.put( "exp"  , CPINSTRUCTION_TYPE.BuiltinUnary);
		String2CPInstructionType.put( "abs"  , CPINSTRUCTION_TYPE.BuiltinUnary);
		String2CPInstructionType.put( "sin"  , CPINSTRUCTION_TYPE.BuiltinUnary);
		String2CPInstructionType.put( "cos"  , CPINSTRUCTION_TYPE.BuiltinUnary);
		String2CPInstructionType.put( "tan"  , CPINSTRUCTION_TYPE.BuiltinUnary);
		String2CPInstructionType.put( "sqrt" , CPINSTRUCTION_TYPE.BuiltinUnary);
		String2CPInstructionType.put( "plogp", CPINSTRUCTION_TYPE.BuiltinUnary);
		String2CPInstructionType.put( "print", CPINSTRUCTION_TYPE.BuiltinUnary);
		String2CPInstructionType.put( "print2",CPINSTRUCTION_TYPE.BuiltinUnary);
		String2CPInstructionType.put( "round" ,CPINSTRUCTION_TYPE.BuiltinUnary);
		
		// Parameterized Builtin Functions
		String2CPInstructionType.put( "cdf"	 		, CPINSTRUCTION_TYPE.ParameterizedBuiltin);
		String2CPInstructionType.put( "groupedagg"	, CPINSTRUCTION_TYPE.ParameterizedBuiltin);

		// Variable Instruction Opcodes 
		String2CPInstructionType.put( "assignvar"   , CPINSTRUCTION_TYPE.Variable);
		String2CPInstructionType.put( "cpvar"    	, CPINSTRUCTION_TYPE.Variable);
		String2CPInstructionType.put( "rmvar"    	, CPINSTRUCTION_TYPE.Variable);
		String2CPInstructionType.put( "rmfilevar"   , CPINSTRUCTION_TYPE.Variable);
		String2CPInstructionType.put( "assignvarwithfile", CPINSTRUCTION_TYPE.Variable);
		String2CPInstructionType.put( "attachfiletovar"  , CPINSTRUCTION_TYPE.Variable);
		String2CPInstructionType.put( "valuepick"   , CPINSTRUCTION_TYPE.Variable);
		String2CPInstructionType.put( "iqsize"      , CPINSTRUCTION_TYPE.Variable);
		String2CPInstructionType.put( "read"  		, CPINSTRUCTION_TYPE.Variable);
		String2CPInstructionType.put( "write" 		, CPINSTRUCTION_TYPE.Variable);
		String2CPInstructionType.put( "createvar"   , CPINSTRUCTION_TYPE.Variable);
		//String2CPInstructionType.put( "setfilename" , CPINSTRUCTION_TYPE.Variable);

		// User-defined function Opcodes
		String2CPInstructionType.put( "extfunct"   	, CPINSTRUCTION_TYPE.External);

		String2CPInstructionType.put( "r'"   	, CPINSTRUCTION_TYPE.Reorg);
		String2CPInstructionType.put( "rdiagV2M", CPINSTRUCTION_TYPE.Reorg);
		
		String2CPInstructionType.put( "append", CPINSTRUCTION_TYPE.Append);
		
		String2CPInstructionType.put( "Rand"  , CPINSTRUCTION_TYPE.Rand);
		String2CPInstructionType.put( "ctable", CPINSTRUCTION_TYPE.Tertiary);
		String2CPInstructionType.put( "cm"    , CPINSTRUCTION_TYPE.AggregateUnary);
		String2CPInstructionType.put( "cov"   , CPINSTRUCTION_TYPE.AggregateBinary);
		String2CPInstructionType.put( "sort"  , CPINSTRUCTION_TYPE.Sort);
		String2CPInstructionType.put( "inmem-iqm"  		, CPINSTRUCTION_TYPE.Variable);
		String2CPInstructionType.put( "inmem-valuepick" , CPINSTRUCTION_TYPE.Variable);
		
		String2CPInstructionType.put( "rangeReIndex", CPINSTRUCTION_TYPE.MatrixIndexing);
		String2CPInstructionType.put( "leftIndex"   , CPINSTRUCTION_TYPE.MatrixIndexing);
		
	}

	public static CPInstruction parseSingleInstruction (String str ) throws DMLUnsupportedOperationException, DMLRuntimeException {
		if ( str == null || str.isEmpty() )
			return null;
		
		CPINSTRUCTION_TYPE cptype = InstructionUtils.getCPType(str); 
		if ( cptype == null ) 
			throw new DMLRuntimeException("Unable derive cptype for instruction: " + str);
		CPInstruction cpinst = CPInstructionParser.parseSingleInstruction(cptype, str);
		if ( cpinst == null )
			throw new DMLRuntimeException("Unable to parse instruction: " + str);
		return cpinst;
	}
	
	public static CPInstruction parseSingleInstruction ( CPINSTRUCTION_TYPE cptype, String str ) throws DMLUnsupportedOperationException, DMLRuntimeException {
		if ( str == null || str.isEmpty() ) 
			return null;
		switch(cptype) {
		case AggregateBinary:
			return (CPInstruction) AggregateBinaryCPInstruction.parseInstruction(str);
		
		case AggregateUnary:
			return (CPInstruction) AggregateUnaryCPInstruction.parseInstruction(str);

		case ArithmeticBinary:
			return (CPInstruction) ArithmeticBinaryCPInstruction.parseInstruction(str);
		
		case Tertiary:
			return (CPInstruction) TertiaryCPInstruction.parseInstruction(str);
		
		case BooleanBinary:
			return (CPInstruction) BooleanBinaryCPInstruction.parseInstruction(str);
			
		case BooleanUnary:
			return (CPInstruction) BooleanUnaryCPInstruction.parseInstruction(str);
			
		case BuiltinBinary:
			return (CPInstruction) BuiltinBinaryCPInstruction.parseInstruction(str);
			
		case BuiltinUnary:
			return (CPInstruction) BuiltinUnaryCPInstruction.parseInstruction(str);
			
		case Reorg:
			return (CPInstruction) ReorgCPInstruction.parseInstruction(str);

		case Append:
			return (CPInstruction) AppendCPInstruction.parseInstruction(str);
			
		case RelationalBinary:
			return (CPInstruction) RelationalBinaryCPInstruction.parseInstruction(str);
			
		case File:
			return (CPInstruction) FileCPInstruction.parseInstruction(str);
			
		case Variable:
			return (CPInstruction) VariableCPInstruction.parseInstruction(str);
			
		case Rand:
			return (CPInstruction) RandCPInstruction.parseInstruction(str);
			
		case External:
			//return (CPInstruction) ExtBuiltinCPInstruction.parseInstruction(str);
			return (CPInstruction) FunctionCallCPInstruction.parseInstruction(str);
			
		case ParameterizedBuiltin: 
			return (CPInstruction) ParameterizedBuiltinCPInstruction.parseInstruction(str);
		
		case Sort: 
			return (CPInstruction) SortCPInstruction.parseInstruction(str);
		
		case MatrixIndexing: 
			return (CPInstruction) MatrixIndexingCPInstruction.parseInstruction(str);
		
		case Builtin: 
			String []parts = InstructionUtils.getInstructionPartsWithValueType(str);
			if ( parts[0].equals("log") ) {
				if ( parts.length == 3 ) {
					// B=log(A), y=log(x)
					return (CPInstruction) BuiltinUnaryCPInstruction.parseInstruction(str);
				} else if ( parts.length == 4 ) {
					// B=log(A,10), y=log(x,10)
					return (CPInstruction) BuiltinBinaryCPInstruction.parseInstruction(str);
				}
			}
			else {
				throw new DMLRuntimeException("Invalid Builtin Instruction: " + str );
			}
		
		case INVALID:
		default: 
			throw new DMLRuntimeException("Invalid CP Instruction Type: " + cptype );
		}
	}
	
	public static CPInstruction[] parseMixedInstructions ( String str ) throws DMLUnsupportedOperationException, DMLRuntimeException {
		if ( str == null || str.isEmpty() )
			return null;
		
		Instruction[] inst = InstructionParser.parseMixedInstructions(str);
		CPInstruction[] cpinst = new CPInstruction[inst.length];
		for ( int i=0; i < inst.length; i++ ) {
			cpinst[i] = (CPInstruction) inst[i];
		}
		
		return cpinst;
	}
	
	
}
