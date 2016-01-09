package toni.druck.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DirectoryHelper {

	public static void createDirsForFile(String filename) {
		File dir = new File(filename).getParentFile();
		if (dir != null) {
			dir.mkdirs();
		}
	}
}
