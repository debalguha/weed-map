package org.progressivelifestyle.weedmaps.processor;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

public class SerializationUtil {
	public static void serializeToDisk(Collection<String> lines) throws IOException {
		String filePath = "C:/logs/URLList.txt";
		FileUtils.writeLines(new File(filePath), lines);
	}
}
