package at.schrogl.fsfinance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * This class uses Hibernate's SchemaExport tool, to generate a DDL file
 * from all entity classes mapped in the Hibernate configuration file
 * (hibernate.cfg.xml)
 * 
 * @author Fritz Schrogl
 * @since 0.0.1
 */
public class Hibernate2DDL {

	private static final String defaultOutputFilename = "src/main/resources/META-INF/sql/hbm2ddl.sql";

	public static void main(String[] args) throws IOException {
		System.out.println("=========================");
		System.out.println("Hibernate Schema Exporter");
		System.out.println("=========================");
		System.out.println();

		System.out.print("Output file [" + defaultOutputFilename + "]: ");
		String outputFilename = new BufferedReader(new InputStreamReader(System.in)).readLine();
		outputFilename = (outputFilename.length() > 0) ? outputFilename : defaultOutputFilename;

		Configuration cfg = new Configuration().configure();
		SchemaExport ddlGenerator = new SchemaExport(cfg);

		ddlGenerator
				.setOutputFile(outputFilename)
				.setFormat(true)
				.setHaltOnError(true)
				.setDelimiter(";");

		ddlGenerator.create(true, false);
	}

}
