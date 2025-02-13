package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	public FileInputStream fi;
	public FileOutputStream fo;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle style;
	String path;

	public ExcelUtility(String path) {
		this.path = path;
	}

	public int getRowCount(String sheetName) throws IOException {
		this.fi = new FileInputStream(this.path);
		this.workbook = new XSSFWorkbook(this.fi);
		this.sheet = this.workbook.getSheet(sheetName);
		int rowcount = this.sheet.getLastRowNum();
		this.workbook.close();
		this.fi.close();
		return rowcount;
	}

	public int getCellCount(String sheetName, int rownum) throws IOException {
		this.fi = new FileInputStream(this.path);
		this.workbook = new XSSFWorkbook(this.fi);
		this.sheet = this.workbook.getSheet(sheetName);
		this.row = this.sheet.getRow(rownum);
		int cellcount = this.row.getLastCellNum();
		this.workbook.close();
		this.fi.close();
		return cellcount;
	}

	public String getCellData(String sheetName, int rownum, int colnum) throws IOException {
		this.fi = new FileInputStream(this.path);
		this.workbook = new XSSFWorkbook(this.fi);
		this.sheet = this.workbook.getSheet(sheetName);
		this.row = this.sheet.getRow(rownum);
		this.cell = this.row.getCell(colnum);
		DataFormatter formatter = new DataFormatter();

		String data;
		try {
			data = formatter.formatCellValue(this.cell);
		} catch (Exception var7) {
			data = "";
		}

		this.workbook.close();
		this.fi.close();
		return data;
	}

	public void setCellData(String sheetName, int rownum, int colnum, String data) throws IOException {
		File xlfile = new File(this.path);
		if (!xlfile.exists()) {     //If file not exist then create new file
			this.workbook = new XSSFWorkbook();
			this.fo = new FileOutputStream(this.path);
			this.workbook.write(this.fo);
		}

		this.fi = new FileInputStream(this.path);
		this.workbook = new XSSFWorkbook(this.fi);
		if (this.workbook.getSheetIndex(sheetName) == -1) { //If sheet not exists create new sheet
			this.workbook.createSheet(sheetName);
		}

		this.sheet = this.workbook.getSheet(sheetName); //If row not exist then create a new row
		if (this.sheet.getRow(rownum) == null) {
			this.sheet.createRow(rownum);
		}

		this.row = this.sheet.getRow(rownum);
		this.cell = this.row.createCell(colnum);
		this.cell.setCellValue(data);
		this.fo = new FileOutputStream(this.path);
		this.workbook.write(this.fo);
		this.workbook.close();
		this.fi.close();
		this.fo.close();
	}

	public void fillGreenColor(String sheetName, int rownum, int colnum) throws IOException {
		this.fi = new FileInputStream(this.path);
		this.workbook = new XSSFWorkbook(this.fi);
		this.sheet = this.workbook.getSheet(sheetName);
		this.row = this.sheet.getRow(rownum);
		this.cell = this.row.getCell(colnum);
		this.style = this.workbook.createCellStyle();
		this.style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		this.style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		this.cell.setCellStyle(this.style);
		this.workbook.write(this.fo);
		this.workbook.close();
		this.fi.close();
		this.fo.close();
	}

	public void fillRedColor(String sheetName, int rownum, int colnum) throws IOException {
		this.fi = new FileInputStream(this.path);
		this.workbook = new XSSFWorkbook(this.fi);
		this.sheet = this.workbook.getSheet(sheetName);
		this.row = this.sheet.getRow(rownum);
		this.cell = this.row.getCell(colnum);
		this.style = this.workbook.createCellStyle();
		this.style.setFillForegroundColor(IndexedColors.RED.getIndex());
		this.style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		this.cell.setCellStyle(this.style);
		this.workbook.write(this.fo);
		this.workbook.close();
		this.fi.close();
		this.fo.close();
	}
}
