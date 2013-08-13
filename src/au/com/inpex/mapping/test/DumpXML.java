package au.com.inpex.mapping.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.sap.aii.mapping.api.AbstractTransformation;
import com.sap.aii.mapping.api.StreamTransformationException;
import com.sap.aii.mapping.api.TransformationInput;
import com.sap.aii.mapping.api.TransformationOutput;   

/*
 * SAP NetWeaver PI mapping program to dump the source message as an XML file.
 * File location is specified by a mapping parameter.
 * This comes in very handy when testing multi-mappings where you cannot see
 * the intermediate message data.
 */
public class DumpXML extends AbstractTransformation {
	public void transform(TransformationInput arg0, TransformationOutput arg1) 
		throws StreamTransformationException {
		
	    try {
	    	//get mapping parameter for filename on server
	    	String fileName = arg0.getInputParameters().getString("FILENAME");
	    	
	    	//dump the message as a file for debugging
	    	FileOutputStream fos = new FileOutputStream(new File(fileName));
	    	IOUtils.copy(arg0.getInputPayload().getInputStream(), fos);
	    	
	    	//copy input to output
	    	IOUtils.copy(arg0.getInputPayload().getInputStream(), arg1.getOutputPayload().getOutputStream());
	    }
	    catch (FileNotFoundException fnf) {
	    	throw new StreamTransformationException("Stream File not found exception." + fnf.getMessage());
	    }
	    catch (IOException io) {
	    	throw new StreamTransformationException("Stream IOException." + io.getMessage());
	    }

	}
}
