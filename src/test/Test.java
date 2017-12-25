package test;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import jdbc.JdbcUtil;
import toilet.ToiletDAO;
import toilet.ToiletVO;

public class Test {
	public static void main(String[] args) {
		ToiletDAO dao = new ToiletDAO();
		ToiletVO vo = dao.getToiletInfo(1);
		System.out.println(vo.getName());
	}
}

class Toilet {
	ToiletDAO dao = new ToiletDAO();
	
	void insertToilet(ToiletVO[] vo){
		Connection conn = JdbcUtil.getConnection();
		for (int i = 0; i < vo.length; i++) {
			dao.insertToilet(conn, vo[i]);
		}
	}
	
	ToiletVO[] analysisToiletData() {
		FileInputStream fis = null;
		ToiletVO vo[] = null;
		try {
			fis = new FileInputStream("WebContent/WEB-INF/data/toiletData.xls");
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			HSSFSheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			vo = new ToiletVO[rows-1];
			int rowindex = 0;
			int columnindex = 0;
			for (rowindex = 1; rowindex < rows; rowindex++) {
				// 행을 읽는다
				HSSFRow row = sheet.getRow(rowindex);
				if (row != null) {
					// 셀의 수
					int cells = row.getPhysicalNumberOfCells();
					ToiletVO fooVO = new ToiletVO();
					for (columnindex = 0; columnindex <= cells; columnindex++) {
						// 셀값을 읽는다
						HSSFCell cell = row.getCell(columnindex);
						String value = "";
						// 셀이 빈값일경우를 위한 널체크
						if (cell != null) {
							// 타입별로 내용 읽기
							switch (columnindex) {
//fooVO.setHoNum(Integer.parseInt(cell.getStringCellValue()));
							case 0:		//구분
								fooVO.setKind(cell.getStringCellValue());
								break;
							case 1:		//화장실 명
								fooVO.setName(cell.getStringCellValue());
								break;
							case 2:		//도로명 주소
								value = cell.getStringCellValue();
								if(value.equals("") || value.equals("-")) {
									value = null;
								} else if(value.indexOf("\"") >= 0) {
									value = value.replaceAll("\"", "");
								}
								fooVO.setRoadAddr(value);
								break;
							case 3:		//공용화장실 여부
								fooVO.setUnisex(cell.getStringCellValue().toUpperCase().equals("Y"));
								break;
							case 6:		//남성용 장애인 대변기
							case 7:		//남성용 장애인 소변기
							case 11:		//여성용 장애인 대변기
								if(cell.toString().equals("") || cell.toString().equals(".")) {
									break;
								} else if((int)Double.parseDouble(cell.toString()) > 0) {
									fooVO.setForDisabled(true);
								}
								break;
							case 8:		//남성 어린이용 대변기
							case 9:		//남성 어린이용 소변기
							case 12:		//여성 어린이용 대변기
								if(cell.toString().equals("") || cell.toString().equals(".")) {
									break;
								} else if((int)Double.parseDouble(cell.toString()) > 0) {
									fooVO.setForChiledren(true);
								}
								break;
							case 13:		//관리 기관명 
								fooVO.setManagement(cell.getStringCellValue());
								break;
							case 14:		//연락처
								fooVO.setContact(cell.getStringCellValue());
								break;
							case 15:		//개방 시간
								fooVO.setOpenTime(cell.getStringCellValue());
								break;
							case 17:		//번지 주소
								value = cell.getStringCellValue();
								if(value.equals("") || value.equals("-")) {
									value = null;
								} else if(value.indexOf("\"") >= 0) {
									value = value.replaceAll("\"", "");
								}
								fooVO.setAreaAddr(value);
								break;
							case 18:		//경도
								if(cell.toString().equals("")) {
									fooVO.setY(0);
								} else {
									fooVO.setY(Double.parseDouble(cell.toString()));
								}
								break;
							case 19:		//위도
								if(cell.toString().equals("")) {
									fooVO.setX(0);
								} else {
									fooVO.setX(Double.parseDouble(cell.toString()));
								}
								break;
							}
						}
					}
					vo[rowindex - 1] = fooVO;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}
}