package com.txt.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.txt.entity.AllEntity;
import com.txt.entity.countryEntity;
import com.txt.entity.districtEntity;
import com.txt.entity.stateEntity;
import com.txt.repo.AllRepo;
import com.txt.repo.CountryRepo;
import com.txt.repo.districtRepo;
import com.txt.repo.stateRepo;

@Controller
public class DropdownController {

	@Autowired
	CountryRepo countryRepo;
	@Autowired
	stateRepo stateRepo;
	@Autowired
	districtRepo districtRepo;
	@Autowired
	AllRepo allRepo;

	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("cc", countryRepo.findAll());

		//model.addAttribute("mm",allRepo.findAll());
		return "index";
	}
	@RequestMapping("/view")
	public String view(Model model) {
		System.err.println("view....");
		model.addAttribute("mm",allRepo.findAll());
		return "view";
	}
	@RequestMapping(value="save",method = RequestMethod.POST)
	public String save(Model model,@ModelAttribute("AllEntity") AllEntity allEntity,
			@RequestParam(value = "country_id", required = false) countryEntity country_id,
			@RequestParam(value = "state_id", required = false) stateEntity state_id,
			@RequestParam(value = "district_id", required = false) districtEntity district_id,
			@RequestParam("dscertificate") MultipartFile dscertificate) throws IOException {
		System.err.println("dghbsnmx");
		String fileName = StringUtils.cleanPath(dscertificate.getOriginalFilename());		 
		//allEntity=new AllEntity(country_id, state_id, district_id,dscertificate.getBytes());
		allEntity.setCountry_id1(country_id);
		allEntity.setState_id1(state_id);
		allEntity.setDistrict_id1(district_id);
		 
		allRepo.save(allEntity);
		
		return "redirect:/";
	}

	@ResponseBody
	@RequestMapping(value = "/find-Country-Id.htm", method = RequestMethod.GET)
	public String abc(@RequestParam(value = "country_id", required = false) int country_id) throws JSONException {
		System.err.println("comeee......dist");
		List<stateEntity> ss = stateRepo.findAllstateByDistrictId(country_id);
		JSONArray jarr = new JSONArray();
		JSONObject jobj = null;
		for (stateEntity se : ss) {
			jobj = new JSONObject();
			jobj.put("State_id", se.getState_id());
			jobj.put("State_name", se.getState_name());
			jarr.put(jobj);
		}
		return jarr.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/find-State-Id.htm", method = RequestMethod.GET)
	public String abc1(@RequestParam(value = "state_id", required = false) int state_id) throws JSONException {
		System.err.println("comeee......");
		List<districtEntity> findAllstateByDistrictId = districtRepo.findAllBlockByDistrictId(state_id);
		JSONArray jarr = new JSONArray();
		JSONObject jobj = null;
		for (districtEntity dd : findAllstateByDistrictId) {
			jobj = new JSONObject();
			jobj.put("district_id", dd.getDistrict_id());
			jobj.put("district_name", dd.getDistrict_name());
			jarr.put(jobj);
		}
		return jarr.toString();
	}
	
	@RequestMapping("/ExcelReport.htm")
	public void ExcelReport(HttpServletResponse response) {
		System.err.println("view Excel....");
		List<AllEntity> allEntity = allRepo.getAll();
		try {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Country Report");

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFillBackgroundColor(IndexedColors.BLUE_GREY.getIndex());
		headerCellStyle.setFillPattern(FillPatternType.BIG_SPOTS);

		Font newFont = workbook.createFont();
		newFont.setBold(true);
		newFont.setColor(IndexedColors.WHITE.getIndex());
		newFont.setFontHeightInPoints((short) 15);
		newFont.setItalic(true);

		headerCellStyle.setFont(newFont);
		Row headerRow = sheet.createRow(6);
		Row rows = sheet.createRow(0);
		Row rows1 = sheet.createRow(2);

		Cell heading = rows.createCell(0);
		heading.setCellStyle(headerCellStyle);
		heading.setCellValue("Country Report");

		Cell genCell = rows1.createCell(0);
		genCell.setCellStyle(headerCellStyle);
		genCell.setCellValue("Generated On:");
		rows1.createCell(1).setCellValue(new SimpleDateFormat("dd-MMM-yyyy").format(new Date()));

		String[] columns = { "Sl. No.", "Country", "State","District" };
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}
		int rowNum = 7;
		int count = 0;
		for (AllEntity allEntity1 : allEntity) {

			Row row = sheet.createRow(rowNum++);
			if (allEntity1.getClass() != null) {
				count = count + 1;
				String s = String.valueOf(count);
				row.createCell(0).setCellValue(s.trim());

				if (allEntity1.getCountry_id1().getCountry_name() != null) {
					row.createCell(1).setCellValue(allEntity1.getCountry_id1().getCountry_name().trim());
				} else {
					row.createCell(1).setCellValue("-NA-");
				}if (allEntity1.getState_id1().getState_name() != null) {
					row.createCell(2).setCellValue(allEntity1.getState_id1().getState_name().trim());
				} else {
					row.createCell(2).setCellValue("-NA-");
				}if (allEntity1.getDistrict_id1().getDistrict_name() != null) {
					row.createCell(3).setCellValue(allEntity1.getDistrict_id1().getDistrict_name().trim());
				} else {
					row.createCell(3).setCellValue("-NA-");
				}
				
			}
		}

		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		response.setContentType("application/vnd.ms-excel");

		String FILENAME = "financialyearExcelReport.xls";
		response.addHeader("Content-Disposition", "attachment; filename=" + FILENAME);
		try {
			workbook.write(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	} catch (Exception e) {
		e.printStackTrace();
	}
	}
	@RequestMapping(value = "/pdfReport.htm",method = RequestMethod.POST)
	public void pdfReport(HttpServletResponse response) {
	System.out.println("pdfReport.....");
	try {
		List<AllEntity> allcountrystatecity = allRepo.findAll();
		Document document = new Document(PageSize.A4.rotate(), 10f, 10f, 10f, 0f);
		String FILENAME = "All-Country-State-City-report.pdf";
		PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "attachment; filename=" + FILENAME);
		document.open();

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		table.setSpacingBefore(10f);
		table.setSpacingAfter(0);
		table.setWidthPercentage(100);
		PdfPCell cell1 = new PdfPCell(new Paragraph("Generated On:"));
		cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		cell1.setPaddingLeft(10);
		cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell1);

		PdfPCell cell2 = new PdfPCell(
		new Paragraph(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())));
		cell2.setPaddingLeft(10);
		cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell2);

		table.addCell(new Paragraph(" "));


		document.add(table);
		PdfPTable space = new PdfPTable(1);
		space.getDefaultCell().setFixedHeight(65);
		space.setWidthPercentage(100);
		space.getDefaultCell().setBorder(Rectangle.OUT_BOTTOM);
		space.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
		PdfPCell spaceCell = new PdfPCell(new Paragraph(" "));
		space.addCell(spaceCell);
		document.add(space);

		//Font font = (Font) FontFactory.getFont(FontFactory.COURIER, 10);

		PdfPTable table1 = new PdfPTable(4);
		table1.setWidthPercentage(100);
		table1.setSpacingBefore(0);
		table1.setSpacingAfter(0);
		table1.setWidthPercentage(100);

		// Font font=FontFactory.getFont(FontFactory.COURIER, 11);


		String[] columns = { "SlNo.", "Country Name", "State Name", "City Name" };
		for (int i = 0; i < columns.length; i++) {
		PdfPCell cell = new PdfPCell(new Paragraph(columns[i]));
		cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		cell.setPaddingLeft(10);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table1.addCell(cell);
		}
		int slNo = 1;

		for (int i = 0; i < allcountrystatecity.size(); i++) {
		PdfPCell cellRow1 = new PdfPCell(new Paragraph(String.valueOf(slNo)));

		cellRow1.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellRow1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table1.addCell(cellRow1);

		PdfPCell cellRow2s = new PdfPCell(new Paragraph(
		allcountrystatecity.get(i).getCountry_id1() == null ? "NA" : allcountrystatecity.get(i).getCountry_id1().getCountry_name()));
		cellRow2s.setHorizontalAlignment(Element.ALIGN_LEFT);
		cellRow2s.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table1.addCell(cellRow2s);

		PdfPCell cellRow3s = new PdfPCell(new Paragraph(
		allcountrystatecity.get(i).getState_id1() == null ? "NA" : allcountrystatecity.get(i).getState_id1().getState_name()));
		cellRow3s.setHorizontalAlignment(Element.ALIGN_LEFT);
		cellRow3s.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table1.addCell(cellRow3s);

		PdfPCell cellRow4s = new PdfPCell(new Paragraph(
		allcountrystatecity.get(i).getDistrict_id1() == null ? "NA" : allcountrystatecity.get(i).getDistrict_id1().getDistrict_name()));
		cellRow4s.setHorizontalAlignment(Element.ALIGN_LEFT);
		cellRow4s.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table1.addCell(cellRow4s);
		slNo++;
		}

		document.add(table1);
		document.close();
		writer.close();

		} catch (Exception e) {
		// TODO Auto-generated catch blocks
		System.out.println(e.toString());
		e.printStackTrace();
		}
	}
	
	
}
