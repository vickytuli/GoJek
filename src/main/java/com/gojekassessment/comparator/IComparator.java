package com.gojekassessment.comparator;

import java.io.File;

/**
 * @author VTuli
 *
 */
public interface IComparator {
	
	public boolean compare(String response1, String response2);
	public void getData(File file1, File file2);

}
