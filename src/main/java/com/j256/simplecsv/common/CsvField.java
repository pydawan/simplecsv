package com.j256.simplecsv.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.j256.simplecsv.converter.Converter;
import com.j256.simplecsv.converter.VoidConverter;
import com.j256.simplecsv.processor.CsvProcessor;

/**
 * Annotation to be added to a field to mark it as a cell in a CSV file.
 * 
 * @author graywatson
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CsvField {

	/** Used internally to detect whether or not a value has been configured. */
	public static final String DEFAULT_VALUE = "__simplecsv__ default";

	/**
	 * Sets the name of the cell used in the header and for logging. If not specified, then the field name will be used.
	 */
	public String cellName() default DEFAULT_VALUE;

	/**
	 * Set to true if a value in the field is required -- i.e. the field cannot be empty.
	 */
	public boolean required() default false;

	/**
	 * Set to true if you want the field read from the line to be trimmed before it is converted to Java. This may not
	 * apply to all field types.
	 */
	public boolean trimInput() default false;

	/**
	 * Sets the format for this field. Not all types use the format specifier. Take a look at the particular converter
	 * class javadocs for more particulars. The default format tends to be the toString() of the type, and often use of
	 * the java.text.Format classes are used to override.
	 */
	public String format() default DEFAULT_VALUE;

	/**
	 * Optional flags for the converter which adjust the output. The flags that are used depend on the converter. See
	 * the converter Javadocs for more information. These need to be constants that are added together. For example,
	 * 
	 * <pre>
	 * &#064;CsvField(converterFlags = XxxConverter.FLAG1 + XxxConverter.FLAG2)
	 * private Xxx dollarAmount;
	 * </pre>
	 */
	public long converterFlags() default 0;

	/**
	 * The converter class to use to convert this field if you don't want to use the default appropriate internal class.
	 * This will construct and instance of the class for this particular field. If you want to use a singleton then you
	 * should register the type using {@link CsvProcessor#registerConverter(Class, Converter)}. This converter class
	 * must have a public no-arg constructor.
	 */
	public Class<? extends Converter<?, ?>> converterClass() default VoidConverter.class;

	/**
	 * Set this to a default string that if the cell is empty when read, the value will be used instead. Default is the
	 * empty string.
	 */
	public String defaultValue() default DEFAULT_VALUE;
}
