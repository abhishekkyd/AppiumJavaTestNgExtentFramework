package com.helloselenium.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row =null;
	private XSSFCell cell = null;
	private FileInputStream fis;
	private FileOutputStream fos;
	private String path;

	/**
	 * Constructor to set values for excel used further in the scripts 
	 * 
	 * @param path
	 */
	public ExcelUtility(String path) {
		try {
			this.path = path;
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}

	/** 
	 * returns the row count in a sheet
	 * 
	 * @param sheetName
	 * @return
	 */
	public int getRowCount(String sheetName){
		sheet = workbook.getSheet(sheetName); 
		int number=sheet.getLastRowNum();
		return number;
	}
	
	/** 
	 * returns the row count of particular column in a sheet
	 * 
	 * @param sheetName
	 * @return
	 */
	public int getColumnCount(String sheetName){
		sheet = workbook.getSheet(sheetName); 
		row = sheet.getRow(0);
		int number=row.getLastCellNum();
		return number;
	}

	/**
	 * returns the data from a cell
	 * 
	 * @param sheetName
	 * @param colName
	 * @param rowNum
	 * @return
	 */
	public String getCellData(String sheetName,String colName,int rowNum){
		int col_Num = 0;
		String cellText = "";
		try{
			sheet = workbook.getSheet(sheetName);
			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++){
				if(row.getCell(i).getStringCellValue().trim().equals(colName.trim())){
					col_Num=i;
					break;
				}
			}
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(rowNum);
			cell = row.getCell(col_Num);

			if(cell==null)
				cellText = "";
			else
				if(cell.getCellType()==CellType.STRING)
					cellText = cell.getStringCellValue();
				else if(cell.getCellType()==CellType.NUMERIC){
					//	cellText = String.valueOf((double)cell.getNumericCellValue());
					cellText = NumberToTextConverter.toText(cell.getNumericCellValue());
				}
			return cellText;
		}
		catch(Exception e){
			return "row "+rowNum+" or column "+colName +" does not exist in xlsx";
		}
	}

	/**
	 * returns the data of different format from a cell
	 * 
	 * @param sheetName
	 * @param colName
	 * @param rowNum
	 * @param format
	 * @return
	 */
	public String getCellDataWithDiffFormat(String sheetName, String colName, int rowNum, int headerIndex, String format){
		int col_Num = 0;
		String cellText = "";
		Date day = null;
		try{
			sheet = workbook.getSheet(sheetName);
			row=sheet.getRow(headerIndex);
			for(int i=0;i<row.getLastCellNum();i++){
				if(row.getCell(i).getStringCellValue().replace("\n","").trim().equals(colName.trim())){	
					col_Num=i;
					break;
				}
			}
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(rowNum);
			cell = row.getCell(col_Num);
			if(!format.isEmpty()) {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				day = cell.getDateCellValue();
				cellText = sdf.format(day);
			}else {
				cellText = cell.getStringCellValue();
			}
			return cellText;
		}
		catch(Exception e){
			return "row "+rowNum+" or column "+colName +" does not exist in xls";
		}
	}

	/**
	 * returns the data of different columns (along with headers and rows)
	 * 
	 * @param sheetName
	 * @param colName
	 * @param rowNum
	 * @param format
	 * @return
	 */
	public String[] getCellDataColumnWise(String sheetName, int rowNum){
		String[] colText = null; 
		try{
				sheet = workbook.getSheet(sheetName);
	            int noOfColumns = sheet.getRow(rowNum).getLastCellNum();
	            colText = new String[noOfColumns];
	            for (int j=0;j<noOfColumns;j++){
	            	colText[j] = sheet.getRow(rowNum).getCell(j).getStringCellValue();
	            }
		}
		catch(Exception e){
		}
		return colText;
	}

	/**
	 * returns the data of different columns (along with headers and rows)
	 * 
	 * @param sheetName
	 * @param colName
	 * @param rowNum
	 * @param format
	 * @return
	 */
	public String getLastColumnData(String sheetName, int rowNum){
		String cellText = ""; 
		try{
				sheet = workbook.getSheet(sheetName);
	            int noOfColumns = sheet.getRow(rowNum).getLastCellNum();
	            for (int j=noOfColumns-1;j<noOfColumns;j++){
	            	cellText = sheet.getRow(rowNum).getCell(j).getStringCellValue();
	            }
		}
		catch(Exception e){
		}
		return cellText;
	}
	
	/**
	 * sets the data in a cell
	 * 
	 * @param sheetName
	 * @param colName
	 * @param rowNum
	 * @param data
	 * @return yes or no wrt setting value
	 */
	public boolean setCellData(String sheetName,String colName,int rowNum, String data){
		try{
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			if(rowNum<=0) {
				return false;
			}
			int index = workbook.getSheetIndex(sheetName);
			int colNum=-1;
			if(index==-1){
				return false;
			}
			sheet = workbook.getSheetAt(index);
			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++){
				//System.out.println(row.getCell(i).getStringCellValue().trim());
				if(row.getCell(i).getStringCellValue().replace("\n","").trim().equals(colName)) {
					colNum=i;
				}
			}
			if(colNum==-1) {
				return false;
			}
			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum);
			if (row == null) {
				row = sheet.createRow(rowNum); 
			}
			cell = row.getCell(colNum);

			if (cell == null) {
				cell = row.createCell(colNum);
			}
			cell.setCellValue("");
			cell.setCellValue(data); 
			fos = new FileOutputStream(path);
			workbook.write(fos);
			fis.close();
			fos.close();
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * method to create excel 
	 * 
	 * @param pathDataFile
	 * @param pathResultFile
	 */
	public void createExcel(String pathDataFile, String pathResultFile) {
		try{
			fis = new FileInputStream(pathDataFile);
			workbook = new XSSFWorkbook(fis);
			fos = new FileOutputStream(pathResultFile);
			workbook.write(fos);
			fis.close();
			fos.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * method to read cell with value Yes
	 * 
	 * @param sheetName
	 * @return
	 *//*
	public List<String> testsToRun(String sheetName)
	{
		ArrayList<String> testsToRun = new ArrayList<String>();
		sheet = workbook.getSheet(sheetName);
		for (Row row : sheet) {
			Cell cell = row.getCell(3);
			if(cell.getStringCellValue().trim().equalsIgnoreCase("Yes"))
			{	
				testsToRun.add(row.getCell(2).getStringCellValue().trim());
			}
		}
		return testsToRun;
	}*/

}