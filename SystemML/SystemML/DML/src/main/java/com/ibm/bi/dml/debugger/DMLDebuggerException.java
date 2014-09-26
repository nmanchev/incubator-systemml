/**
 * IBM Confidential
 * OCO Source Materials
 * (C) Copyright IBM Corp. 2010, 2014
 * The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
 */

package com.ibm.bi.dml.debugger;

import com.ibm.bi.dml.api.DMLException;

/**
 * This exception should be thrown to flag debugger errors.
 */
public class DMLDebuggerException extends DMLException 
{
	@SuppressWarnings("unused")
	private static final String _COPYRIGHT = "Licensed Materials - Property of IBM\n(C) Copyright IBM Corp. 2010, 2014\n" +
                                             "US Government Users Restricted Rights - Use, duplication  disclosure restricted by GSA ADP Schedule Contract with IBM Corp.";
	
	private static final long serialVersionUID = 1L;

	public DMLDebuggerException(String string) {
		super(string);
	}
	
	public DMLDebuggerException(Exception e) {
		super(e);
	}

	public DMLDebuggerException(String string, Exception ex){
		super(string,ex);
	}
}